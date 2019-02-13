package Risovalka;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;

public class DrawLine {
  static void drawBadLine(int x0, int x1, int y0, int y1, BufferedImage image) {
    boolean steep = false;
//    смотрим наклон прямой
    if (Math.abs(x0 - x1) < Math.abs(y0 - y1)) {
      steep = true;
      int tmp = x0;
      x0 = y0;
      y0 = tmp;
      tmp = x1;
      x1 = y1;
      y1 = tmp;
    }
//    смотрим направление прямой относительно х
    if (x0 > x1) {
      int tmp = x0;
      x0 = x1;
      x1 = tmp;
      tmp = y0;
      y0 = y1;
      y1 = tmp;
    }
    for (int x = x0; x < x1; x++) {
      double tmp = (x - x0) * (y1 - y0);
      int y = (int) Math.round(tmp / (x1 - x0) + y0);
      if (steep) {
        image.setRGB(y, x, Color.WHITE.getRGB());
      } else {
        image.setRGB(x, y, Color.WHITE.getRGB());
      }
    }
  }

  static void drawBresenhamsLine(int x0, int x1, int y0, int y1, BufferedImage image) {
    boolean steep = false;
//    смотрим наклон прямой
    if (Math.abs(x0 - x1) < Math.abs(y0 - y1)) {
      steep = true;
      int tmp = x0;
      x0 = y0;
      y0 = tmp;
      tmp = x1;
      x1 = y1;
      y1 = tmp;
    }
//    смотрим направление прямой относительно х
    if (x0 > x1) {
      int tmp = x0;
      x0 = x1;
      x1 = tmp;
      tmp = y0;
      y0 = y1;
      y1 = tmp;
    }
    int dx = x1 - x0;
    int dy = y1 - y0;
    int error = 0;
    int y = y0;
    int sy = y1 > y0 ? 1 : -1;
    for (int x = x0; x < x1; x++) {
      if (steep) {
        image.setRGB(y, x, Color.WHITE.getRGB());
      } else {
        image.setRGB(x, y, Color.WHITE.getRGB());
      }
      error += Math.abs(dy);
      if (2 * error > Math.abs(dx)) {
        y += sy;
        error -= dx;
      }
    }
  }

  static void drawBresenhamsCircle(int x0, int y0, int R) {

  }

  static void drawWuLine(int x0, int x1, int y0, int y1, BufferedImage image) {
    boolean steep = false;
//    смотрим наклон прямой
    if (Math.abs(x0 - x1) < Math.abs(y0 - y1)) {
      steep = true;
      int tmp = x0;
      x0 = y0;
      y0 = tmp;
      tmp = x1;
      x1 = y1;
      y1 = tmp;
    }
//    смотрим направление прямой относительно х
    if (x0 > x1) {
      int tmp = x0;
      x0 = x1;
      x1 = tmp;
      tmp = y0;
      y0 = y1;
      y1 = tmp;
    }
    int dx = x1 - x0;
    int dy = y1 - y0;
    double derror = 1.0 * dy / dx;
    double error = 0;
    int y = y0;
    int sy = y1 > y0 ? 1 : -1;
    for (int x = x0; x < x1; x++) {
      int c1 = (int) (255 * (1 - 0.5 * error));
      int c2 = (int) (255 * Math.min(1, (1.5 * error)));
      if (steep && c1 > new Color(image.getRGB(y, x)).getRed()) {
        image.setRGB(y, x, new Color(c1, c1, c1).getRGB());
      } else if (!steep && c1 > new Color(image.getRGB(x, y)).getRed()) {
        image.setRGB(x, y, new Color(c1, c1, c1).getRGB());
      }
      if (steep && c2 > new Color(image.getRGB(y + sy, x)).getRed()) {
        image.setRGB(y + sy, x, new Color(c2, c2, c2).getRGB());
      } else if (!steep && c2 > new Color(image.getRGB(x, y + sy)).getRed()) {
        image.setRGB(x, y + sy, new Color(c2, c2, c2).getRGB());
      }
      error += Math.abs(derror);
      if (error > 1) {
        y += sy;
        error--;
      }
    }
  }
}
