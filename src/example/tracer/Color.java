package example.tracer;

//The ray tracer code in this file is written by Adam Burmister. It
//is available in its original form from:
//
//http://labs.flog.co.nz/raytracer/
//
//Ported from the v8 benchmark suite by Google 2012.

//Ported from Dart to Java by Nikolay Botev.
//Dart Source: https://github.com/dart-lang/benchmark_harness/blob/8283bfab9d2427290c03955b7ced5aee4752ca5f/example/Tracer/dart/color.dart

public class Color {
    double red;
    double green;
    double blue;

    Color(double red, double green, double blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    void limit() {
        this.red = (this.red > 0.0) ? ((this.red > 1.0) ? 1.0 : this.red) : 0.0;
        this.green = (this.green > 0.0) ?
                ((this.green > 1.0) ? 1.0 : this.green) : 0.0;
                this.blue = (this.blue > 0.0) ?
                        ((this.blue > 1.0) ? 1.0 : this.blue) : 0.0;
    }

    Color operator_plus(Color c2) {
        return new Color(red + c2.red, green + c2.green, blue + c2.blue);
    }

    Color addScalar(double s){
        Color result = new Color(red + s, green + s, blue + s);
        result.limit();
        return result;
    }

    Color operator_star(Color c2) {
        Color result = new Color(red * c2.red, green * c2.green, blue * c2.blue);
        return result;
    }

    Color multiplyScalar(double f) {
        Color result = new Color(red * f, green * f, blue * f);
        return result;
    }

    Color blend(Color c2, double w) {
        Color result = this.multiplyScalar(1.0 - w).operator_plus(c2.multiplyScalar(w));
        return result;
    }

    int brightness() {
        int r = (int) (this.red * 255);
        int g = (int) (this.green * 255);
        int b = (int) (this.blue * 255);
        return (r * 77 + g * 150 + b * 29) >> 8;
    }

    public String toString() {
        int r = (int) (this.red * 255);
        int g = (int) (this.green * 255);
        int b = (int) (this.blue * 255);

        return "rgb(" + r + "," + g + "," + b + ")";
    }
}
