package com.locked_in.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.locked_in.config.DbConfig;
import com.locked_in.model.ProductModel;

/**
 * Service class for handling product-related operations.
 * Provides methods to retrieve, create, update, and delete products.
 */
public class ProductService {
    
    private Connection dbConn;
    private boolean isConnectionError = false;
    
    /**
     * Constructor initializes the database connection.
     * Sets the connection error flag if the connection fails.
     */
    public ProductService() {
        try {
            dbConn = DbConfig.getDbConnection();
            System.out.println("Database connection successful in ProductService");
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            isConnectionError = true;
            System.out.println("Database connection failed in ProductService: " + ex.getMessage());
        }
    }
    
    /**
     * Retrieves all products of a specific category
     * 
     * @param category the category of products to retrieve
     * @return List of ProductModel objects, or empty list if none found
     */
    public List<ProductModel> getProductsByCategory(String category) {
        if (isConnectionError) {
            System.out.println("ProductService - Connection Error!");
            return new ArrayList<>();
        }
        
        List<ProductModel> products = new ArrayList<>();
        String query = "SELECT * FROM product WHERE category = ?";
        
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setString(1, category);
            ResultSet result = stmt.executeQuery();
            
            while (result.next()) {
                ProductModel product = mapResultSetToProduct(result);
                products.add(product);
            }
        } catch (SQLException e) {
            System.out.println("ProductService - SQL Error retrieving products: " + e.getMessage());
            e.printStackTrace();
        }
        
        return products;
    }
    
    /**
     * Retrieves products by category with sorting
     * 
     * @param category the category of products to retrieve
     * @param sortBy the sorting criteria (price-low-high, price-high-low, relevancy, newest)
     * @return List of sorted products
     */
    public List<ProductModel> getProductsByCategory(String category, String sortBy) {
        if (isConnectionError) {
            System.out.println("ProductService - Connection Error!");
            return new ArrayList<>();
        }
        
        List<ProductModel> products = new ArrayList<>();
        String query = "SELECT * FROM product WHERE category = ?";
        
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setString(1, category);
            ResultSet result = stmt.executeQuery();
            
            while (result.next()) {
                ProductModel product = mapResultSetToProduct(result);
                products.add(product);
            }
            
            // Sort the products based on the sortBy parameter
            switch (sortBy) {
                case "price-low-high":
                    products.sort((p1, p2) -> p1.getPrice().compareTo(p2.getPrice()));
                    break;
                case "price-high-low":
                    products.sort((p1, p2) -> p2.getPrice().compareTo(p1.getPrice()));
                    break;
                case "newest":
                    products.sort((p1, p2) -> p2.getCreatedAt().compareTo(p1.getCreatedAt()));
                    break;
                case "relevancy":
                default:
                    // Default sorting by relevancy (could be based on popularity, ratings, etc.)
                    // For now, we'll keep the original order
                    break;
            }
            
        } catch (SQLException e) {
            System.out.println("ProductService - SQL Error retrieving products: " + e.getMessage());
            e.printStackTrace();
        }
        
        return products;
    }
    
    /**
     * Retrieves a single product by its ID
     * 
     * @param productId the ID of the product to retrieve
     * @return ProductModel object, or null if not found
     */
    public ProductModel getProductById(Integer productId) {
        if (isConnectionError) {
            System.out.println("ProductService - Connection Error!");
            return null;
        }
        
        String query = "SELECT * FROM product WHERE product_id = ?";
        
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setInt(1, productId);
            ResultSet result = stmt.executeQuery();
            
            if (result.next()) {
                return mapResultSetToProduct(result);
            }
        } catch (SQLException e) {
            System.out.println("ProductService - SQL Error retrieving product: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * Creates a new product in the database
     * 
     * @param product the product to create
     * @return true if successful, false otherwise
     */
    public boolean createProduct(ProductModel product) {
        if (isConnectionError) {
            System.out.println("ProductService - Connection Error!");
            return false;
        }
        
        String query = "INSERT INTO product (name, description, brand, category, tags, price, " +
                      "stock_quantity, weight, image, dimensions) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setString(3, product.getBrand());
            stmt.setString(4, product.getCategory());
            stmt.setString(5, product.getTags());
            stmt.setBigDecimal(6, product.getPrice());
            stmt.setInt(7, product.getStockQuantity());
            stmt.setBigDecimal(8, product.getWeight());
            stmt.setString(9, product.getImage());
            stmt.setString(10, product.getDimensions());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("ProductService - SQL Error creating product: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Updates an existing product in the database
     * 
     * @param product the product to update
     * @return true if successful, false otherwise
     */
    public boolean updateProduct(ProductModel product) {
        if (isConnectionError) {
            System.out.println("ProductService - Connection Error!");
            return false;
        }
        
        String query = "UPDATE product SET name = ?, description = ?, brand = ?, category = ?, " +
                      "tags = ?, price = ?, stock_quantity = ?, weight = ?, image = ?, dimensions = ? " +
                      "WHERE product_id = ?";
        
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setString(3, product.getBrand());
            stmt.setString(4, product.getCategory());
            stmt.setString(5, product.getTags());
            stmt.setBigDecimal(6, product.getPrice());
            stmt.setInt(7, product.getStockQuantity());
            stmt.setBigDecimal(8, product.getWeight());
            stmt.setString(9, product.getImage());
            stmt.setString(10, product.getDimensions());
            stmt.setInt(11, product.getProductId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("ProductService - SQL Error updating product: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Deletes a product from the database
     * 
     * @param productId the ID of the product to delete
     * @return true if successful, false otherwise
     */
    public boolean deleteProduct(Integer productId) {
        if (isConnectionError) {
            System.out.println("ProductService - Connection Error!");
            return false;
        }
        
        String query = "DELETE FROM product WHERE product_id = ?";
        
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setInt(1, productId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("ProductService - SQL Error deleting product: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Retrieves a limited number of products from the database
     * 
     * @param limit the maximum number of products to retrieve
     * @param sortBy the sorting criteria (price-low-high, price-high-low, relevancy, newest)
     * @return List of ProductModel objects, or empty list if none found
     */
    public List<ProductModel> getLimitedProducts(int limit, String sortBy) {
        if (isConnectionError) {
            System.out.println("ProductService - Connection Error!");
            return new ArrayList<>();
        }
        
        List<ProductModel> products = new ArrayList<>();
        String query = "SELECT * FROM product LIMIT ?";
        
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setInt(1, limit);
            ResultSet result = stmt.executeQuery();
            
            while (result.next()) {
                ProductModel product = mapResultSetToProduct(result);
                products.add(product);
            }
            
        } catch (SQLException e) {
            System.out.println("ProductService - SQL Error retrieving products: " + e.getMessage());
            e.printStackTrace();
        }
        
        return products;
    }
    
    /**
     * Retrieves all products from the database with optional sorting
     * 
     * @param sortBy the sorting criteria (price-low-high, price-high-low, relevancy, newest)
     * @return List of ProductModel objects, or empty list if none found
     */
    public List<ProductModel> getAllProducts(String sortBy) {
        if (isConnectionError) {
            System.out.println("ProductService - Connection Error!");
            return new ArrayList<>();
        }
        List<ProductModel> products = new ArrayList<>();
        String query = "SELECT * FROM product";
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                ProductModel product = mapResultSetToProduct(result);
                products.add(product);
            }
            // Sort the products based on the sortBy parameter
            switch (sortBy) {
                case "price-low-high":
                    products.sort((p1, p2) -> p1.getPrice().compareTo(p2.getPrice()));
                    break;
                case "price-high-low":
                    products.sort((p1, p2) -> p2.getPrice().compareTo(p1.getPrice()));
                    break;
                case "newest":
                    products.sort((p1, p2) -> p2.getCreatedAt().compareTo(p1.getCreatedAt()));
                    break;
                case "relevancy":
                default:
                    // Default sorting by relevancy (could be based on popularity, ratings, etc.)
                    // For now, we'll keep the original order
                    break;
            }
        } catch (SQLException e) {
            System.out.println("ProductService - SQL Error retrieving all products: " + e.getMessage());
            e.printStackTrace();
        }
        return products;
    }
    
    /**
     * Helper method to map a ResultSet row to a ProductModel object
     */
    private ProductModel mapResultSetToProduct(ResultSet result) throws SQLException {
        return new ProductModel(
            result.getInt("product_id"),
            result.getString("name"),
            result.getString("description"),
            result.getString("brand"),
            result.getString("category"),
            result.getString("tags"),
            result.getBigDecimal("price"),
            result.getInt("stock_quantity"),
            result.getBigDecimal("weight"),
            result.getString("image"),
            result.getString("dimensions"),
            result.getTimestamp("created_at").toLocalDateTime()
        );
    }
} 