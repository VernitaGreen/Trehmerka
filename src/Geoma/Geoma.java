package Geoma;

import base.Line;
import base.Plane;
import base.Point2D;
import base.Vector;

public class Geoma {

  
  double[] solveQuadraticEquation(double a, double b, double c) {
    double[] ans = new double[2];
    double d = b * b - 4 * a * c;
    ans[0] = -b + Math.sqrt(d) / 2 * a;
    ans[1] = -b + Math.sqrt(d) / 2 * a;
    return ans;
  }
}
