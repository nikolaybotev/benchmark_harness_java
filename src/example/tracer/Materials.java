package example.tracer;

//The ray tracer code in this file is written by Adam Burmister. It
//is available in its original form from:
//
//http://labs.flog.co.nz/raytracer/
//
//Ported from the v8 benchmark suite by Google 2012.

//Ported from Dart to Java by Nikolay Botev.
//Dart Source: https://github.com/dart-lang/benchmark_harness/blob/8283bfab9d2427290c03955b7ced5aee4752ca5f/example/Tracer/dart/materials.dart

public abstract class Materials {
    final double gloss;             // [0...infinity] 0 = matt
    final double transparency;      // 0=opaque
    final double reflection;        // [0...infinity] 0 = no reflection
    double refraction = 0.50;
    boolean hasTexture = false;

    Materials(double reflection, double transparency, double gloss) {
        this.reflection = reflection;
        this.transparency = transparency;
        this.gloss = gloss;
    }

    abstract Color getColor(double u, double v);

    double wrapUp(double t) {
        t = t % 2.0;
        if(t < -1) t += 2.0;
        if(t >= 1) t -= 2.0;
        return t;
    }
}


class Chessboard extends Materials {
    Color colorEven, colorOdd; double density;

    Chessboard(Color colorEven,
            Color colorOdd,
            double reflection,
            double transparency,
            double gloss,
            double density) {
        super(reflection, transparency, gloss);
        this.colorEven = colorEven;
        this.colorOdd = colorOdd;
        this.density = density;
        this.hasTexture = true;
    }

    Color getColor(double u, double v) {
        double t = this.wrapUp(u * this.density) * this.wrapUp(v * this.density);

        if (t < 0.0) {
            return this.colorEven;
        } else {
            return this.colorOdd;
        }
    }
}


class Solid extends Materials {
    Color color;

    Solid(Color color, double reflection, double refraction, double transparency, double gloss) {
        super(reflection, transparency, gloss);

        this.color = color;
        this.hasTexture = false;
        this.refraction = refraction;
    }

    Color getColor(double u, double v) {
        return this.color;
    }
}
