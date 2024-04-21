/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import models.Admin;
import models.Book;
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
        if (!(SingletonManager.getInstance().getPerson() instanceof Admin)) {return false;}
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
        if (!(SingletonManager.getInstance().getPerson() instanceof Admin)) {return false;}
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
    
    public boolean addBook(Book book) {
        if (!(SingletonManager.getInstance().getPerson() instanceof Admin)) {return false;}
        DatabaseHandler.getInstance().connect();
        String query = "INSERT INTO book (isbn, year, title, genre, category, author, stock, pic_path) VALUES(?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement stmt =  DatabaseHandler.getInstance().con.prepareStatement(query);
            stmt.setString(1, book.getIsbn());
            stmt.setInt(2, book.getYear());
            stmt.setString(3, book.getTitle());
            stmt.setString(4, book.getGenre().toString());
            stmt.setString(5, book.getCategory().toString());
            stmt.setString(6, book.getAuthor());
            stmt.setInt(7, book.getStock());
            stmt.setString(8, book.getPicPath());
            stmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}