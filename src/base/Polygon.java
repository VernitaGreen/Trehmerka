package base;

import java.util.Objects;

public class Polygon {
  public final Vector a;
  public final Vector b;
  public final Vector c;

  public Polygon(Vector a, Vector b, Vector c) {
    this.a = a;
    this.b = b;
    this.c = c;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Polygon polygon = (Polygon) o;
    return Objects.equals(a, polygon.a) &&
        Objects.equals(b, polygon.b) &&
        Objects.equals(c, polygon.c);
  }

  @Override
  public int hashCode() {
    return Objects.hash(a, b, c);
  }
}
