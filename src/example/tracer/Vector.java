package example.tracer;

//The ray tracer code in this file is written by Adam Burmister. It
//is available in its original form from:
//
//http://labs.flog.co.nz/raytracer/
//
//Ported from the v8 benchmark suite by Google 2012.

//Ported from Dart to Java by Nikolay Botev.
//Dart Source: https://github.com/dart-lang/benchmark_harness/blob/8283bfab9d2427290c03955b7ced5aee4752ca5f/example/Tracer/dart/vector.dart

public class Vector {
    double x, y, z;
    Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    void copy(Vector v) {
      this.x = v.x;
      this.y = v.y;
      this.z = v.z;
    }

    Vector normalize() {
      double m = this.magnitude();
      return new Vector(this.x / m, this.y / m, this.z / m);
    }

    double magnitude() {
      return Math.sqrt((this.x * this.x) + (this.y * this.y) + (this.z * this.z));
    }

    Vector cross(Vector w) {
      return new Vector(-this.z * w.y + this.y * w.z,
                        this.z * w.x - this.x * w.z,
                        -this.y * w.x + this.x * w.y);
    }

    double dot(Vector w) {
      return this.x * w.x + this.y * w.y + this.z * w.z;
    }

    Vector operator_plus(Vector w) {
      return new Vector(w.x + x, w.y + y, w.z + z);
    }

    Vector operator_minus(Vector w) {
      return new Vector(x - w.x, y - w.y, z - w.z);
    }

    Vector operator_star(Vector w) {
      return new Vector(x * w.x, y * w.y, z * w.z);
    }

    Vector multiplyScalar(double w) {
      return new Vector(x * w, y * w, z * w);
    }

    public String toString() {
      return "Vector [" + x + ", " + y + " ," + z + " ]";
    }
  }
