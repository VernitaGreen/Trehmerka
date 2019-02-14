package base;

import java.util.Objects;

public class Point2DInt {
  public final int x;
  public final int y;

  public Point2DInt(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public Point2DInt add(Point2DInt o) {
    return new Point2DInt(x + o.x, y + o.y);
  }

  public double norm() {
    return Math.sqrt(x * x + y * y);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Point2DInt point2DInt = (Point2DInt) o;
    return x == point2DInt.x &&
        y == point2DInt.y;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }

  @Override
  public String toString() {
    return "Point2DInt{" +
        "x=" + x +
        ", y=" + y +
        '}';
  }
}
