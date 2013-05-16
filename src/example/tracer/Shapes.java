package example.tracer;

//The ray tracer code in this file is written by Adam Burmister. It
//is available in its original form from:
//
//http://labs.flog.co.nz/raytracer/
//
//Ported from the v8 benchmark suite by Google 2012.

//Ported from Dart to Java by Nikolay Botev.
//Dart Source: https://github.com/dart-lang/benchmark_harness/blob/8283bfab9d2427290c03955b7ced5aee4752ca5f/example/Tracer/dart/shapes.dart

abstract class BaseShape {
    final Vector position;
    final Materials material;

    BaseShape(Vector position, Materials material) {
        this.position = position;
        this.material = material;
    }

    abstract IntersectionInfo intersect(Ray ray);

    public String toString() {
        return "BaseShape";
    }
}


class Plane extends BaseShape {
final double d;

Plane(Vector pos, double d, Materials material) {
    super(pos, material);
    this.d = d;
}

IntersectionInfo intersect(Ray ray) {
 IntersectionInfo info = new IntersectionInfo();

 double Vd = this.position.dot(ray.direction);
 if (Vd == 0) return info; // no intersection

 double t = -(this.position.dot(ray.position) + this.d) / Vd;
 if (t <= 0) return info;

 info.shape = this;
 info.isHit = true;
 info.position = ray.position.operator_plus(ray.direction.multiplyScalar(t));
 info.normal = this.position;
 info.distance = t;

 if(this.material.hasTexture){
   Vector vU = new Vector(this.position.y, this.position.z, -this.position.x);
   Vector vV = vU.cross(this.position);
   double u = info.position.dot(vU);
   double v = info.position.dot(vV);
   info.color = this.material.getColor(u,v);
 } else {
   info.color = this.material.getColor(0,0);
 }

 return info;
}

public String toString() {
 return String.format("Plane [%s, d=%f]", position, d);
}
}


class Sphere extends BaseShape {
double radius;
Sphere(Vector pos, double radius, Materials material) {
    super(pos, material);
    this.radius = radius;
}

IntersectionInfo intersect(Ray ray){
    IntersectionInfo info = new IntersectionInfo();
    info.shape = this;

 Vector dst = ray.position.operator_minus(this.position);

 double B = dst.dot(ray.direction);
 double C = dst.dot(dst) - (this.radius * this.radius);
 double D = (B * B) - C;

 if (D > 0) { // intersection!
   info.isHit = true;
   info.distance = (-B) - Math.sqrt(D);
   info.position = ray.position.operator_plus
       (ray.direction.multiplyScalar(info.distance));
   info.normal = (info.position.operator_minus(this.position)).normalize();

   info.color = this.material.getColor(0,0);
 } else {
   info.isHit = false;
 }
 return info;
}

public String toString() {
 return String.format("Sphere [position=%s, radius=%f]", position, radius);
}
}

public class Shapes {
    // Dummy
}

