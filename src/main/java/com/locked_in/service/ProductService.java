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
    
    private final Connection dbConn;
    private final boolean isConnectionError;
    private final ReviewService reviewService;
    
    /**
     * Constructor initializes the database connection.
     * Sets the connection error flag if the connection fails.
     */
    public ProductService() {
        Connection tempConn = null;
        boolean connectionError = true;
        try {
            tempConn = DbConfig.getDbConnection();
            connectionError = false;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("ProductService - Connection Error: " + e.getMessage());
        }
        this.dbConn = tempConn;
        this.isConnectionError = connectionError;
        this.reviewService = new ReviewService();
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
     * Checks if a product has sufficient stock for the requested quantity
     * 
     * @param productId the ID of the product to check
     * @param requestedQuantity the quantity requested
     * @return true if sufficient stock available, false otherwise
     */
    public boolean hasAvailableStock(Integer productId, int requestedQuantity) {
        if (isConnectionError) {
            System.out.println("ProductService - Connection Error!");
            return false;
        }
        
        String query = "SELECT stock_quantity FROM product WHERE product_id = ?";
        
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setInt(1, productId);
            ResultSet result = stmt.executeQuery();
            
            if (result.next()) {
                int stockQuantity = result.getInt("stock_quantity");
                return stockQuantity >= requestedQuantity;
            }
        } catch (SQLException e) {
            System.out.println("ProductService - SQL Error checking stock: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    /**
     * Updates the stock quantity of a product.
     * 
     * This method can be used to both increase and decrease stock quantities.
     * For decreasing stock, pass a negative quantityChange value.
     * 
     * @param productId the ID of the product to update
     * @param quantityChange the amount to change the stock by (positive or negative)
     * @return true if the update was successful, false otherwise
     */
    public boolean updateStockQuantity(Integer productId, int quantityChange) {
        if (isConnectionError) {
            System.out.println("ProductService - Connection Error!");
            return false;
        }
        
        String query = "UPDATE product SET stock_quantity = stock_quantity + ? WHERE product_id = ?";
        
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setInt(1, quantityChange);
            stmt.setInt(2, productId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("ProductService - SQL Error updating stock: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves a limited number of products with optional sorting.
     * 
     * This method is useful for displaying featured products or creating
     * product previews on various pages.
     * 
     * @param limit the maximum number of products to retrieve
     * @param sortBy the sorting criteria (price-low-high, price-high-low, relevancy, newest)
     * @return List of sorted products, limited to the specified count
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
     * Retrieves all products with optional sorting.
     * 
     * This method returns the complete product catalog, sorted according
     * to the specified criteria.
     * 
     * @param sortBy the sorting criteria (price-low-high, price-high-low, relevancy, newest)
     * @return List of all products, sorted according to the criteria
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
     * Searches for products based on query, category, and sorting criteria.
     * 
     * This method performs a flexible search that can:
     * - Filter by category if specified
     * - Search across product names, descriptions, and tags
     * - Sort results according to the specified criteria
     * 
     * @param query the search query string
     * @param category optional category filter (can be null)
     * @param sortBy the sorting criteria (price-low-high, price-high-low, relevancy, newest)
     * @return List of matching products, sorted according to the criteria
     */
    public List<ProductModel> searchProducts(String query, String category, String sortBy) {
        if (isConnectionError) {
            System.out.println("ProductService - Connection Error!");
            return new ArrayList<>();
        }
        
        List<ProductModel> products = new ArrayList<>();
        
        try {
            StringBuilder sql = new StringBuilder(
                "SELECT * FROM product WHERE (name LIKE ? OR description LIKE ? OR brand LIKE ? OR tags LIKE ?)");
            
            // Add category filter if specified
            if (category != null && !category.isEmpty()) {
                sql.append(" AND category = ?");
            }
            
            // Add sorting
            sql.append(" ORDER BY ");
            switch (sortBy) {
                case "price-low-high":
                    sql.append("price ASC");
                    break;
                case "price-high-low":
                    sql.append("price DESC");
                    break;
                case "newest":
                    sql.append("created_at DESC");
                    break;
                default: // relevancy
                    sql.append("CASE WHEN name LIKE ? THEN 1 WHEN description LIKE ? THEN 2 ELSE 3 END, name ASC");
            }
            
            try (PreparedStatement stmt = dbConn.prepareStatement(sql.toString())) {
                String searchPattern = "%" + query + "%";
                stmt.setString(1, searchPattern);
                stmt.setString(2, searchPattern);
                stmt.setString(3, searchPattern);
                stmt.setString(4, searchPattern);
                
                int paramIndex = 5;
                if (category != null && !category.isEmpty()) {
                    stmt.setString(paramIndex++, category);
                }
                
                if (sortBy.equals("relevancy")) {
                    stmt.setString(paramIndex++, searchPattern);
                    stmt.setString(paramIndex, searchPattern);
                }
                
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        products.add(mapResultSetToProduct(rs));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("ProductService - SQL Error searching products: " + e.getMessage());
            e.printStackTrace();
        }
        
        return products;
    }
    
    /**
     * Maps a database ResultSet row to a ProductModel object.
     * 
     * This private helper method extracts all product fields from a database
     * result row and creates a corresponding ProductModel instance.
     * 
     * @param result the ResultSet containing the product data
     * @return a new ProductModel instance with the data from the ResultSet
     * @throws SQLException if there is an error accessing the ResultSet data
     */
    private ProductModel mapResultSetToProduct(ResultSet result) throws SQLException {
        ProductModel product = new ProductModel();
        product.setProductId(result.getInt("product_id"));
        product.setName(result.getString("name"));
        product.setDescription(result.getString("description"));
        product.setBrand(result.getString("brand"));
        product.setCategory(result.getString("category"));
        product.setTags(result.getString("tags"));
        product.setPrice(result.getBigDecimal("price"));
        product.setStockQuantity(result.getInt("stock_quantity"));
        product.setWeight(result.getBigDecimal("weight"));
        product.setImage(result.getString("image"));
        product.setDimensions(result.getString("dimensions"));
        product.setCreatedAt(result.getTimestamp("created_at").toLocalDateTime());
        
        // Get average rating for the product
        product.setAverageRating(reviewService.getAverageRating(product.getProductId()));
        
        return product;
    }
} 