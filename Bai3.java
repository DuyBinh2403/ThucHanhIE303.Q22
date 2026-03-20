// Giải theo phương pháp Graham Scan
import java.util.*;

class Point {
  int x, y;
  Point(int x, int y) {
    this.x = x;
    this.y = y;
  }
}


public class Bai3 {
  // Ham tinh Cross giua 3 diem
  public static long cross(Point O, Point A, Point B) {
    return (long)(A.x - O.x)*(B.y - O.y) - (long)(A.y - O.y)*(B.x - O.x);
  }

  public static int distance(Point A, Point B) {
    return (A.x - B.x)*(A.x - B.x) + (A.y - B.y)*(A.y - B.y);
  }

  public static Point findPivot(List<Point> points) {
    Point pivot = points.get(0);
    // Tim diem thap nhat
    for (Point p : points) {
      if (p.y < pivot.y || (p.y == pivot.y && p.x < pivot.x)) {
        pivot = p;
      }
    }

    return pivot;
  }
  public static List<Point> grahamScan(List<Point> points) {
    Point pivot = findPivot(points);

     points.sort((a, b) -> {
            long c = cross(pivot, a, b);
            if (c == 0) {
                return distance(pivot, a) - distance(pivot, b);
            }
            return c > 0 ? -1 : 1;
        });

    Stack<Point> stack = new Stack<>();
    stack.push(points.get(0));
    stack.push(points.get(1));

    for (int i = 2; i < points.size(); i++) {
        Point top = stack.pop();

        while (!stack.isEmpty() &&
                cross(stack.peek(), top, points.get(i)) <= 0) {
            top = stack.pop();
        }

        stack.push(top);
        stack.push(points.get(i));
    }

    return new ArrayList<>(stack);
  }
  public static void main(String[] arg) {
    Scanner sc = new Scanner(System.in);

    int n = sc.nextInt();
    List<Point> points = new ArrayList<>();

    for (int i = 0; i < n; i++) {
        points.add(new Point(sc.nextInt(), sc.nextInt()));
    }

    List<Point> result = grahamScan(points);

    for (Point p : result) {
      System.out.println(p.x + " " + p.y);
    }

    sc.close();
  }
}
