package benchmark_harness;

import dart_core.Stopwatch;

//Copyright 2011 Google Inc. All Rights Reserved.

public class BenchmarkBase {
    protected final String name;

    // Empty constructor.
    public BenchmarkBase(String name) {
        this.name = name;
        System.out.println(System.getProperty("java.vm.name")
                + " " + System.getProperty("java.version")
                + " " + System.getProperty("java.vm.version"));
    }

    // The benchmark code.
    // This function is not used, if both [warmup] and [exercise] are overwritten.
    protected void run() { }

    // Runs a short version of the benchmark. By default invokes [run] once.
    void warmup() {
        run();
    }

    // Exercices the benchmark. By default invokes [run] 10 times.
    void exercise() {
        for (int i = 0; i < 10; i++) {
            run();
        }
    }

    // Not measured setup code executed prior to the benchmark runs.
    void setup() { }

    // Not measures teardown code executed after the benchark runs.
    void teardown() { }

    // Measures the score for this benchmark by executing it repeately until
    // time minimum has been reached.
    static double measureFor(Runnable f, long timeMinimum) {
        int iter = 0;
        Stopwatch watch = new Stopwatch();
        watch.start();
        long elapsed = 0;
        while (elapsed < timeMinimum) {
            f.run();
            elapsed = watch.getElapsedMilliseconds();
            iter++;
        }
        return 1000.0 * elapsed / iter;
    }

    // Measures the score for the benchmark and returns it.
    double measure() {
        setup();
        // Warmup for at least 100ms. Discard result.
        measureFor(new Runnable() { public void run() { BenchmarkBase.this.warmup(); } }, 100);
        // Run the benchmark for at least 2000ms.
        double result = measureFor(new Runnable() { public void run() { BenchmarkBase.this.exercise(); } }, 2000);
        teardown();
        return result;
    }

    public void report() {
        double score = measure();
        System.out.printf("%s(RunTime): %f us.", name, score);
    }

}