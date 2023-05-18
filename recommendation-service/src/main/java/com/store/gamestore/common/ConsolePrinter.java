package com.store.gamestore.common;

import java.util.Collections;

public class ConsolePrinter {

  public static void printProgress(long total, long current) {
    StringBuilder stringBuilder = new StringBuilder(140);
    int percent = (int) (current * 100 / total);
    stringBuilder
        .append('\r')
        .append(String.join("",
            Collections.nCopies(percent == 0 ? 2 : 2 - (int) (Math.log10(percent)), " ")))
        .append(String.format(" %d%% [", percent))
        .append(String.join("", Collections.nCopies(percent, "=")))
        .append('>')
        .append(String.join("", Collections.nCopies(100 - percent, " ")))
        .append(']')
        .append(String.join("",
            Collections.nCopies((int) (Math.log10(total)) - (int) (Math.log10(current)), " ")))
        .append(String.format(" %d/%d", current, total));
    System.out.print(stringBuilder);
  }
}
