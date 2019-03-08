package io;

import java.io.IOException;
import java.io.OutputStream;

public class SplittingOutputStream extends OutputStream {

  private final OutputStream[] outputStreams;

  public SplittingOutputStream(OutputStream... outputStreams) {
    this.outputStreams = outputStreams;
  }

  @Override
  public void write(int b) throws IOException {
    for (OutputStream outputStream : outputStreams) {
      outputStream.write(b);
    }
  }
}
