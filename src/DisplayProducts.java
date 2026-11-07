
import java.sql.*;
import java.util.Scanner;

public class DisplayProducts {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/retail_db";
        String username = "root";
        String password = "Ratna@2006";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    url, username, password);

            Scanner sc = new Scanner(System.in);

            System.out.print("Enter category: ");
            String category = sc.nextLine();

            String sql = "SELECT * FROM Product " +
                    "WHERE category = ? AND quantity < 20 " +
                    "ORDER BY price DESC";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, category);

            ResultSet rs = ps.executeQuery();

            System.out.println("\nProducts in category: " + category);
            System.out.println("----------------------------------------");

            boolean found = false;

            while (rs.next()) {
                found = true;

                System.out.println("Product ID: " + rs.getInt("product_id"));
                System.out.println("Product Name: " + rs.getString("product_name"));
                System.out.println("Category: " + rs.getString("category"));
                System.out.println("Quantity: " + rs.getInt("quantity"));
                System.out.println("Price: " + rs.getDouble("price"));

                System.out.println("----------------------------------------");
            }

            if (!found) {
                System.out.println("No matching products found!");
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

