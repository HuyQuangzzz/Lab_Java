package jbdc.lab2;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Program {
    private ProductDAO<Product, Long> productDAO;

    private BasicDataSource dataSource;

    public Program() {
        productDAO = new ProductDAO<>();
        dataSource = new BasicDataSource();
    }

    private void setDataSource(String[] args) {
        dataSource.setUrl(args[0]);
        dataSource.setUsername(args[1]);
        dataSource.setPassword(args[2]);
        dataSource.setMinIdle(5);
        dataSource.setMinIdle(10);
        dataSource.setMaxOpenPreparedStatements(100);
    }

    private void showALlProducts() throws SQLException {
        Connection conn = dataSource.getConnection();
        productDAO.setConnection(conn);
        List<Product> products = productDAO.readAll();
        for (Product product : products) {
            product.print();
        }
    }

    private void showOneProducts() throws SQLException {
        Connection conn = dataSource.getConnection();
        productDAO.setConnection(conn);
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a product id: ");
        long id = sc.nextLong();
        Product product = productDAO.read(id);
        product.print();
    }

    private void addProduct() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a product name: ");
        String name = sc.nextLine();
        System.out.print("Enter a product price: ");
        double price = sc.nextDouble();
        Product product = new Product(0L, name, price);
        productDAO.add(product);
    }

    private void updateProduct() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a product id: ");
        Long id = sc.nextLong();
        System.out.print("Enter a product name: ");
        String name = sc.nextLine();
        System.out.print("Enter a product price: ");
        double price = sc.nextDouble();
        Product product = new Product(id, name, price);
        productDAO.update(product);
    }

    private void deleteProduct() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a product id: ");
        Long id = sc.nextLong();
        productDAO.delete(id);
    }

    private void createTableProduct() throws SQLException {
        Connection connection = dataSource.getConnection();
        productDAO.setConnection(connection);
        productDAO.createProductTable();
    }

    private void showMenu() {
        System.out.println("----------Menu---------");
        System.out.println("1. Read all products");
        System.out.println("2. Read detail of a product by id");
        System.out.println("3. Add a new product");
        System.out.println("4. Update a product");
        System.out.println("5. Delete a product by id");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }

    public static void main(String[] args) throws SQLException {
        Program program = new Program();
        program.setDataSource(args);
        program.createTableProduct();

        int choice = 0;
        while (choice != 6) {
            program.showMenu();
            Scanner sc = new Scanner(System.in);
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    program.showALlProducts();
                    break;
                case 2:
                    program.showOneProducts();
                    break;
                case 3:
                    program.addProduct();
                    break;
                case 4:
                    program.updateProduct();
                    break;
                case 5:
                    program.deleteProduct();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }
    }

}
