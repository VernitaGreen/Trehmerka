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
//    String name = "deer";
    String name = "millenium-falcon";
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

      Point3D cameraPosition = new Point3D(0, 0, 1);
      Vector cameraDirection = new Vector(1, 1, -1);
      cameraDirection = G.normalize(cameraDirection);

      Vector cameraZ = cameraDirection;
      Vector cameraX = G.normalize(G.vectorProduct(cameraZ, Vector.oY));
      Vector cameraY = G.normalize(G.vectorProduct(cameraZ, cameraX));

      Matrix R = Matrix.rotationMatrix(
          Vector.oX, Vector.oY, Vector.oZ,
          cameraX, cameraY, cameraZ);
      Matrix C = Matrix.fromVector(cameraPosition.rv);
      Matrix t = Matrix.multiply(R, Matrix.multiply(C, -1));

      Matrix Rt = Matrix.concatenateJ(R, t);

      double f = n / 2.0;
      Matrix K = new Matrix(new double[][]{
          {f, 0, n / 2.0},
          {0, f, n / 2.0},
          {0, 0, 1},
      });

      for (Polygon polygon : polygons) {
        Matrix aOriginal = Matrix.concatenateI(Matrix.fromVector(polygon.a), Matrix.eye(1));
        Matrix bOriginal = Matrix.concatenateI(Matrix.fromVector(polygon.b), Matrix.eye(1));
        Matrix cOriginal = Matrix.concatenateI(Matrix.fromVector(polygon.c), Matrix.eye(1));

        Vector aNormalRotated = Matrix.toVector(Matrix.multiply(R, Matrix.fromVector(polygon.aNormal)));
        Vector bNormalRotated = Matrix.toVector(Matrix.multiply(R, Matrix.fromVector(polygon.bNormal)));
        Vector cNormalRotated = Matrix.toVector(Matrix.multiply(R, Matrix.fromVector(polygon.cNormal)));

        Matrix aRotatedShifted = Matrix.multiply(Rt, aOriginal);
        Matrix bRotatedShifted = Matrix.multiply(Rt, bOriginal);
        Matrix cRotatedShifted = Matrix.multiply(Rt, cOriginal);

        Matrix aProjected = Matrix.multiply(K, aRotatedShifted);
        Matrix bProjected = Matrix.multiply(K, bRotatedShifted);
        Matrix cProjected = Matrix.multiply(K, cRotatedShifted);

        aProjected = Matrix.multiply(aProjected, 1 / aProjected.get(2, 0));
        bProjected = Matrix.multiply(bProjected, 1 / bProjected.get(2, 0));
        cProjected = Matrix.multiply(cProjected, 1 / cProjected.get(2, 0));

        Point2DInt a2d = new Point2DInt((int) Math.round(aProjected.get(0, 0)), (int) Math.round(aProjected.get(1, 0)));
        Point2DInt b2d = new Point2DInt((int) Math.round(bProjected.get(0, 0)), (int) Math.round(bProjected.get(1, 0)));
        Point2DInt c2d = new Point2DInt((int) Math.round(cProjected.get(0, 0)), (int) Math.round(cProjected.get(1, 0)));

        Vector va = G.subtract(polygon.a, polygon.c);
        Vector vb = G.subtract(polygon.b, polygon.c);


        int left = Util.max(Util.min(a2d.x, b2d.x, c2d.x), 0);
        int right = Util.min(Util.max(a2d.x, b2d.x, c2d.x), n);
        int bottom = Util.max(Util.min(a2d.y, b2d.y, c2d.y), 0);
        int top = Util.min(Util.max(a2d.y, b2d.y, c2d.y), n);

        for (int x = left; x < right; x++) {
          for (int y = bottom; y < top; y++) {
            double lambda0 =
                (
                    1.0 * (y - c2d.y) * (b2d.x - c2d.x)
                        - (x - c2d.x) * (b2d.y - c2d.y)
                ) / (
                    (a2d.y - c2d.y) * (b2d.x - c2d.x)
                        - (a2d.x - c2d.x) * (b2d.y - c2d.y)
                );
            double lambda1 =
                (
                    1.0 * (y - a2d.y) * (c2d.x - a2d.x) - (x - a2d.x) * (c2d.y - a2d.y)
                ) / (
                    (b2d.y - a2d.y) * (c2d.x - a2d.x)
                        - (b2d.x - a2d.x) * (c2d.y - a2d.y)
                );
            double lambda2 =
                (
                    1.0 * (y - b2d.y) * (a2d.x - b2d.x)
                        - (x - b2d.x) * (a2d.y - b2d.y)
                ) / (
                    (c2d.y - b2d.y) * (a2d.x - b2d.x)
                        - (c2d.x - b2d.x) * (a2d.y - b2d.y)
                );

            if (lambda0 < 0 || lambda1 < 0 || lambda2 < 0) {
              continue;
            }

            double z = aRotatedShifted.get(2, 0) * lambda0
                + bRotatedShifted.get(2, 0) * lambda1
                + cRotatedShifted.get(2, 0) * lambda2;

            if (z < zBuffer[x][y]) {
              Vector normal = Vector.zero;
              normal = G.add(normal, G.scale(aNormalRotated, lambda0));
              normal = G.add(normal, G.scale(bNormalRotated, lambda1));
              normal = G.add(normal, G.scale(cNormalRotated, lambda2));

              if (G.isZero(normal)) {
                continue;
              }
              normal = G.normalize(normal);

              double p = G.dotProduct(normal, G.scale(Vector.oZ, -1));
//              double angle = Math.acos(p);
//              if (angle < Math.PI / 2 - 1e-2) {
//                continue;
//              }
              if (p < 0) {
                continue;
              }

              zBuffer[x][y] = z;
//              int color = (int) Math.min(255, (255 * (angle / Math.PI)));
              int color = (int) (255 * p);
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
