/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import models.User;

/**
 *
 * @author Darren
 */
public class RegisterController {
    
    public boolean registerNewUser(User user) {
        if(checkIfNameAvailable(user.getName())) { return false; }
        
        DatabaseHandler.getInstance().connect();
        String query = "INSERT INTO person (password, name, email, phone, pic_path) VALUES(?,?,?,?,?)";
        try {
            PreparedStatement stmt =  DatabaseHandler.getInstance().con.prepareStatement(query);
            stmt.setString(1, user.getPassword());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPhone());
            stmt.setString(5, user.getPicPath());
            stmt.executeUpdate();
            
            String query2 = "SELECT id FROM person WHERE name='" + user.getName() + "'";
            Statement stmt2 = DatabaseHandler.getInstance().con.createStatement();
            ResultSet rs2 = stmt2.executeQuery(query2);
            
            int tmpId = -1;
            while (rs2.next()) {
                tmpId = rs2.getInt("id");
            }
            
            String query3 = "INSERT INTO user (id, bio, warning) VALUES(?,?,?)";
            PreparedStatement stmt3 =  DatabaseHandler.getInstance().con.prepareStatement(query3);
            stmt3.setInt(1, tmpId);
            stmt3.setString(2, user.getBio());
            stmt3.setInt(3, user.getWarning());
            stmt3.executeUpdate();
            
            return (true);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return (false);
        }
    }
    
    public boolean checkIfNameAvailable(String name){
        DatabaseHandler.getInstance().connect();
        String query = "SELECT name FROM person WHERE name='" + name + "'";
        try {
            Statement stmt = DatabaseHandler.getInstance().con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}