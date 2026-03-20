import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Bai4 {

    public static void main(String[] args) {
       Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int k = sc.nextInt();

        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = sc.nextInt();
        }

        

        System.out.println("Output:");
        System.out.println(findLongestSubsequence(n, k, a));


        sc.close();
    }

    public static String findLongestSubsequence(int n, int k, int[] A) {
        // Tạo bảng DP kích thước (n+1) x (k+1)
        int[][] dp = new int[n + 1][k + 1];

        // Khởi tạo bảng DP với giá trị -1
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= k; j++) {
                dp[i][j] = -1;
            }
        }

        // Base case: Tổng = 0 thì độ dài dãy con là 0
        for (int i = 0; i <= n; i++) {
            dp[i][0] = 0;
        }

        // Điền bảng DP
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= k; j++) {
                // Mặc định là không chọn phần tử A[i-1]
                dp[i][j] = dp[i - 1][j];

                // Nếu có thể chọn phần tử A[i-1] và mang lại dãy dài hơn
                if (j >= A[i - 1] && dp[i - 1][j - A[i - 1]] != -1) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - A[i - 1]] + 1);
                }
            }
        }

        // Nếu không có cách nào tạo ra tổng k
        if (dp[n][k] == -1) {
            return "Không có dãy con nào thỏa mãn";
        }

        // Truy vết để lấy các phần tử
        List<Integer> result = new ArrayList<>();
          int i = n, j = k;

          while (i > 0 && j > 0) {

              // nếu lấy A[i-1]
              if (j >= A[i - 1] &&
                  dp[i][j] == dp[i - 1][j - A[i - 1]] + 1) {

                  result.add(A[i - 1]);
                  j -= A[i - 1];
              }

              i--;
          }



        // Nối mảng thành chuỗi output
        StringBuilder sb = new StringBuilder();
        for (int idx = 0; idx < result.size(); idx++) {
            sb.append(result.get(idx));
            if (idx < result.size() - 1) {
                sb.append(", ");
            }
        }

        return sb.toString();
    }
}