package base;

public class Matrix {
  public final int n;
  public final int m;

  private final double[][] a;

  public Matrix(double[][] a) {
    this.n = a.length;
    this.m = a[0].length;
    this.a = new double[n][m];

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        this.a[i][j] = a[i][j];
      }
    }
  }

  public double get(int i, int j) {
    if (i < 0 || i >= n || j < 0 || j >= m) {
      throw new IllegalArgumentException();
    }

    return a[i][j];
  }

  public Matrix copy() {
    Matrix res = zero(n, m);

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        res.a[i][j] = a[i][j];
      }
    }

    return res;
  }

  public Matrix copyExcept(int i, int j) {
    Matrix res = zero(n - 1, m - 1);

    int resI = 0;
    for (int ii = 0; ii < n; ii++) {
      if (ii == i) {
        continue;
      }
      int resJ = 0;
      for (int jj = 0; jj < m; jj++) {
        if (jj == j) {
          continue;
        }
        res.a[resI][resJ] = a[ii][jj];
        resJ++;
      }
      resI++;
    }

    return res;
  }

  public static Matrix zero(int n, int m) {
    if (n <= 0 || m <= 0) {
      throw new IllegalArgumentException();
    }

    return new Matrix(new double[n][m]);
  }

  public static Matrix zeroLike(Matrix a) {
    return zero(a.n, a.m);
  }

  public static Matrix eye(int n) {
    Matrix res = zero(n, n);
    for (int i = 0; i < n; i++) {
      res.a[i][i] = 1;
    }
    return res;
  }

  public static Matrix extendAsIdentity(Matrix a, int n) {
    if (a.n > n || a.n != a.m) {
      throw new IllegalArgumentException();
    }

    Matrix res = Matrix.zero(n, n);

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (i < a.n && j < a.m) {
          res.a[i][j] = a.a[i][j];
        } else {
          res.a[i][j] = i == j ? 1 : 0;
        }
      }
    }

    return res;
  }

  public static Matrix add(Matrix a, Matrix b) {
    if (a.n != b.n || a.m != b.m) {
      throw new IllegalArgumentException();
    }

    Matrix res = Matrix.zeroLike(a);

    for (int i = 0; i < a.n; i++) {
      for (int j = 0; j < a.m; j++) {
        res.a[i][j] = a.a[i][j] + b.a[i][j];
      }
    }

    return res;
  }

  public static Matrix multiply(Matrix a, Matrix b) {
    if (a.m != b.n) {
      throw new IllegalArgumentException();
    }

    Matrix res = Matrix.zero(a.n, b.m);

    for (int i = 0; i < a.n; i++) {
      for (int j = 0; j < b.m; j++) {
        for (int k = 0; k < a.m; k++) {
          res.a[i][j] += a.a[i][k] * b.a[k][j];
        }
      }
    }

    return res;
  }

  public static Vector multiply(Matrix a, Vector v) {
    if (a.n != 3) {
      throw new IllegalArgumentException();
    }

    Matrix res = multiply(a, Matrix.fromVector(v));

    return new Vector(
        res.a[0][0],
        res.a[1][0],
        res.a[2][0]
    );
  }

  public static double determinant(Matrix a) {
    if (a.n != a.m) {
      throw new IllegalArgumentException();
    }

    if (a.n == 1) {
      return a.get(0, 0);
    }

    double res = 0;
    int sign = 1;
    for (int i = 0; i < a.n; i++) {
      res += sign * a.get(0, i) * Matrix.determinant(a.copyExcept(0, i));

      sign *= -1;
    }

    return res;
  }

  public static Matrix transpose(Matrix a) {
    Matrix res = Matrix.zero(a.m, a.n);

    for (int i = 0; i < a.n; i++) {
      for (int j = 0; j < a.m; j++) {
        res.a[j][i] = a.a[i][j];
      }
    }

    return res;
  }

  public static Matrix concatenateI(Matrix a, Matrix b) {
    if (a.m != b.m) {
      throw new IllegalArgumentException();
    }

    Matrix res = Matrix.zero(a.n + b.n, a.m);

    for (int i = 0; i < res.n; i++) {
      for (int j = 0; j < res.m; j++) {
        res.a[i][j] = i < a.n ? a.a[i][j] : b.a[i - a.n][j];
      }
    }

    return res;
  }

  public static Matrix concatenateJ(Matrix a, Matrix b) {
    if (a.n != b.n) {
      throw new IllegalArgumentException();
    }

    Matrix res = Matrix.zero(a.n, a.m + b.m);

    for (int i = 0; i < res.n; i++) {
      for (int j = 0; j < res.m; j++) {
        res.a[i][j] = j < a.m ? a.a[i][j] : b.a[i][j - a.m];
      }
    }

    return res;
  }

  public static Matrix fromValues(double... a) {
    Matrix res = Matrix.zero(a.length, 1);
    for (int i = 0; i < a.length; i++) {
      res.a[i][0] = a[i];
    }

    return res;
  }

  public static Matrix rotationMatrix(Vector x1, Vector y1, Vector z1,
                                      Vector x2, Vector y2, Vector z2) {
    x1 = G.normalize(x1);
    y1 = G.normalize(y1);
    z1 = G.normalize(z1);

    x2 = G.normalize(x2);
    y2 = G.normalize(y2);
    z2 = G.normalize(z2);

    double[][] a = {
        {G.dotProduct(x2, x1), G.dotProduct(x2, y1), G.dotProduct(x2, z1)},
        {G.dotProduct(y2, x1), G.dotProduct(y2, y1), G.dotProduct(y2, z1)},
        {G.dotProduct(z2, x1), G.dotProduct(z2, y1), G.dotProduct(z2, z1)}
    };
    return new Matrix(a);
  }

  public static Matrix fromVector(Vector v) {
    return fromValues(v.x, v.y, v.z);
  }
}
