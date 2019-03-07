package io;

import java.io.IOException;
import java.io.OutputStream;

public class SplittingOutputWriter extends OutputWriter {
  private final OutputWriter[] outputWriters;

  public SplittingOutputWriter(OutputWriter... outputWriters) {
    super(new OutputStream() {
      @Override
      public void write(int b) throws IOException {
        for (OutputWriter outputWriter : outputWriters) {
          outputWriter.write(b);
        }
      }
    });

    this.outputWriters = outputWriters;
  }

  @Override
  public void flush() {
    for (OutputWriter outputWriter : outputWriters) {
      outputWriter.flush();
    }
  }

  @Override
  public void close() {
    for (OutputWriter outputWriter : outputWriters) {
      outputWriter.close();
    }
  }
}
