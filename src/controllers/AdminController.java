/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import models.Admin;
import models.Person;
import models.User;

/**
 *
 * @author Darren
 */
public class AdminController {
    public ArrayList<Person> getAllUser() {
        if (!(SingletonManager.getInstance().getPerson() instanceof Admin)) {return null;}
        DatabaseHandler.getInstance().connect();
        String query = "SELECT * FROM person p JOIN user u ON p.id = u.id WHERE p.id IN (SELECT id FROM `user`);";
        ArrayList<Person> result = new ArrayList<>();
        try {
            Statement stmt = DatabaseHandler.getInstance().con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                result.add(new User(rs.getInt("id"), rs.getString("password"), rs.getString("name"), rs.getString("email"), rs.getString("phone"), rs.getString("pic_path"), rs.getString("bio"), rs.getInt("warning")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public boolean increaseWarning(int idUser) {
        DatabaseHandler.getInstance().connect();
        String query = "SELECT warning FROM user WHERE id=" + idUser + ";";
        try {
            Statement stmt = DatabaseHandler.getInstance().con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                int tempWarning = rs.getInt("warning");
                tempWarning++;
                
                String queryUpdate = "UPDATE user SET warning =" + tempWarning + " WHERE id = " + idUser + ";";
                Statement stmt2 = DatabaseHandler.getInstance().con.createStatement();
                stmt2.execute(queryUpdate);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean decreaseWarning(int idUser) {
        DatabaseHandler.getInstance().connect();
        String query = "SELECT warning FROM user WHERE id=" + idUser + ";";
        try {
            Statement stmt = DatabaseHandler.getInstance().con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                int tempWarning = rs.getInt("warning");
                tempWarning--;
                
                String queryUpdate = "UPDATE user SET warning =" + tempWarning + " WHERE id = " + idUser + ";";
                Statement stmt2 = DatabaseHandler.getInstance().con.createStatement();
                stmt2.execute(queryUpdate);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}