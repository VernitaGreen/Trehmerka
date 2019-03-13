package base;

import java.util.Objects;

public class Vector {
  public static final Vector zero = new Vector(0, 0, 0);
  public static final Vector oX = new Vector(1, 0, 0);
  public static final Vector oY = new Vector(0, 1, 0);
  public static final Vector oZ = new Vector(0, 0, 1);

  public final double x;
  public final double y;
  public final double z;

  public Vector(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Vector vector = (Vector) o;
    return G.doubleEquality(x, vector.x)
        && G.doubleEquality(y, vector.y)
        && G.doubleEquality(z, vector.z);
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y, z);
  }

  @Override
  public String toString() {
    return String.format("{%+.2f,%+.2f,%+.2f}", x, y, z);
  }
}
