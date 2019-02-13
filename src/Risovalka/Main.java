package Risovalka;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
  public static void main(String[] args) {
    BufferedImage image = new BufferedImage(600, 200, BufferedImage.TYPE_BYTE_GRAY);
    try {
      drawStar(100, 100, 80, 15, image, 0);
      drawStar(300, 100, 80, 15, image, 1);
      drawStar(500, 100, 80, 15, image, 2);
    } catch (Exception e) {
      e.printStackTrace();
    }

    File outputfile = new File("saved.png");
    try {
      ImageIO.write(image, "png", outputfile);
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println("privet");
  }

  /**
   * @param x0 центр звезды по оси x
   * @param y0 центр звезды по оси y
   * @param r радиус звезды
   * @param n количество лучей
   * @param method 0 (базовый), 1 (Брезензем), 2 (Ву)
   */
  static void drawStar(int x0, int y0, int r, int n, BufferedImage image, int method) {
//    по определению синуса и косинуса найдем концы лучей
    int[] x = new int[n];
    for (int i = 0; i < n; i++) {
      x[i] = x0 + (int)(Math.cos(2 * Math.PI / n * i) * r);
    }
    int[] y = new int[n];
    for (int i = 0; i < n; i++) {
      y[i] = y0 + (int)(Math.sin(2 * Math.PI / n * i) * r);
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
