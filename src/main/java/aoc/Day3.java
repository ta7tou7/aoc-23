package aoc;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Day3 {
  public static void main(String[] args) {
    var url = Day3.class.getResource("/day31");
    try {
      var engine = Files.readAllLines(Path.of(url.toURI())).stream()
          .map(String::stripTrailing)
          .toList();
      System.out.println(part2(engine));
    } catch (IOException | URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

  static int part1(List<String> engine) {
    List<String> res = new ArrayList<>();
    for (int i = 0; i < engine.size(); i++) {
      var line = engine.get(i);
      for (int j = 0; j < engine.get(i).length(); j++) {
        if (Character.isDigit(line.charAt(j))) {
          int baseline = j;
          while ( j+1 < line.length() && Character.isDigit(line.charAt(j+1))) {
            j++;
          }
          if (hasSymbolNeighbors(engine, i, baseline, j+1)) {
            res.add(line.substring(baseline, j+1));
          }
        }
      }
    }
    System.out.println(res);
    return res.stream().mapToInt(Integer::parseInt).sum();
  }

  static int part2(List<String> engine) {
    int s = 0;
    for (int i = 0; i < engine.size(); i++) {
      var line = engine.get(i);
      for (int j = 0; j < engine.get(i).length(); j++) {
        if (line.charAt(j) == '*') {
          s+=computeGearRatio(engine, i, j);
        }
      }
    }
    return s;
  }

  private static int computeGearRatio(List<String> engine, int i, int j) {
    var line = engine.get(i);
    List<Integer> partNumbers = new ArrayList<>();
    // right
    if (j+1 < line.length() && Character.isDigit(line.charAt(j+1))) {
      var end = j+1;
      while(end < line.length() && Character.isDigit(line.charAt(end))) {
        end++;
      }
      partNumbers.add(Integer.parseInt(line.substring(j+1, end)));
    }
    // left
    if (j-1 >= 0 && Character.isDigit(line.charAt(j-1))) {
      var start = j-1;
      while(start >= 0 && Character.isDigit(line.charAt(start))) {
        start--;
      }
      partNumbers.add(Integer.parseInt(line.substring(start+1, j)));
    }

    //down
    if (i+1 < engine.size() && Character.isDigit(engine.get(i+1).charAt(j))) {
      var start = j;
      while(start >= 0 && Character.isDigit(engine.get(i+1).charAt(start))) {
        start--;
      }
      var end = j;
      while(end < engine.get(i+1).length() && Character.isDigit(engine.get(i+1).charAt(end))) {
        end++;
      }
      partNumbers.add(Integer.parseInt(engine.get(i+1).substring(start+1, end)));
    }
    else {
      // down right
      if (i+1 < engine.size() && j+1 < engine.get(i+1).length() && Character.isDigit(engine.get(i+1).charAt(j+1))) {
        var end = j+1;
        while(end < engine.get(i+1).length() && Character.isDigit(engine.get(i+1).charAt(end))) {
          end++;
        }
        partNumbers.add(Integer.parseInt(engine.get(i+1).substring(j+1, end)));
      }
      // down left
      if (i+1 < engine.size() && j-1 >= 0 && Character.isDigit(engine.get(i+1).charAt(j-1))) {
        var start = j-1;
        while(start >= 0 && Character.isDigit(engine.get(i+1).charAt(start))) {
          start--;
        }
        partNumbers.add(Integer.parseInt(engine.get(i+1).substring(start+1, j)));
      }
    }
    // up
    if (i-1 >= 0 && Character.isDigit(engine.get(i-1).charAt(j))) {
      var start = j;
      while(start >= 0 && Character.isDigit(engine.get(i-1).charAt(start))) {
        start--;
      }
      var end = j;
      while(end < engine.get(i-1).length() && Character.isDigit(engine.get(i-1).charAt(end))) {
        end++;
      }
      partNumbers.add(Integer.parseInt(engine.get(i-1).substring(start+1, end)));
    }
    else {
      // up right
      if (i-1 >= 0 && j+1 < engine.get(i-1).length() && Character.isDigit(engine.get(i-1).charAt(j+1))) {
        var end = j+1;
        while(end < engine.get(i+1).length() && Character.isDigit(engine.get(i-1).charAt(end))) {
          end++;
        }
        partNumbers.add(Integer.parseInt(engine.get(i-1).substring(j+1, end)));
      }
      // up left
      if (i-1 >= 0 && j-1 >= 0 && Character.isDigit(engine.get(i-1).charAt(j-1))) {
        var start = j-1;
        while(start >= 0 && Character.isDigit(engine.get(i-1).charAt(start))) {
          start--;
        }
        partNumbers.add(Integer.parseInt(engine.get(i-1).substring(start+1, j)));
      }
    }
    return partNumbers.size() == 2 ? partNumbers.stream().reduce(1, (a, b) -> a*b) : 0;
  }

  private static boolean hasSymbolNeighbors(List<String> engine, int i, int baseline, int end) {
    var line = engine.get(i);
    int start = Math.max(0, baseline-1);
    int finish = Math.min(line.length(), end+1);
    return (end < line.length() && line.charAt(end) != '.') ||
        (baseline >= 1 && line.charAt(baseline-1) != '.') ||
        (i < line.length()-1 && !engine.get(i+1).substring(start, finish).equals(".".repeat(finish-start))) ||
        (i >= 1 && !engine.get(i-1).substring(start, finish).equals(".".repeat(finish-start)));
  }
}
