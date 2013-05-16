package example.tracer;

//The ray tracer code in this file is written by Adam Burmister. It
//is available in its original form from:
//
//http://labs.flog.co.nz/raytracer/
//
//Ported from the v8 benchmark suite by Google 2012.

//Ported from Dart to Java by Nikolay Botev.
//Dart Source: https://github.com/dart-lang/benchmark_harness/blob/8283bfab9d2427290c03955b7ced5aee4752ca5f/example/Tracer/dart/Tracer.dart

import benchmark_harness.BenchmarkBase;

public class TracerExample {

  //Dummy HTML definition.

    void query(Object a) {}

    public static void main(String[] args) {
        new TracerBenchmark().report();
    }

  //Variable used to hold a number that can be used to verify that
  //the scene was ray traced correctly.
  static int checkNumber;

}

class TracerBenchmark extends BenchmarkBase {
    TracerBenchmark() { super("Tracer"); }

    public void warmup() {
        RenderScene.renderScene(null);
    }

    public void exercise() {
        RenderScene.renderScene(null);
    }
}