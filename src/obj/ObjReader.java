package obj;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import base.Polygon;
import base.Vector;

public class ObjReader {
  static Pattern vertexPattern = Pattern.compile("v (\\S+) (\\S+) (\\S+)");
  static Pattern vertexTexturePattern = Pattern.compile("vt (\\S+) (\\S+) (\\S+)");
  static Pattern vertexNormalPattern = Pattern.compile("vn (\\S+) (\\S+) (\\S+)");
  static Pattern polygonPattern = Pattern.compile("f (\\S+) (\\S+) (\\S+)");

  public static List<Polygon> parseFile(String filename) throws IOException {
    byte[] encoded = Files.readAllBytes(Paths.get(filename));
    return parseString(new String(encoded, StandardCharsets.UTF_8));
  }

  public static List<Polygon> parseString(String s) {
    List<Polygon> polygons = new ArrayList<>();
    List<Vector> vectors = new ArrayList<>();

    Matcher m;

    for (String line : s.split("\n")) {
      line = line.trim();

      m = vertexPattern.matcher(line);
      if (m.matches()) {
        try {
          double x = Double.parseDouble(m.group(1));
          double y = Double.parseDouble(m.group(2));
          double z = Double.parseDouble(m.group(3));

          vectors.add(new Vector(x, y, z));
        } catch (Exception e) {
          e.printStackTrace();
        }
        continue;
      }

      m = polygonPattern.matcher(line);
      if (m.matches()) {
        try {
          int x_i = Integer.parseInt(m.group(1).split("/")[0]);
          int y_i = Integer.parseInt(m.group(2).split("/")[0]);
          int z_i = Integer.parseInt(m.group(3).split("/")[0]);

          polygons.add(new Polygon(
              vectors.get(x_i - 1),
              vectors.get(y_i - 1),
              vectors.get(z_i - 1)
          ));
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }

    return polygons;
  }

  public static void main(String[] args) {
    Matcher matcher = vertexPattern.matcher("v 1 2 3");
    System.out.println(matcher.find());
    System.out.println(matcher.group(3));
  }

}
