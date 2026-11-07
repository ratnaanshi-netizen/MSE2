
import java.sql.*;
import java.util.Scanner;

public class UpdateStock {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/retail_db";
        String username = "root";
        String password = "Ratna@2006";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    url, username, password);

            Scanner sc = new Scanner(System.in);

            System.out.print("Enter Product ID: ");
            int id = sc.nextInt();

            System.out.print("Enter quantity to be added: ");
            int quantity = sc.nextInt();

            String sql = "UPDATE Product SET quantity = quantity + ? WHERE product_id = ?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, quantity);
            ps.setInt(2, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Product stock updated successfully!");
            } else {
                System.out.println("Product not found!");
            }

            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

