package example.tracer;

// A minimal canvas interface.
public abstract class Canvas {

    public Color fillStyle;

    public abstract void fillRect(int i, int j, int pxW, int pxH);

}
