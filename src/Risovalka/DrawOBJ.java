package Risovalka;

import Geoma.Geoma;
import base.*;
import base.Polygon;
import obj.ObjReader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DrawOBJ {
  public static void main(String[] args) {
    Random rnd = new Random();
    String name = "deer";
//    String name = "millenium-falcon";
//    String name = "african_head";
    try {
      double max = 0;
      double min = Double.MAX_VALUE;
      List<Polygon> polygons = ObjReader.parseFile("obj/" + name + ".obj");
      System.out.println(polygons.size());
      for (int i = 0; i < polygons.size(); i++) {

        max = Math.max(Math.max(max, polygons.get(i).a.x), Math.max(polygons.get(i).a.y, polygons.get(i).a.z));
        max = Math.max(Math.max(max, polygons.get(i).b.x), Math.max(polygons.get(i).b.y, polygons.get(i).b.z));
        max = Math.max(Math.max(max, polygons.get(i).c.x), Math.max(polygons.get(i).c.y, polygons.get(i).c.z));

        min = Math.min(Math.min(min, polygons.get(i).a.x), Math.min(polygons.get(i).a.y, polygons.get(i).a.z));
        min = Math.min(Math.min(min, polygons.get(i).b.x), Math.min(polygons.get(i).b.y, polygons.get(i).b.z));
        min = Math.min(Math.min(min, polygons.get(i).c.x), Math.min(polygons.get(i).c.y, polygons.get(i).c.z));
      }

      System.out.println(min + " " + max);
      int delta = (int) Math.abs(min) + 100;
      int n = 2000;
      int m = 3000;

      BufferedImage image = new BufferedImage(n, m, BufferedImage.TYPE_3BYTE_BGR);
      double[][] zbuffer = new double[n][m];
      for (int i = 0; i < n; i++) {
        Arrays.fill(zbuffer[i], Double.MAX_VALUE);
      }

      for (int i = 0; i < polygons.size(); i++) {


        Vector camera = new Vector(0, 0, 1);

        Point2DInt a = new Point2DInt((int) (polygons.get(i).a.x + delta + 300), m - (int) (polygons.get(i).a.y + delta));
        Point2DInt b = new Point2DInt((int) (polygons.get(i).b.x + delta + 300), m - (int) (polygons.get(i).b.y + delta));
        Point2DInt c = new Point2DInt((int) (polygons.get(i).c.x + delta + 300), m - (int) (polygons.get(i).c.y + delta));

        Vector va = G.subtract(polygons.get(i).a, polygons.get(i).c);
        Vector vb = G.subtract(polygons.get(i).b, polygons.get(i).c);

        // нормаль
        Vector norm = new Vector(va.y * vb.z - va.z * vb.y,
            va.z * vb.x - va.x * vb.z, va.x * vb.y - va.y * vb.x);
        double p = norm.x * camera.x + norm.y * camera.y + norm.z * camera.z;
        p /= G.norm(norm);
        p /= G.norm(camera);
        double angle = Math.acos(p);
        if (angle < Math.PI / 2 - 1e-2) {
          continue;
        }

        int left = (int) Math.floor(Math.min(Math.min(a.x, b.x), c.x));
        int right = (int) Math.ceil(Math.max(Math.max(a.x, b.x), c.x));
        int bot = (int) Math.floor(Math.min(Math.min(a.y, b.y), c.y));
        int top = (int) Math.ceil(Math.max(Math.max(a.y, b.y), c.y));

        for (int x = left; x < right; x++) {
          for (int y = bot; y < top; y++) {
            double lambda0 = (1.0 * (y - c.y) * (b.x - c.x) - (x - c.x) * (b.y - c.y)) /
                ((a.y - c.y) * (b.x - c.x) - (a.x - c.x) * (b.y - c.y));
            double lambda1 = (1.0 * (y - a.y) * (c.x - a.x) - (x - a.x) * (c.y - a.y)) /
                ((b.y - a.y) * (c.x - a.x) - (b.x - a.x) * (c.y - a.y));
            double lambda2 = (1.0 * (y - b.y) * (a.x - b.x) - (x - b.x) * (a.y - b.y)) /
                ((c.y - b.y) * (a.x - b.x) - (c.x - b.x) * (a.y - b.y));

            if (lambda0 < 0 || lambda1 < 0 || lambda2 < 0) {
              continue;
            }

            double z = (polygons.get(i).a.z + delta) * lambda0
                + (polygons.get(i).b.z + delta) * lambda1 + (polygons.get(i).c.z + delta) * lambda2;

            if (z < zbuffer[x][y]) {
              zbuffer[x][y] = z;
              int color = (int) Math.min(255, (255 * (angle / Math.PI)));
//              int color = 255;
              image.setRGB(x, y, new Color(color, color, color).getRGB());
            }
          }
        }
      }
      File outputfile = new File(name + ".png");
      try {
        ImageIO.write(image, "png", outputfile);
      } catch (IOException e) {
        e.printStackTrace();
      }
      System.out.println("privet");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
