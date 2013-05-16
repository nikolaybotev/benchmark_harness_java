package example.tracer;

// The ray tracer code in this file is written by Adam Burmister. It
// is available in its original form from:
//
//   http://labs.flog.co.nz/raytracer/
//
// Ported from the v8 benchmark suite by Google 2012.

//Ported from Dart to Java by Nikolay Botev.
//Dart Source: https://github.com/dart-lang/benchmark_harness/blob/3a78dfbede5cdd50babab144eeb5ffa57716a613/example/Tracer/dart/renderscene.dart

class Light {
    final Vector position;
    final Color color;
    final double intensity;

    Light(Vector position, Color color, double intensity) {
        this.position = position;
        this.color = color;
        this.intensity = intensity;
    }

    Light(Vector position, Color color) {
        this(position, color, 10.0);
    }
}

public class RenderScene {

    // 'event' null means that we are benchmarking
    static void renderScene(Object event) {
        Scene scene = new Scene();
        scene.camera = new Camera(new Vector(0.0, 0.0, -15.0),
                new Vector(-0.2, 0.0, 5.0),
                new Vector(0.0, 1.0, 0.0));
        scene.background = new Background(new Color(0.5, 0.5, 0.5), 0.4);

        Sphere sphere = new Sphere(
                new Vector(-1.5, 1.5, 2.0),
                1.5,
                new Solid(
                        new Color(0.0, 0.5, 0.5),
                        0.3,
                        0.0,
                        0.0,
                        2.0
                        )
                );

        Sphere sphere1 = new Sphere(
                new Vector(1.0, 0.25, 1.0),
                0.5,
                new Solid(
                        new Color(0.9,0.9,0.9),
                        0.1,
                        0.0,
                        0.0,
                        1.5
                        )
                );

        Plane plane = new Plane(
                new Vector(0.1, 0.9, -0.5).normalize(),
                1.2,
                new Chessboard(
                        new Color(1.0, 1.0, 1.0),
                        new Color(0.0, 0.0, 0.0),
                        0.2,
                        0.0,
                        1.0,
                        0.7
                        )
                );

        scene.shapes.add(plane);
        scene.shapes.add(sphere);
        scene.shapes.add(sphere1);

        Light light = new Light(
                new Vector(5.0, 10.0, -1.0),
                new Color(0.8, 0.8, 0.8)
                );

        Light light1 = new Light(
                new Vector(-3.0, 5.0, -15.0),
                new Color(0.8, 0.8, 0.8),
                100.0
                );

        scene.lights.add(light);
        scene.lights.add(light1);

        int imageWidth, imageHeight, pixelSize;
        boolean renderDiffuse, renderShadows, renderHighlights, renderReflections;
        Canvas canvas;
        if (event == null) {
            imageWidth = 100;
            imageHeight = 100;
            pixelSize = 5;
            renderDiffuse = true;
            renderShadows = true;
            renderHighlights = true;
            renderReflections = true;
            canvas = null;
        } else {
//            imageWidth = int.parse(query('#imageWidth').value);
//            imageHeight = int.parse(query('#imageHeight').value);
//            pixelSize = int.parse(query('#pixelSize').value.split(',')[0]);
//            renderDiffuse = query('#renderDiffuse').checked;
//            renderShadows = query('#renderShadows').checked;
//            renderHighlights = query('#renderHighlights').checked;
//            renderReflections = query('#renderReflections').checked;
//            canvas = query("#canvas");
            throw new RuntimeException("not supported");
        }
        int rayDepth = 2;

        Engine raytracer = new Engine(/*canvasWidth:*/imageWidth,
                /*canvasHeight:*/imageHeight,
                /*pixelWidth: */pixelSize,
                /*pixelHeight: */pixelSize,
                /*renderDiffuse: */renderDiffuse,
                /*renderShadows: */renderShadows,
                /*renderHighlights: */renderHighlights,
                /*renderReflections: */renderReflections,
                /*rayDepth: */rayDepth
                );

        raytracer.renderScene(scene, canvas);
    }
}
