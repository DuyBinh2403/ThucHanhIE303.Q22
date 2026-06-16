package src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {
    private static final String DB_URL = "jdbc:sqlite:products.db";

    static {
        try {
            Class.forName("org.sqlite.JDBC");
            initDatabase();
        } catch (ClassNotFoundException e) {
            System.err.println("Failed to load SQLite JDBC driver.");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    private static void initDatabase() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS products ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "title TEXT NOT NULL,"
                + "price TEXT NOT NULL,"
                + "brand TEXT,"
                + "desc TEXT,"
                + "image_path TEXT"
                + ");";

        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement()) {

            // Create table
            stmt.execute(createTableSQL);

            // Check if table is empty
            String checkSQL = "SELECT COUNT(*) AS count FROM products;";
            try (ResultSet rs = stmt.executeQuery(checkSQL)) {
                if (rs.next() && rs.getInt("count") == 0) {
                    seedData(conn);
                }
            }
        } catch (SQLException e) {
            System.err.println("Database initialization failed.");
            e.printStackTrace();
        }
    }

    private static void seedData(Connection conn) throws SQLException {
        String insertSQL = "INSERT INTO products (title, price, brand, desc, image_path) VALUES (?, ?, ?, ?, ?);";
        try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            // Add default products
            addProduct(pstmt, "4DFWD PULSE SHOES", "$160.00", "Adidas",
                    "This product is excluded from all promotional discounts and offers.", "images/img1.png");
            addProduct(pstmt, "FORUM MID SHOES", "$100.00", "Adidas",
                    "This product is excluded from all promotional discounts and offers.", "images/img2.png");
            addProduct(pstmt, "SUPERNOVA SHOES", "$150.00", "Adidas", "NMD City Stock 2", "images/img3.png");
            addProduct(pstmt, "Adidas", "$160.00", "Adidas", "NMD City Stock 2", "images/img4.png");
            addProduct(pstmt, "Adidas", "$120.00", "Adidas", "NMD City Stock 2", "images/img5.png");
            addProduct(pstmt, "4DFWD PULSE SHOES", "$150.00", "Adidas",
                    "This product is excluded from all promotional discounts and offers.", "images/img6.png");
            addProduct(pstmt, "4DFWD PULSE SHOES", "$140.00", "Adidas",
                    "This product is excluded from all promotional discounts and offers.", "images/img1.png");
            addProduct(pstmt, "FORUM MID SHOES", "$100.00", "Adidas",
                    "This product is excluded from all promotional discounts and offers.", "images/img2.png");
        }
    }

    private static void addProduct(PreparedStatement pstmt, String title, String price, String brand, String desc,
            String imagePath) throws SQLException {
        pstmt.setString(1, title);
        pstmt.setString(2, price);
        pstmt.setString(3, brand);
        pstmt.setString(4, desc);
        pstmt.setString(5, imagePath);
        pstmt.executeUpdate();
    }

    public static List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String querySQL = "SELECT title, price, brand, desc, image_path FROM products;";

        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(querySQL)) {

            while (rs.next()) {
                String title = rs.getString("title");
                String price = rs.getString("price");
                String brand = rs.getString("brand");
                String desc = rs.getString("desc");
                String imagePath = rs.getString("image_path");

                products.add(new Product(title, price, brand, desc, imagePath));
            }
        } catch (SQLException e) {
            System.err.println("Failed to fetch products from database.");
            e.printStackTrace();
        }

        return products;
    }
}
