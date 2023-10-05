package jbdc.lab2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO<P extends Product, L extends Long> implements Repository<Product, Long> {

    private Connection connection;

    private final String SQL_SELECT = "SELECT * FROM PRODUCT";
    private final String SQL_SELECT_BY_ID = "SELECT * FROM PRODUCT WHERE ID = ?";
    private final String SQL_INSERT = "INSERT INTO PRODUCT(NAME, PRICE) VALUES (?, ?)";
    private final String SQL_UPDATE = "UPDATE PRODUCT SET NAME = ?, PRICE = ? WHERE ID = ?";
    private final String SQL_DELETE = "DELETE FROM PRODUCT WHERE ID = ?";
    private final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS PRODUCT";
    private final String SQL_CREATE_TABLE = "CREATE TABLE product (id SERIAL primary key, name varchar(100) NOT NULL, price double precision NOT NULL)";

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public ProductDAO(Connection connection) {
        this.connection = connection;
    }

    public ProductDAO() {}

    @Override
    public Long add(Product item) {
        List<Product> products = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT);
            statement.setString(1, item.getName());
            statement.setDouble(2, item.getPrice());
            statement.executeQuery();
            return item.getId();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Product> readAll() {
        List<Product> products = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                Double price = rs.getDouble("price");
                products.add(new Product(id, name, price));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return products;
    }

    @Override
    public Product read(Long id) {
        List<Product> products = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID);
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            rs.next();
            String name = rs.getString("name");
            Double price = rs.getDouble("price");
            return new Product(id, name, price);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean update(Product item) {
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);
            statement.setString(1, item.getName());
            statement.setDouble(2, item.getPrice());
            statement.setLong(3, item.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Long id) {
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean createProductTable() {
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_DROP_TABLE);
            statement.executeUpdate();
            statement = connection.prepareStatement(SQL_CREATE_TABLE);
            statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
