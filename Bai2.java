public class Bai2 {
  public static double CircleArea(double radius, int totalPoints) {
    int totalPointsInside = 0;

    for (int i = 0; i < totalPoints; i++) {
        double x = Math.random() * (2 * radius) - radius;
        double y = Math.random() * (2 * radius) - radius;

        if ((x * x + y * y) <= (radius * radius)) {
            totalPointsInside++;
        }
    }

    // Tính xác suất bên trong hình tròn
    double ratio = (double) totalPointsInside / totalPoints;
    double area = ratio * 4 * radius * radius;

    return area;
  }

  public static double calculatePiNumber(double area, double radius) {
    double pi = area / Math.pow(radius,2);

    return pi;

  }

  public static void main(String[] args) {
    int totalPoints = 1000000;
    double radius = 1.0;

    double area = CircleArea(radius, totalPoints);
    double pi = calculatePiNumber(area, radius);

    System.out.println("Pi:" + pi);
    
  }
}
