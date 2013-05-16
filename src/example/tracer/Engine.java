package example.tracer;

//The ray tracer code in this file is written by Adam Burmister. It
//is available in its original form from:
//
//http://labs.flog.co.nz/raytracer/
//
//Ported from the v8 benchmark suite by Google 2012.

//Ported from Dart to Java by Nikolay Botev.
//Dart Source: https://github.com/dart-lang/benchmark_harness/blob/8283bfab9d2427290c03955b7ced5aee4752ca5f/example/Tracer/dart/engine.dart

class IntersectionInfo {
    boolean isHit = false;
    int hitCount = 0;
    BaseShape shape; Vector position, normal; Color color; double distance;

    IntersectionInfo() {
        this.color = new Color(0.0, 0.0, 0.0);
    }

    public String toString() { return String.format("Intersection [%s]", position); }
}


public class Engine {
    int canvasWidth;
    int canvasHeight;
    int pixelWidth, pixelHeight;
    boolean renderDiffuse, renderShadows, renderHighlights, renderReflections;
    int rayDepth;
    Canvas canvas;

    Engine(int canvasWidth, int canvasHeight,
            int pixelWidth, int pixelHeight,
            boolean renderDiffuse, boolean renderShadows,
            boolean renderHighlights, boolean renderReflections,
            int rayDepth) {
        this.canvasHeight = canvasHeight;
        this.canvasWidth = canvasWidth;
        this.pixelWidth = pixelWidth;
        this.pixelHeight = pixelHeight;
        this.renderDiffuse = renderDiffuse;
        this.renderShadows = renderShadows;
        this.renderHighlights = renderHighlights;
        this.renderReflections = renderReflections;
        this.rayDepth = rayDepth;

        this.canvasHeight = canvasHeight / pixelHeight;
        this.canvasWidth = canvasWidth / pixelWidth;
    }

    void setPixel(int x, int y, Color color){
        int pxW, pxH;
        pxW = this.pixelWidth;
        pxH = this.pixelHeight;

        if (this.canvas != null) {
            this.canvas.fillStyle = color;
            this.canvas.fillRect(x * pxW, y * pxH, pxW, pxH);
        } else {
            if (x == y) {
                TracerExample.checkNumber += color.brightness();
            }
        }
    }

    // 'canvas' can be null if raytracer runs as benchmark
    void renderScene(Scene scene, Canvas canvas) {
        TracerExample.checkNumber = 0;
        /* Get canvas */
        this.canvas = canvas == null ? null : canvas;

        int canvasHeight = this.canvasHeight;
        int canvasWidth = this.canvasWidth;

        for(int y = 0; y < canvasHeight; y++){
            for(int x = 0; x < canvasWidth; x++){
                double yp = y * 1.0 / canvasHeight * 2 - 1;
                double xp = x * 1.0 / canvasWidth * 2 - 1;

                Ray ray = scene.camera.getRay(xp, yp);
                this.setPixel(x, y, this.getPixelColor(ray, scene));
            }
        }
        if ((canvas == null) && (TracerExample.checkNumber != 2321)) {
            // Used for benchmarking.
            throw new RuntimeException("Scene rendered incorrectly");
        }
    }

    Color getPixelColor(Ray ray, Scene scene){
        IntersectionInfo info = this.testIntersection(ray, scene, null);
        if(info.isHit){
            Color color = this.rayTrace(info, ray, scene, 0);
            return color;
        }
        return scene.background.color;
    }

    IntersectionInfo testIntersection(Ray ray, Scene scene, BaseShape exclude) {
        int hits = 0;
        IntersectionInfo best = new IntersectionInfo();
        best.distance = 2000;

        for(int i=0; i < scene.shapes.size(); i++){
            BaseShape shape = scene.shapes.get(i);

            if(shape != exclude){
                IntersectionInfo info = shape.intersect(ray);
                if (info.isHit &&
                        (info.distance >= 0) &&
                        (info.distance < best.distance)){
                    best = info;
                    hits++;
                }
            }
        }
        best.hitCount = hits;
        return best;
    }

    Ray getReflectionRay(Vector P, Vector N, Vector V){
        double c1 = -N.dot(V);
        Vector R1 = N.multiplyScalar(2*c1).operator_plus(V);
        return new Ray(P, R1);
    }

    Color rayTrace(IntersectionInfo info, Ray ray, Scene scene, int depth) {
        // Calc ambient
        Color color = info.color.multiplyScalar(scene.background.ambience);
        //Color oldColor = color;
        double shininess = Math.pow(10, info.shape.material.gloss + 1);

        for(int i = 0; i < scene.lights.size(); i++) {
            Light light = scene.lights.get(i);

            // Calc diffuse lighting
            Vector v = (light.position.operator_minus(info.position)).normalize();

            if (this.renderDiffuse) {
                double L = v.dot(info.normal);
                if (L > 0.0) {
                    color = color.operator_plus(info.color.operator_star(light.color.multiplyScalar(L)));
                }
            }

            // The greater the depth the more accurate the colours, but
            // this is exponentially (!) expensive
            if (depth <= this.rayDepth) {
                // calculate reflection ray
                if (this.renderReflections && info.shape.material.reflection > 0) {
                    Ray reflectionRay = this.getReflectionRay(info.position,
                            info.normal,
                            ray.direction);
                    IntersectionInfo refl = this.testIntersection(reflectionRay, scene, info.shape);

                    if (refl.isHit && refl.distance > 0){
                        refl.color = this.rayTrace(refl, reflectionRay, scene, depth + 1);
                    } else {
                        refl.color = scene.background.color;
                    }

                    color = color.blend(refl.color, info.shape.material.reflection);
                }
                // Refraction
                /* TODO */
            }
            /* Render shadows and highlights */

            IntersectionInfo shadowInfo = new IntersectionInfo();

            if (this.renderShadows) {
                Ray shadowRay = new Ray(info.position, v);

                shadowInfo = this.testIntersection(shadowRay, scene, info.shape);
                if (shadowInfo.isHit &&
                        shadowInfo.shape != info.shape
                        /*&& shadowInfo.shape.type != 'PLANE'*/) {
                    Color vA = color.multiplyScalar(0.5);
                    double dB = (0.5 * Math.pow(shadowInfo.shape.material.transparency, 0.5));
                    color = vA.addScalar(dB);
                }
            }
            // Phong specular highlights
            if (this.renderHighlights &&
                    !shadowInfo.isHit &&
                    (info.shape.material.gloss > 0)) {
                Vector Lv = (info.shape.position.operator_minus(light.position)).normalize();

                Vector E = (scene.camera.position.operator_minus(info.shape.position)).normalize();

                Vector H = (E.operator_minus(Lv)).normalize();

                double glossWeight = Math.pow(Math.max(info.normal.dot(H), 0), shininess);
                color = light.color.multiplyScalar(glossWeight).operator_plus(color);
            }
        }
        color.limit();
        return color;
    }

    public String toString() {
        return String.format("Engine [canvasWidth: %d, canvasHeight: %d]", canvasWidth, canvasHeight);
    }
}
