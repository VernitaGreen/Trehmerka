package obj;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import base.Polygon;
import base.Vector;

public class ObjReaderTest {

//  @Test
//  void testParseString() {
//    List<Polygon> polygons = ObjReader.parseString("" +
//        "v 1 2 3\n" +
//        "v 2 3 4\n" +
//        "v 3 4 5\n" +
//        "f 3 2 1\n");
//
//    assertEquals(1, polygons.size());
//    assertEquals(
//        new Polygon(
//            new Vector(3, 4, 5),
//            new Vector(2, 3, 4),
//            new Vector(1, 2, 3)
//        ),
//        polygons.get(0));
//  }

  @Test
  void testParseFile() throws IOException {
    List<Polygon> polygons;

    polygons = ObjReader.parseFile("obj/african_head.obj");
    assertEquals(2492, polygons.size());

    polygons = ObjReader.parseFile("obj/deer.obj");
    assertEquals(1508, polygons.size());

    polygons = ObjReader.parseFile("obj/millenium-falcon.obj");
    assertEquals(178514, polygons.size());
  }
}
