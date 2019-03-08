package obj;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import base.Polygon;
import base.Util;
import base.Vector;
import io.InputReader;

public class ObjReader {
  static Pattern vertexPattern = Pattern.compile("v\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)");
  static Pattern vertexTexturePattern = Pattern.compile("vt\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)");
  static Pattern vertexNormalPattern = Pattern.compile("vn\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)");
  static Pattern polygonPattern3 = Pattern.compile("f\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)");
  static Pattern polygonPattern4 = Pattern.compile("f\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)");

  public static List<Polygon> parseFile(String filename) {
    InputReader in = new InputReader(filename);

    List<Polygon> polygons = new ArrayList<>();
    List<Vector> vectors = new ArrayList<>();

    Matcher m;
    int vc = 0;
    int fc = 0;

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

        vectors.add(new Vector(x, y, z));
        vc++;
        continue;
      }

      m = polygonPattern3.matcher(line);
      if (m.matches()) {
        int v1_i = Integer.parseInt(m.group(1).split("/")[0]);
        int v2_i = Integer.parseInt(m.group(2).split("/")[0]);
        int v3_i = Integer.parseInt(m.group(3).split("/")[0]);

        polygons.add(new Polygon(
            vectors.get(v1_i - 1),
            vectors.get(v2_i - 1),
            vectors.get(v3_i - 1)
        ));
        fc++;
      }

      m = polygonPattern4.matcher(line);
      if (m.matches()) {
        int v1_i = Integer.parseInt(m.group(1).split("/")[0]);
        int v2_i = Integer.parseInt(m.group(2).split("/")[0]);
        int v3_i = Integer.parseInt(m.group(3).split("/")[0]);
        int v4_i = Integer.parseInt(m.group(4).split("/")[0]);

        polygons.add(new Polygon(
            vectors.get(v1_i - 1),
            vectors.get(v2_i - 1),
            vectors.get(v3_i - 1)
        ));
        polygons.add(new Polygon(
            vectors.get(v1_i - 1),
            vectors.get(v3_i - 1),
            vectors.get(v4_i - 1)
        ));
        fc += 2;
      }
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
          scaleShift01Vector(polygon.c, minX, minY, minZ, maxX, maxY, maxZ)
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
