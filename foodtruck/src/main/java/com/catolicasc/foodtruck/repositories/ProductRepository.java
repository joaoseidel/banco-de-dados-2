/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catolicasc.foodtruck.repositories;

import com.catolicasc.foodtruck.ConnectionFactory;
import com.catolicasc.foodtruck.models.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
public class ProductRepository {
    private static final String TABLE_NAME = "PRODUCTS";

    private final Connection connection;
    
    public ProductRepository() {
        connection = new ConnectionFactory().getConnection();
    }
    
    public Product find(Integer productId) {
        try{
            String sql = "SELECT ID, DESCRIPTION, PRICE FROM " + TABLE_NAME + 
                    " WHERE ID = ?";
            PreparedStatement selectStmt = connection.prepareStatement(sql);
            selectStmt.setInt(1, productId);
            ResultSet resultSet = selectStmt.executeQuery();
            
            Product product = null;
            
            if(resultSet.first()) {
                product = new Product();
            
                int id = resultSet.getInt("ID");
                String description = resultSet.getString("DESCRIPTION");
                Double price = resultSet.getDouble("PRICE");

                product.setId(id);
                product.setDescription(description);
                product.setPrice(price);
            }
           
            return product;
        }catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public ArrayList<Product> getAll() {
        try{
            ArrayList<Product> products = new ArrayList<>();
            
            String sql = "SELECT ID, DESCRIPTION, PRICE FROM " + TABLE_NAME;
            Statement selectStmt = connection.createStatement();
            ResultSet resultSet = selectStmt.executeQuery(sql);
            
            while(resultSet.next()) {
                int id = resultSet.getInt("ID");
                String description = resultSet.getString("DESCRIPTION");
                Double price = resultSet.getDouble("PRICE");
                
                Product product = new Product();
                product.setId(id);
                product.setDescription(description);
                product.setPrice(price);
                
                products.add(product);
            }
            
            return products;
        }catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public Product add(Product product) {
        try {
            String sql = "INSERT INTO " + TABLE_NAME + " (DESCRIPTION, PRICE) "
                    + "VALUES (?, ?)";
            PreparedStatement insertStmt = connection.prepareStatement(sql);
            insertStmt.setString(1, product.getDescription());
            insertStmt.setDouble(2, product.getPrice());
            insertStmt.executeUpdate();
            insertStmt.close();
            
            sql = "SELECT LAST_INSERT_ID() AS ID";
            Statement selectStmt = connection.createStatement();
            ResultSet resultSet = selectStmt.executeQuery(sql);
            
            while(resultSet.next()) {
                Integer id  = resultSet.getInt("ID");
                product.setId(id);
            }
            
            selectStmt.close();
            
            return product;
        }catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
   
    public void saveOrUpdate(Product product) {
        try {
            String sql = "UPDATE " + TABLE_NAME + " SET description=?, price=? "
                    + "WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(
                    sql);
            preparedStatement.setString(1, product.getDescription());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setInt(3, product.getId());
            preparedStatement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(UserRepository.class.getName()).log(Level.SEVERE, 
                    null, ex);
        }
    }
    
    public void delete(Product product) {
        try {
            String sql = "DELETE FROM " + TABLE_NAME + " WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(
                    sql);
            preparedStatement.setInt(1, product.getId());
            preparedStatement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(UserRepository.class.getName()).log(Level.SEVERE, 
                    null, ex);
        }
    }
}
