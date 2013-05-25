package example.tracer;

import java.util.ArrayList;
import java.util.List;

//The ray tracer code in this file is written by Adam Burmister. It
//is available in its original form from:
//
//http://labs.flog.co.nz/raytracer/
//
//Ported from the v8 benchmark suite by Google 2012.

//Ported from Dart to Java by Nikolay Botev.
//Dart Source: https://github.com/dart-lang/benchmark_harness/blob/8283bfab9d2427290c03955b7ced5aee4752ca5f/example/Tracer/dart/scene.dart

class Ray {
    final Vector position;
    final Vector direction;

    Ray(Vector position, Vector direction) {
        this.position = position;
        this.direction = direction;
    }
    public String toString() {
        return ("Ray [" + position + ", " + direction + "]");
    }
}


class Camera {
    final Vector position;
    final Vector lookAt;
    final Vector up;
    Vector equator, screen;

    Camera(Vector position, Vector lookAt, Vector up) {
        this.position = position;
        this.lookAt = lookAt;
        this.up = up;

        this.equator = lookAt.normalize().cross(this.up);
        this.screen = this.position.operator_plus(this.lookAt);
    }

    Ray getRay(double vx, double vy) {
        Vector pos = screen.operator_minus
                (this.equator.multiplyScalar(vx).operator_minus(this.up.multiplyScalar(vy)));
        pos.y = pos.y * -1.0;
        Vector dir = pos.operator_minus(this.position);
        Ray ray = new Ray(pos, dir.normalize());
        return ray;
    }

    public String toString() {
        return "Camera []";
    }
}


class Background {
    final Color color;
    final double ambience;

    Background(Color color, double ambience) {
        this.color = color;
        this.ambience = ambience;
    }
}


public class Scene {
    Camera camera;
    List shapes;
    List lights;
    Background background;
    Scene() {
        camera = new Camera(new Vector(0.0, 0.0, -0.5),
                new Vector(0.0, 0.0, 1.0),
                new Vector(0.0, 1.0, 0.0));
        shapes = new ArrayList();
        lights = new ArrayList();
        background = new Background(new Color(0.0, 0.0, 0.5), 0.2);
    }
}
