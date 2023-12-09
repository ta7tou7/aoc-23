package aoc;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Pattern;

public class Day1 {
  private static List<String> numbers = List.of(
      "one", "two", "three",
      "four", "five", "six",
      "seven", "eight", "nine");
  public static void main(String[] args) {
    var url = Day1.class.getResource("/day11");
    try {
      int res = Files.readAllLines(Path.of(url.toURI())).stream()
          .mapToInt(Day1::parse1)
          .sum();
      System.out.println(res);
    } catch (IOException | URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

  private static int parse(String str) {
    int i = 0;
    int msd = 0, lsd = 0;
    while (i < str.length()) {
      if(Character.isDigit(str.charAt(i))) {
        lsd = Character.digit(str.charAt(i), 10);
      }
      i++;
    }
    i = str.length()-1;
    while (i >= 0) {
      if(Character.isDigit(str.charAt(i))) {
        msd = Character.digit(str.charAt(i), 10);
      }
      i--;
    }
    return msd*10 + lsd;
  }

  private static int parse1(String str) {
    var patternFirst = Pattern.compile(".*?(" + String.join("|", numbers) + "|\\d)");
    var patternLast = Pattern.compile(".*(" + String.join("|", numbers) + "|\\d)");
    var mF = patternFirst.matcher(str);
    var mL = patternLast.matcher(str);
    String capturedFirst = null;
    if (mF.find()) {
      capturedFirst = mF.group(1);
    }
    String capturedLast = null;
    if (mL.find()) {
      capturedLast = mL.group(1);
    }
    int first = numbers.indexOf(capturedFirst);
    int last = numbers.indexOf(capturedLast);
    if (first != -1) {
      first = first + 1;
    } else {
      first = Integer.parseInt(capturedFirst);
    }
    if (last != -1) {
      last = last + 1;
    }
    else {
      last = Integer.parseInt(capturedLast);
    }
    return first*10 + last;
  }
}
