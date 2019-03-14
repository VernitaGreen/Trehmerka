package obj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import base.G;
import base.Polygon;
import base.Util;
import base.Vector;
import io.InputReader;

public class ObjReader {
  static Pattern vertexPattern = Pattern.compile("v\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)");
  static Pattern normalVectorPattern = Pattern.compile("vn\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)");
  static Pattern polygonPattern3 = Pattern.compile("f\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)");
  static Pattern polygonPattern4 = Pattern.compile("f\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)");

  static Pattern vertexTexturePattern = Pattern.compile("vt\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)");

  static class PolygonIndices {
    public final Integer a;
    public final Integer b;
    public final Integer c;

    public final Integer textureA;
    public final Integer textureB;
    public final Integer textureC;

    public final Integer normalA;
    public final Integer normalB;
    public final Integer normalC;

    PolygonIndices(String a, String b, String c) {
      String[] partsA = a.split("/");
      String[] partsB = b.split("/");
      String[] partsC = c.split("/");

      this.a = Integer.parseInt(partsA[0]) - 1;
      this.b = Integer.parseInt(partsB[0]) - 1;
      this.c = Integer.parseInt(partsC[0]) - 1;

      if (partsA.length >= 2) {
        this.textureA = Integer.parseInt(partsA[1]) - 1;
        this.textureB = Integer.parseInt(partsB[1]) - 1;
        this.textureC = Integer.parseInt(partsC[1]) - 1;
      } else {
        this.textureA = null;
        this.textureB = null;
        this.textureC = null;
      }

      if (partsA.length >= 3) {
        this.normalA = Integer.parseInt(partsA[2]) - 1;
        this.normalB = Integer.parseInt(partsB[2]) - 1;
        this.normalC = Integer.parseInt(partsC[2]) - 1;
      } else {
        this.normalA = null;
        this.normalB = null;
        this.normalC = null;
      }
    }
  }

  public static List<Polygon> parseFile(String filename) {
    InputReader in = new InputReader(filename);

    List<Vector> vertices = new ArrayList<>();
    List<Vector> normalVectors = new ArrayList<>();
    List<PolygonIndices> polygonIndices = new ArrayList<>();

    Matcher m;

    while (true) {
      String line = in.nextLine();

      if (line == null) {
        break;
      }

      line = line.trim();

      m = vertexPattern.matcher(line);
      if (m.matches()) {
        double x = Double.parseDouble(m.group(1));
        double y = Double.parseDouble(m.group(2));
        double z = Double.parseDouble(m.group(3));

        vertices.add(new Vector(x, y, z));
        continue;
      }

      m = normalVectorPattern.matcher(line);
      if (m.matches()) {
        double x = Double.parseDouble(m.group(1));
        double y = Double.parseDouble(m.group(2));
        double z = Double.parseDouble(m.group(3));

        normalVectors.add(new Vector(x, y, z));
        continue;
      }

      m = polygonPattern3.matcher(line);
      if (m.matches()) {
        polygonIndices.add(new PolygonIndices(
            m.group(1),
            m.group(2),
            m.group(3)
        ));
      }

      m = polygonPattern4.matcher(line);
      if (m.matches()) {
        polygonIndices.add(new PolygonIndices(
            m.group(1),
            m.group(2),
            m.group(3)
        ));
        polygonIndices.add(new PolygonIndices(
            m.group(1),
            m.group(2),
            m.group(4)
        ));
      }
    }

    Map<Integer, List<Integer>> vertexToTouchingPolygons = new HashMap<>();
    for (int i = 0; i < vertices.size(); i++) {
      vertexToTouchingPolygons.put(i, new ArrayList<>());
    }

    List<Vector> computedPolygonNormals = new ArrayList<>();
    for (int i = 0; i < polygonIndices.size(); i++) {
      PolygonIndices poly = polygonIndices.get(i);
      vertexToTouchingPolygons.get(poly.a).add(i);
      vertexToTouchingPolygons.get(poly.b).add(i);
      vertexToTouchingPolygons.get(poly.c).add(i);

      Vector a = vertices.get(poly.a);
      Vector b = vertices.get(poly.b);
      Vector c = vertices.get(poly.c);

      Vector ab = G.subtract(b, a);
      Vector ac = G.subtract(c, a);
      Vector normal = G.vectorProduct(ab, ac);
      normal = G.normalize(normal);

      computedPolygonNormals.add(normal);
    }

    List<Vector> computedVertexNormals = new ArrayList<>();
    for (int i = 0; i < vertices.size(); i++) {
      Vector normal = Vector.zero;

      List<Integer> touchingPolygons = vertexToTouchingPolygons.get(i);

      for (Integer touchingPolygonIndex : touchingPolygons) {
        normal = G.add(normal, computedPolygonNormals.get(touchingPolygonIndex));
      }

      if (G.isZero(normal)) {
        System.out.println("BAD");
        normal = Vector.oX;
      }

      normal = G.normalize(normal);
      computedVertexNormals.add(normal);
    }

    List<Polygon> polygons = new ArrayList<>();

    for (PolygonIndices poly : polygonIndices) {
      Vector a = vertices.get(poly.a);
      Vector b = vertices.get(poly.b);
      Vector c = vertices.get(poly.c);

      Vector aNormal = computedVertexNormals.get(poly.a);
      Vector bNormal = computedVertexNormals.get(poly.b);
      Vector cNormal = computedVertexNormals.get(poly.c);

      if (poly.normalA != null) {
        aNormal = normalVectors.get(poly.a);
        bNormal = normalVectors.get(poly.b);
        cNormal = normalVectors.get(poly.c);
      }

      polygons.add(new Polygon(
          a, b, c, aNormal, bNormal, cNormal
      ));
    }

    return scaleShift01(polygons);
  }

  private static Vector scaleShift01Vector(Vector v,
                                           double minX, double minY, double minZ,
                                           double maxX, double maxY, double maxZ) {
    double factorX = maxX - minX;
    double factorY = maxY - minY;
    double factorZ = maxZ - minZ;

    double maxFactor = Util.max(factorX, factorY, factorZ);
    if (maxFactor == 0) {
      maxFactor = 1;
    }

    double newX = (v.x - minX) / maxFactor + 0.5 - factorX / maxFactor / 2;
    double newY = (v.y - minY) / maxFactor + 0.5 - factorY / maxFactor / 2;
    double newZ = (v.z - minZ) / maxFactor + 0.5 - factorZ / maxFactor / 2;

    return new Vector(newX, newY, newZ);
  }

  private static List<Polygon> scaleShift01(List<Polygon> polygons) {
    double minX = polygons.get(0).a.x;
    double minY = polygons.get(0).a.y;
    double minZ = polygons.get(0).a.z;
    double maxX = minX;
    double maxY = minY;
    double maxZ = minZ;

    for (Polygon polygon : polygons) {
      minX = Math.min(minX, polygon.a.x);
      minY = Math.min(minY, polygon.a.y);
      minZ = Math.min(minZ, polygon.a.z);

      minX = Math.min(minX, polygon.b.x);
      minY = Math.min(minY, polygon.b.y);
      minZ = Math.min(minZ, polygon.b.z);

      minX = Math.min(minX, polygon.c.x);
      minY = Math.min(minY, polygon.c.y);
      minZ = Math.min(minZ, polygon.c.z);

      maxX = Math.max(maxX, polygon.a.x);
      maxY = Math.max(maxY, polygon.a.y);
      maxZ = Math.max(maxZ, polygon.a.z);

      maxX = Math.max(maxX, polygon.b.x);
      maxY = Math.max(maxY, polygon.b.y);
      maxZ = Math.max(maxZ, polygon.b.z);

      maxX = Math.max(maxX, polygon.c.x);
      maxY = Math.max(maxY, polygon.c.y);
      maxZ = Math.max(maxZ, polygon.c.z);
    }

    List<Polygon> result = new ArrayList<>();

    for (Polygon polygon : polygons) {
      result.add(new Polygon(
          scaleShift01Vector(polygon.a, minX, minY, minZ, maxX, maxY, maxZ),
          scaleShift01Vector(polygon.b, minX, minY, minZ, maxX, maxY, maxZ),
          scaleShift01Vector(polygon.c, minX, minY, minZ, maxX, maxY, maxZ),
          G.normalize(polygon.aNormal),
          G.normalize(polygon.bNormal),
          G.normalize(polygon.cNormal)
      ));
    }

    return result;
  }

  public static void main(String[] args) {
    Matcher matcher = vertexPattern.matcher("v 1 2 3");
    System.out.println(matcher.find());
    System.out.println(matcher.group(3));
  }

}
