package misc;

import base.Plane;
import base.Ray;
import base.Vector;

public class Test {
  public static void main(String[] args) {
    Plane plane = new Plane(Vector.zero, new Vector(0, 0, 1));

    System.out.println(
        new Ray(new Vector(0, 0, 1), new Vector(1, 0, -1))
            .intersect(plane));
    System.out.println(
        new Ray(new Vector(0, 0, -1), new Vector(1, 0, 1))
            .intersect(plane));
    System.out.println(
        new Ray(new Vector(0, 0, 0), new Vector(1, 0, 1))
            .intersect(plane));
  }
}
