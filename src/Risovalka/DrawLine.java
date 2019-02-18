package Risovalka;

import base.Point2DInt;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;

public class DrawLine {
  static void drawBadLine(Point2DInt a, Point2DInt b, BufferedImage image) {
    drawBadLine(a.x, b.x, a.y, b.y, image);
  }

  static void drawBadLine(int x0, int x1, int y0, int y1, BufferedImage image) {
//    смотрим наклон прямой, будем итерироваться по более продолжительной оси
    if (Math.abs(x0 - x1) < Math.abs(y0 - y1)) {
      drawBadLine(y0, y1, x0, x1, image, true);
    } else {
      drawBadLine(x0, x1, y0, y1, image, false);
    }
  }

  static private void drawBadLine(int x0, int x1, int y0, int y1, BufferedImage image, boolean steep) {
//    смотрим направление прямой, будем идти слева направо
    if (x0 > x1) {
      drawBadLine(x1, x0, y1, y0, image, steep);
      return;
    }
//    рассматриваем каждую координату на выбранной оси
    for (int x = x0; x < x1; x++) {
//      находим другую координату по уравнению прямой
      double tmp = (x - x0) * (y1 - y0);
      int y = (int) Math.round(tmp / (x1 - x0) + y0);
//      взависимости от наклона прямой красим нужный пиксель
      if (steep) {
        image.setRGB(y, x, Color.WHITE.getRGB());
      } else {
        image.setRGB(x, y, Color.WHITE.getRGB());
      }
    }
  }

  static void drawBresenhamsLine(Point2DInt a, Point2DInt b, BufferedImage image) {
    drawBresenhamsLine(a.x, b.x, a.y, b.y, image);
  }

  static void drawBresenhamsLine(int x0, int x1, int y0, int y1, BufferedImage image) {
//    смотрим наклон прямой, будем итерироваться по более продолжительной оси
    if (Math.abs(x0 - x1) < Math.abs(y0 - y1)) {
      drawBresenhamsLine(y0, y1, x0, x1, image, true);
    } else {
      drawBresenhamsLine(x0, x1, y0, y1, image, false);
    }
  }

  static private void drawBresenhamsLine(int x0, int x1, int y0, int y1, BufferedImage image, boolean steep) {
//    смотрим направление прямой, будем идти слева направо
    if (x0 > x1) {
      drawBresenhamsLine(x1, x0, y1, y0, image, steep);
      return;
    }
//    находим изменение по обеим осям
    int dx = x1 - x0;
    int dy = y1 - y0;
//    будем накапливать ошибку изменения по оси y
    int error = 0;
//    засетим текущий y начальным значением
    int y = y0;
//    и знак изменения текущего y
    int sy = y1 > y0 ? 1 : -1;
//    рассмотрим каждый пиксель оси x
    for (int x = x0; x < x1; x++) {
//      взависимости от наклона прямой красим нужный пиксель
      if (steep) {
        image.setRGB(y, x, Color.WHITE.getRGB());
      } else {
        image.setRGB(x, y, Color.WHITE.getRGB());
      }
//      накапливаем ошибку изменения текущего y
      error += Math.abs(dy);
//      если ошибка перевалила за границу клетки, инкрементим текущий y и уменьшаем ошибку
      if (2 * error > Math.abs(dx)) {
        y += sy;
        error -= dx;
      }
    }
  }

  static void drawBresenhamsCircle(int x0, int y0, int R) {

  }

  static void drawWuLine(Point2DInt a, Point2DInt b, BufferedImage image) {
    drawWuLine(a.x, b.x, a.y, b.y, image);
  }

  static void drawWuLine(int x0, int x1, int y0, int y1, BufferedImage image) {
//    смотрим наклон прямой, будем итерироваться по более продолжительной оси
    if (Math.abs(x0 - x1) < Math.abs(y0 - y1)) {
      drawWuLine(y0, y1, x0, x1, image, true);
    } else {
      drawWuLine(x0, x1, y0, y1, image, false);
    }
  }

  static private void drawWuLine(int x0, int x1, int y0, int y1, BufferedImage image, boolean steep) {
//    смотрим направление прямой, будем идти слева направо
    if (x0 > x1) {
      drawWuLine(x1, x0, y1, y0, image, steep);
      return;
    }
//    находим изменение по обеим осям
    int dx = x1 - x0;
    int dy = y1 - y0;
//    будем накапливать ошибку изменения по оси y
    double derror = 1.0 * dy / dx;
    double error = 0;
//    засетим текущий y и знак его изменения
    int y = y0;
    int sy = y1 > y0 ? 1 : -1;
//    рассмотрим каждый пиксель оси x
    for (int x = x0; x < x1; x++) {
//      введем 2 цвета в зависимости от текущей ошибки
      int c1 = (int) (255 * Math.min(1, 1.5 * (1 - error)));
      int c2 = (int) (255 * Math.min(1, 1.5 * (error)));
//      рассмотрим 2 соседние точки и посетим в зависимости от наклона прямой
//      также будем красить, если только текущий цвет этой клетки бледнее выбранного нами
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
//      накапливаем ошибку
      error += Math.abs(derror);
//      если ошибка перевалила за границу, то увеличиваем y и уменьшаем ошибку
      if (error > 1) {
        y += sy;
        error--;
      }
    }
  }
}
