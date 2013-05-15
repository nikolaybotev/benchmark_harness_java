package dart_core;

//A simplistic java implementation of a subset of Dart's Stopwatch class protocol.

public class Stopwatch {

    private long startMillis;

    public void start() {
        this.startMillis = System.currentTimeMillis();
    }

    public long getElapsedMilliseconds() {
        return System.currentTimeMillis() - startMillis;
    }

}
