package Risovalka;

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
    BufferedImage image = new BufferedImage(600, 200, BufferedImage.TYPE_BYTE_GRAY);
    String name = "deer";
    try {
      List<Polygon> polygons = ObjReader.parseFile("obj/" + name + ".obj");
      for (int i = 0; i < Math.min(10, polygons.size()); i++) {
        System.out.println(polygons.get(i).toString());

      }
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
