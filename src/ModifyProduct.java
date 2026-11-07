
import java.sql.*;
import java.util.Scanner;

public class ModifyProduct {

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

            System.out.print("Enter new price: ");
            double newPrice = sc.nextDouble();

            if (newPrice <= 0) {
                System.out.println("Invalid price! Price must be greater than zero.");
            } else {

                // Check whether product exists
                String searchSql = "SELECT * FROM Product WHERE product_id = ?";

                PreparedStatement searchPs = con.prepareStatement(searchSql);
                searchPs.setInt(1, id);

                ResultSet rs = searchPs.executeQuery();

                if (rs.next()) {

                    // Update product price
                    String updateSql = "UPDATE Product SET price = ? WHERE product_id = ?";

                    PreparedStatement updatePs = con.prepareStatement(updateSql);

                    updatePs.setDouble(1, newPrice);
                    updatePs.setInt(2, id);

                    updatePs.executeUpdate();

                    System.out.println("\nProduct updated successfully!");

                    // Display updated product details
                    String displaySql = "SELECT * FROM Product WHERE product_id = ?";

                    PreparedStatement displayPs = con.prepareStatement(displaySql);
                    displayPs.setInt(1, id);

                    ResultSet updatedRs = displayPs.executeQuery();

                    if (updatedRs.next()) {
                        System.out.println("\nUpdated Product Details:");
                        System.out.println("Product ID: " + updatedRs.getInt("product_id"));
                        System.out.println("Product Name: " + updatedRs.getString("product_name"));
                        System.out.println("Category: " + updatedRs.getString("category"));
                        System.out.println("Quantity: " + updatedRs.getInt("quantity"));
                        System.out.println("Price: " + updatedRs.getDouble("price"));
                    }

                    updatedRs.close();
                    displayPs.close();
                    updatePs.close();

                } else {
                    System.out.println("Product not found!");
                }

                rs.close();
                searchPs.close();
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

