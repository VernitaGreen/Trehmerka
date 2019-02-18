package base;

public class Line {
//  TODO
  Vector a;
  Vector b;

  public Line(Vector a, Vector b) {
    this.a = a;
    this.b = b;
  }

  public boolean isPointBelonging(Vector point) {
    double dx1 = b.x - a.x;
    double dy1 = b.y - a.y;
    double dz1 = b.z - a.z;

    double dx2 = point.x - a.x;
    double dy2 = point.y - a.y;
    double dz2 = point.z - a.z;

    double eps = 1e-5;

    double c1 = dx1 * dy2 * dz2;
    double c2 = dx2 * dy1 * dz2;
    double c3 = dx2 * dy2 * dz1;

    return Math.abs(c1 - c2) < eps && Math.abs(c2 - c3) < eps;
  }


}
