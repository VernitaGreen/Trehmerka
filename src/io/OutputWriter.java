package io;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;

public class OutputWriter extends PrintWriter {

  public static final String DEFAULT_SEPARATOR = " ";

  public static OutputWriter toFile(String fileName) {
    try {
      return new OutputWriter(fileName);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void close() {
    super.close();
  }

  public OutputWriter(String fileName) throws FileNotFoundException {
    super(fileName);
  }

  public OutputWriter(OutputStream outputStream) {
    super(outputStream, true);
  }

  public OutputWriter(Writer writer) {
    super(writer, true);
  }

  public void prints(Object... objects) {
    for (int i = 0; i < objects.length; i++) {
      if (i > 0) {
        print(DEFAULT_SEPARATOR);
      }
      print(objects[i]);
    }
  }

  public void printlns(Object... objects) {
    prints(objects);
    println();
  }
}
