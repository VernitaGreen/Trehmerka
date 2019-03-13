package Risovalka;

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

      List<Polygon> polygons = ObjReader.parseFile("obj/" + name + ".obj");
      System.out.println(polygons.size());

      int n = 2000;

      BufferedImage image = new BufferedImage(n, n, BufferedImage.TYPE_INT_RGB);
      double[][] zBuffer = new double[n][n];
      for (int i = 0; i < n; i++) {
        Arrays.fill(zBuffer[i], Double.MAX_VALUE);
      }

      Point3D cameraPosition = new Point3D(0, 0, 0);
      Vector cameraDirection = new Vector(0, 1, 1);

      Vector cameraZ = G.normalize(cameraDirection);
      Vector cameraX = G.normalize(G.vectorProduct(new Vector(0, 1, 0), cameraZ));
      Vector cameraY = G.normalize(G.vectorProduct(cameraX, cameraZ));

      Matrix rotationMatrix = Matrix.rotationMatrix(
          new Vector(1, 0, 0), new Vector(0, 1, 0), new Vector(0, 0, 1),
          cameraX, cameraY, cameraZ);
      Matrix t = new Matrix(new double[][]{{-cameraPosition.rv.x}, {-cameraPosition.rv.y}, {-cameraPosition.rv.y}});
      Matrix Rt = Matrix.concatenateJ(rotationMatrix, t);

      double[][] k = new double[3][3];
      k[0][0] = n;
      k[0][2] = n;
      k[1][1] = n;
      k[1][2] = n;
      k[2][2] = 1;
      Matrix K = new Matrix(k);

      Matrix transformationMatrix = Matrix.multiply(K, Rt);

      Point2D center = new Point2D(1.0 * n / 2, 1.0 * n / 2);

      for (Polygon polygon : polygons) {

        Matrix a = Matrix.concatenateI(Matrix.fromVector(polygon.a), Matrix.eye(1));
        Matrix b = Matrix.concatenateI(Matrix.fromVector(polygon.b), Matrix.eye(1));
        Matrix c = Matrix.concatenateI(Matrix.fromVector(polygon.c), Matrix.eye(1));

        a = Matrix.multiply(transformationMatrix, a);
        b = Matrix.multiply(transformationMatrix, b);
        c = Matrix.multiply(transformationMatrix, c);

        Point2DInt a2d = new Point2DInt((int) Math.round(a.get(0, 0)), (int) Math.round(a.get(1, 0)));
        Point2DInt b2d = new Point2DInt((int) Math.round(b.get(0, 0)), (int) Math.round(b.get(1, 0)));
        Point2DInt c2d = new Point2DInt((int) Math.round(c.get(0, 0)), (int) Math.round(c.get(1, 0)));

        Vector va = G.subtract(polygon.a, polygon.c);
        Vector vb = G.subtract(polygon.b, polygon.c);

        // нормаль
        Vector normal = G.vectorProduct(va, vb);
        double p = G.dotProduct(normal, cameraDirection);
        p /= G.norm(normal);
        p /= G.norm(cameraDirection);
        double angle = Math.acos(p);
        if (angle < Math.PI / 2 - 1e-2) {
          continue;
        }

        int left = Util.min(a2d.x, b2d.x, c2d.x);
        int right = Util.max(a2d.x, b2d.x, c2d.x);
        int bot = Util.min(a2d.y, b2d.y, c2d.y);
        int top = Util.max(a2d.y, b2d.y, c2d.y);

        Vector rotatedA =

        for (int x = left; x < right; x++) {
          for (int y = bot; y < top; y++) {
            double lambda0 = (1.0 * (y - c2d.y) * (b2d.x - c2d.x) - (x - c2d.x) * (b2d.y - c2d.y)) /
                ((a2d.y - c2d.y) * (b2d.x - c2d.x) - (a2d.x - c2d.x) * (b2d.y - c2d.y));
            double lambda1 = (1.0 * (y - a2d.y) * (c2d.x - a2d.x) - (x - a2d.x) * (c2d.y - a2d.y)) /
                ((b2d.y - a2d.y) * (c2d.x - a2d.x) - (b2d.x - a2d.x) * (c2d.y - a2d.y));
            double lambda2 = (1.0 * (y - b2d.y) * (a2d.x - b2d.x) - (x - b2d.x) * (a2d.y - b2d.y)) /
                ((c2d.y - b2d.y) * (a2d.x - b2d.x) - (c2d.x - b2d.x) * (a2d.y - b2d.y));

            if (lambda0 < 0 || lambda1 < 0 || lambda2 < 0) {
              continue;
            }

            double z = (polygon.a.x * n) * lambda0
                + (polygon.b.x * n) * lambda1 + (polygon.c.x * n) * lambda2;

            if (z < zBuffer[x][y]) {
              zBuffer[x][y] = z;
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
