package dart_core;

public class Stopwatch {

    private long startMillis;

    public void start() {
        this.startMillis = System.currentTimeMillis();
    }

    public long getElapsedMilliseconds() {
        return System.currentTimeMillis() - startMillis;
    }

}
