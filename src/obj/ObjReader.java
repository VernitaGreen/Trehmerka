package obj;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import base.Polygon;
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
    System.out.println(vc);
    System.out.println(fc);

    return polygons;
  }

  public static void main(String[] args) {
    Matcher matcher = vertexPattern.matcher("v 1 2 3");
    System.out.println(matcher.find());
    System.out.println(matcher.group(3));
  }

}
