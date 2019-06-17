/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catolicasc.foodtruck.repositories;

import com.catolicasc.foodtruck.ConnectionFactory;
import com.catolicasc.foodtruck.models.Customer;
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
public class CustomerRepository {
    private static final String TABLE_NAME = "CUSTOMERS";
       
    private final Connection connection;
    
    public CustomerRepository() {
        connection = new ConnectionFactory().getConnection();
    }
    
    public Customer find(Integer customerId) {
        try{
            String sql = "SELECT ID, NAME, EMAIL, ADDRESS FROM " + TABLE_NAME +
                    " WHERE ID = ?";
            PreparedStatement selectStmt = connection.prepareStatement(sql);
            selectStmt.setInt(1, customerId);
            ResultSet resultSet = selectStmt.executeQuery();
            
            Customer customer = null;
            
            if(resultSet.first()) {
                customer = new Customer();
            
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("NAME");
                String email = resultSet.getString("EMAIL");
                String address = resultSet.getString("ADDRESS");
                   
                customer.setId(id);
                customer.setName(name);
                customer.setEmail(email);
                customer.setAddress(address);
            }
           
            return customer;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public ArrayList<Customer> getAll() {
        try{
            ArrayList<Customer> customers = new ArrayList<>();
            
            String sql = "SELECT ID, NAME, EMAIL, ADDRESS FROM " + TABLE_NAME;
            Statement selectStmt = connection.createStatement();
            ResultSet resultSet = selectStmt.executeQuery(sql);
            
            while(resultSet.next()) {
                Customer customer = new Customer();
            
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("NAME");
                String email = resultSet.getString("EMAIL");
                String address = resultSet.getString("ADDRESS");
                   
                customer.setId(id);
                customer.setName(name);
                customer.setEmail(email);
                customer.setAddress(address);
                
                customers.add(customer);
            }
            
            return customers;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public Customer add(Customer customer) {
        try {
            String sql = "INSERT INTO " + TABLE_NAME + " (NAME, EMAIL, ADDRESS)"
                    + "VALUES (?, ?, ?)";
            PreparedStatement insertStmt = connection.prepareStatement(sql);
            insertStmt.setString(1, customer.getName());
            insertStmt.setString(2, customer.getEmail());
            insertStmt.setString(3, customer.getAddress());
            insertStmt.executeUpdate();
            insertStmt.close();
            
            sql = "SELECT LAST_INSERT_ID() AS ID";
            Statement selectStmt = connection.createStatement();
            ResultSet resultSet = selectStmt.executeQuery(sql);
            
            while(resultSet.next()) {
                Integer id  = resultSet.getInt("ID");
                customer.setId(id);
            }
            
            selectStmt.close();
            
            return customer;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
   
    public void saveOrUpdate(Customer customer) {
        try {
            String sql = "UPDATE " + TABLE_NAME + " SET NAME=?, EMAIL=?, "
                    + "ADDRESS=? WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(
                    sql);
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getEmail());
            preparedStatement.setString(3, customer.getAddress());
            preparedStatement.setInt(4, customer.getId());
            preparedStatement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(UserRepository.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }
    
    public void delete(Customer customer) {
        try {
            String sql = "DELETE FROM " + TABLE_NAME + " WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(
                    sql);
            preparedStatement.setInt(1, customer.getId());
            preparedStatement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(UserRepository.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }
}
