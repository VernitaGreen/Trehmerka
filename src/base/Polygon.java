package base;

import java.util.Objects;

public class Polygon {
  public final Vector a;
  public final Vector b;
  public final Vector c;

  public final Vector aNormal;
  public final Vector bNormal;
  public final Vector cNormal;

  public Polygon(Vector a, Vector b, Vector c) {
    this.a = a;
    this.b = b;
    this.c = c;

    this.aNormal = null;
    this.bNormal = null;
    this.cNormal = null;
  }

  public Polygon(Vector a, Vector b, Vector c, Vector aNormal, Vector bNormal, Vector cNormal) {
    this.a = a;
    this.b = b;
    this.c = c;

    this.aNormal = aNormal;
    this.bNormal = bNormal;
    this.cNormal = cNormal;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Polygon polygon = (Polygon) o;
    return Objects.equals(a, polygon.a) &&
        Objects.equals(b, polygon.b) &&
        Objects.equals(c, polygon.c) &&
        Objects.equals(aNormal, polygon.aNormal) &&
        Objects.equals(bNormal, polygon.bNormal) &&
        Objects.equals(cNormal, polygon.cNormal);
  }

  @Override
  public int hashCode() {
    return Objects.hash(a, b, c, aNormal, bNormal, cNormal);
  }
}
