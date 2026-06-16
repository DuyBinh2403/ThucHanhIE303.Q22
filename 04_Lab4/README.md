# BÁO CÁO BÀI THỰC HÀNH - LAB 4: KẾT NỐI VÀ TRUY VẤN CƠ SỞ DỮ LIỆU

* **Môn học**: Công nghệ Java
* **Bài thực hành**: Lab 4 (Xây dựng CSDL để lưu trữ và truy vấn thông tin sản phẩm
---

## 1. Yêu cầu của bài thực hành (Lab 4)
* Thiết lập Cơ sở dữ liệu (CSDL) để lưu trữ thông tin của các sản phẩm giày.
* Thay thế dữ liệu tĩnh (hardcoded) trong mã nguồn ứng dụng Java Swing bằng việc truy vấn dữ liệu động từ CSDL.

---

## 2. Giải pháp thực hiện của em
Để thuận tiện nhất cho việc chấm bài mà không yêu cầu phải cài đặt các hệ quản trị CSDL phức tạp (như MySQL, SQL Server) hay cấu hình cổng kết nối, em đã lựa chọn sử dụng **SQLite**.

* **SQLite** là một hệ quản trị CSDL nhúng (Embedded Database). Toàn bộ dữ liệu của ứng dụng sẽ được lưu trữ tự động trong một tệp duy nhất là `products.db` nằm ngay tại thư mục gốc của dự án.
* Khi khởi chạy ứng dụng lần đầu tiên, hệ thống sẽ tự động tạo tệp CSDL, khởi tạo bảng và thực hiện nạp dữ liệu mẫu (Seeding) trực tiếp mà không cần bất kỳ bước cấu hình thủ công nào.

---

## 3. Các thành phần chính trong bài làm

### a. Thư viện kết nối CSDL (JDBC Driver)
* Em đã tải và tích hợp thư viện **[sqlite-jdbc-3.36.0.3.jar] đặt trong thư mục `lib/`.
* Đồng thời cấu hình tệp tin dự án **[03_Lab3.iml]** để IntelliJ IDEA tự động nhận diện Driver này.

### b. Xây dựng lớp hỗ trợ kết nối và truy vấn CSDL
Em đã viết thêm lớp **[DatabaseHelper.java]** đảm nhận các nhiệm vụ sau:
* **Kết nối**: Thiết lập chuỗi kết nối JDBC tới SQLite (`jdbc:sqlite:products.db`).
* **Khởi tạo cấu trúc bảng**: Tạo bảng `products` nếu chưa tồn tại với cấu trúc:
  ```sql
  CREATE TABLE IF NOT EXISTS products (
      id INTEGER PRIMARY KEY AUTOINCREMENT,
      title TEXT NOT NULL,
      price TEXT NOT NULL,
      brand TEXT,
      desc TEXT,
      image_path TEXT
  );
  ```
* **Nạp dữ liệu mẫu (Database Seeding)**: Kiểm tra nếu bảng trống, tiến hành chèn tự động 8 sản phẩm giày (tương ứng với các tệp ảnh trong thư mục `images/`).
* **Truy vấn**: Cung cấp hàm `List<Product> getAllProducts()` thực thi lệnh `SELECT * FROM products` để trả về danh sách sản phẩm cho giao diện.

### c. Cập nhật giao diện hiển thị
Em đã chỉnh sửa lớp giao diện chính **[ShopGUI.java]**:
* Thay thế khối khởi tạo danh sách sản phẩm tĩnh trong hàm `initData()` bằng lời gọi hàm lấy dữ liệu động từ CSDL:
  ```java
  private void initData() {
      productList = DatabaseHelper.getAllProducts();
  }
  ```

---

## 4. Hướng dẫn chạy chương trình

### Cách 1: Chạy trực tiếp trên IntelliJ IDEA
1. Mở thư mục dự án `04_Lab4` bằng IntelliJ IDEA.
2. Kiểm tra cấu hình thư viện đã được nhận diện chưa: Vào **File** -> **Project Structure** -> **Modules** -> kiểm tra thẻ **Dependencies** xem đã có `sqlite-jdbc-3.36.0.3.jar` hay chưa. (Nếu chưa, nhấn nút `+` -> chọn `JARs or Directories` rồi dẫn tới tệp JAR trong thư mục `lib/`).
3. Nhấn chuột phải vào lớp `ShopGUI` và chọn **Run 'ShopGUI.main()'**.

### Cách 2: Chạy bằng dòng lệnh (Terminal)
Nếu muốn kiểm tra nhanh bằng dòng lệnh, có thể mở Terminal tại thư mục dự án và chạy các lệnh sau:

* **Bước 1: Biên dịch chương trình**
  ```bash
  javac -cp .:lib/sqlite-jdbc-3.36.0.3.jar -d out/production/03_Lab3 src/*.java
  ```
* **Bước 2: Chạy ứng dụng**
  ```bash
  java -cp .:lib/sqlite-jdbc-3.36.0.3.jar:out/production/03_Lab3 src.ShopGUI
  ```

*(Lưu ý: Đối với Windows, vui lòng thay đổi dấu hai chấm `:` phân cách đường dẫn classpath thành dấu chấm phẩy `;`)*.
