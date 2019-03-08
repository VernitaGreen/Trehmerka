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

public class DrawOBJSkeleton {
  public static void main(String[] args) {
    String name = "deer";
    BufferedImage image = new BufferedImage(2000, 3000, BufferedImage.TYPE_3BYTE_BGR);
    try {
//      drawStar(new Point2DInt(100, 100), 80, 15, image, 0);
//      drawStar(new Point2DInt(300, 100), 80, 15, image, 1);
//      drawStar(new Point2DInt(500, 100), 80, 15, image, 2);
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

      double[][] zbuffer = new double[n][m];
      for (int i = 0; i < n; i++) {
        Arrays.fill(zbuffer[i], Double.MAX_VALUE);
      }

      for (int i = 0; i < polygons.size(); i++) {

        Point2DInt a = new Point2DInt((int) (polygons.get(i).a.x + delta + 300), m - (int) (polygons.get(i).a.y + delta));
        Point2DInt b = new Point2DInt((int) (polygons.get(i).b.x + delta + 300), m - (int) (polygons.get(i).b.y + delta));
        Point2DInt c = new Point2DInt((int) (polygons.get(i).c.x + delta + 300), m - (int) (polygons.get(i).c.y + delta));

        DrawLine.drawWuLine(a, b, image);
        DrawLine.drawWuLine(b, c, image);
        DrawLine.drawWuLine(c, a, image);
      }
      File outputfile = new File(name + "_skeleton.png");
      try {
        ImageIO.write(image, "png", outputfile);
      } catch (IOException e) {
        e.printStackTrace();
      }
      System.out.println("privet");
    } catch (Exception e) {
      e.printStackTrace();
    }

    File outputfile = new File("saved.png");
    try {
      ImageIO.write(image, "png", outputfile);
    } catch (
        IOException e) {
      e.printStackTrace();
    }
    System.out.println("privet");
  }

  static void drawStar(Point2DInt a, int r, int n, BufferedImage image, int method) {
    drawStar(a.x, a.y, r, n, image, method);
  }

  /**
   * @param x0     центр звезды по оси x
   * @param y0     центр звезды по оси y
   * @param r      радиус звезды
   * @param n      количество лучей
   * @param method 0 (базовый), 1 (Брезензем), 2 (Ву)
   */
  static void drawStar(int x0, int y0, int r, int n, BufferedImage image, int method) {
//    по определению синуса и косинуса найдем концы лучей
    int[] x = new int[n];
    for (int i = 0; i < n; i++) {
      x[i] = x0 + (int) (Math.cos(2 * Math.PI / n * i) * r);
    }
    int[] y = new int[n];
    for (int i = 0; i < n; i++) {
      y[i] = y0 + (int) (Math.sin(2 * Math.PI / n * i) * r);
    }
//    нарисуем каждый луч отдельно
    for (int i = 0; i < n; i++) {
      switch (method) {
        case 0:
          DrawLine.drawBadLine(x0, x[i], y0, y[i], image);
          break;
        case 1:
          DrawLine.drawBresenhamsLine(x0, x[i], y0, y[i], image);
          break;
        case 2:
          DrawLine.drawWuLine(x0, x[i], y0, y[i], image);
          break;
      }
    }
  }
}
