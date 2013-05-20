package example.tracer;

//The ray tracer code in this file is written by Adam Burmister. It
//is available in its original form from:
//
//http://labs.flog.co.nz/raytracer/
//
//Ported from the v8 benchmark suite by Google 2012.

//Ported from Dart to Java by Nikolay Botev.
//Dart Source: https://github.com/dart-lang/benchmark_harness/blob/8283bfab9d2427290c03955b7ced5aee4752ca5f/example/Tracer/dart/engine.dart

public class IntersectionInfo {
    boolean isHit = false;
    int hitCount = 0;
    BaseShape shape; Vector position, normal; Color color; double distance;

    IntersectionInfo() {
        this.color = new Color(0.0, 0.0, 0.0);
    }

    public String toString() { return String.format("Intersection [%s]", position); }
}

