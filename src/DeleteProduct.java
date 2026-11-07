
import java.sql.*;
import java.util.Scanner;

public class DeleteProduct {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/retail_db";
        String username = "root";
        String password = "Ratna@2006";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    url, username, password);

            Scanner sc = new Scanner(System.in);

            // Display total inventory value
            String sumSql = "SELECT SUM(quantity * price) AS total_value FROM Product";

            PreparedStatement sumPs = con.prepareStatement(sumSql);

            ResultSet sumRs = sumPs.executeQuery();

            if (sumRs.next()) {
                double total = sumRs.getDouble("total_value");

                System.out.println("Total Inventory Value: " + total);
            }

            sumRs.close();
            sumPs.close();

            // Accept product ID
            System.out.print("\nEnter Product ID to delete: ");
            int id = sc.nextInt();

            // Check whether product exists and quantity is zero
            String searchSql =
                    "SELECT quantity FROM Product WHERE product_id = ?";

            PreparedStatement searchPs =
                    con.prepareStatement(searchSql);

            searchPs.setInt(1, id);

            ResultSet rs = searchPs.executeQuery();

            if (rs.next()) {

                int quantity = rs.getInt("quantity");

                if (quantity == 0) {

                    String deleteSql =
                            "DELETE FROM Product WHERE product_id = ? AND quantity = 0";

                    PreparedStatement deletePs =
                            con.prepareStatement(deleteSql);

                    deletePs.setInt(1, id);

                    int rows = deletePs.executeUpdate();

                    if (rows > 0) {
                        System.out.println("Product deleted successfully!");
                    } else {
                        System.out.println("Product could not be deleted!");
                    }

                    deletePs.close();

                } else {
                    System.out.println(
                            "Product cannot be deleted because quantity is not zero!");
                }

            } else {
                System.out.println("Product not found!");
            }

            rs.close();
            searchPs.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

