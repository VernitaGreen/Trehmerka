package Risovalka;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ObjReader {
  static Pattern lineV = Pattern.compile("v (\\d+(\\.\\d+)?) (\\d+(\\.\\d+)?) (\\d+(\\.\\d+)?)");

  public static void main(String[] args) {
    Matcher matcher = lineV.matcher("v 1 2 3");
    System.out.println(matcher.find());
    System.out.println(matcher.group(3));
  }

}
