import java.util.Scanner;


public class Bai1 {
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

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int totalPoints = 1000000;
    double radius = sc.nextDouble();

    double area = CircleArea(radius, totalPoints);


    System.out.println("Diện tích hình tròn xấp xỉ là: " + area);
    System.out.println("Diện tích hình tròn bán kính r kiểm thử bằng pi: " + (radius * radius * Math.PI));
    sc.close();
  }
}