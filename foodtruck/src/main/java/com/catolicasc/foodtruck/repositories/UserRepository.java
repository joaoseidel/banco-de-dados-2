/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catolicasc.foodtruck.repositories;

import com.catolicasc.foodtruck.ConnectionFactory;
import com.catolicasc.foodtruck.models.User;
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
 * @author guilherme
 */
public class UserRepository {
    private static final String TABLE_NAME = "USERS";
    
    private final Connection connection;
    
    public UserRepository() {
        connection = new ConnectionFactory().getConnection();
    }
    
    public User find(Integer userId) {
        try{
            String sql = "SELECT ID, NAME, EMAIL FROM " + TABLE_NAME + " WHERE ID = ?";
            PreparedStatement selectStmt = connection.prepareStatement(sql);
            selectStmt.setInt(1, userId);
            ResultSet resultSet = selectStmt.executeQuery();
            
            User user = null;
            
            if(resultSet.first()) {
                user = new User();
            
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("NAME");
                String email = resultSet.getString("EMAIL");

                user.setId(id);
                user.setName(name);
                user.setEmail(email);
            }
            
            return user;
        }catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public ArrayList<User> getAll() {
        try{
            ArrayList<User> users = new ArrayList<>();
            
            String sql = "SELECT ID, NAME, EMAIL FROM " + TABLE_NAME;
            Statement selectStmt = connection.createStatement();
            ResultSet resultSet = selectStmt.executeQuery(sql);
            while(resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("NAME");
                String email = resultSet.getString("EMAIL");
                
                User user = new User();
                user.setId(id);
                user.setName(name);
                user.setEmail(email);
                
                users.add(user);
            }
            
            return users;
        }catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public User add(User user) {
        try {
            String sql = "INSERT INTO " + TABLE_NAME + " (NAME, EMAIL) VALUES (?, ?)";
            PreparedStatement insertStmt = connection.prepareStatement(sql);
            insertStmt.setString(1, user.getName());
            insertStmt.setString(2, user.getEmail());
            insertStmt.executeUpdate();
            insertStmt.close();
            
            sql = "SELECT LAST_INSERT_ID() AS ID";
            Statement selectStmt = connection.createStatement();
            ResultSet resultSet = selectStmt.executeQuery(sql);
            while(resultSet.next()) {
                Integer id  = resultSet.getInt("ID");
                user.setId(id);
            }
            selectStmt.close();
            
            return user;
        }catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
   
    public void saveOrUpdate(User user) {
        try {
            String sql = "UPDATE " + TABLE_NAME + " SET name=?, email=? WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setInt(3, user.getId());
            preparedStatement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(UserRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void delete(User user) {
        try {
            String sql = "DELETE FROM " + TABLE_NAME + " WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, user.getId());
            preparedStatement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(UserRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
