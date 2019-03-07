package Risovalka;

import base.Point2D;
import base.Point2DInt;
import base.Polygon;
import obj.ObjReader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class DrawOBJ {
  public static void main(String[] args) {
    BufferedImage image = new BufferedImage(3000, 3000, BufferedImage.TYPE_BYTE_GRAY);
//    String name = "deer";
//    String name = "millenium-falcon";
    String name = "african_head";
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
        try {

          Point2DInt a = new Point2DInt((int) (polygons.get(i).a.x * 1000 + 1100), 3000-(int) (polygons.get(i).a.y * 1000 + 1100));
          Point2DInt b = new Point2DInt((int) (polygons.get(i).b.x * 1000 + 1100), 3000-(int) (polygons.get(i).b.y * 1000 + 1100));
          Point2DInt c = new Point2DInt((int) (polygons.get(i).c.x * 1000 + 1100), 3000-(int) (polygons.get(i).c.y * 1000 + 1100));

          DrawLine.drawWuLine(a, b, image);
          DrawLine.drawWuLine(b, c, image);
          DrawLine.drawWuLine(a, c, image);

        } catch (Exception e) {
          e.printStackTrace();
          System.out.println(i + ": " + polygons.get(i).a + " " + polygons.get(i).b + " " + polygons.get(i).c);
        }
      }
      System.out.println(min + " " + max);


    } catch (Exception e) {
      e.printStackTrace();
    }

    File outputfile = new File(name + ".png");
    try {
      ImageIO.write(image, "png", outputfile);
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println("privet");
  }
}
