package base;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class GTest {

  @Test
  void planeRayIntersect() {
    Plane plane = new Plane(Vector.zero, new Vector(0, 0, 1));

    assertEquals(
        G.intersect(plane, new Ray(new Vector(0, 0, -1), new Vector(0, 0, 1))),
        new Vector(0, 0, 0));

    assertEquals(
        G.intersect(plane, new Ray(new Vector(0, 0, -1), new Vector(0, 1, 1))),
        new Vector(0, 1, 0));

    assertEquals(
        G.intersect(plane, new Ray(new Vector(0, 0, 1), new Vector(0, 1, -1))),
        new Vector(0, 1, 0));

    assertNull(G.intersect(plane, new Ray(new Vector(0, 0, 1), new Vector(0, 1, 1))));


    assertEquals(
        G.intersect(
            new Plane(new Vector(0.5, 0.5, 0.5), new Vector(1, 1, 1)),
            new Ray(new Vector(0, 0, 0), new Vector(1, 1, 1))),
        new Vector(0.5, 0.5, 0.5));
  }
}
