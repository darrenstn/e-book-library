package controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import models.Person;
import models.User;

public class Profile {
    
    public void seeProfile(String name) {
        DatabaseHandler.getInstance().connect();
        String query = "SELECT * FROM person INNER JOIN user ON person.id = user.id WHERE person.name=?";
        try {
            PreparedStatement stmt = DatabaseHandler.getInstance().con.prepareStatement(query);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            
            // Print user's profile information
            while (rs.next()) {
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Phone: " + rs.getString("phone"));
                System.out.println("Bio: " + rs.getString("bio"));
                // Add other profile information as needed
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public boolean updateProfile(Person person) {
        DatabaseHandler.getInstance().connect();
        String query = "UPDATE person p INNER JOIN user u ON p.id = u.id " +
                       "SET p.name=?, p.email=?, p.phone=?, p.pic_path=?, u.bio=? " +
                       "WHERE p.name=?";
        try {
            PreparedStatement stmt = DatabaseHandler.getInstance().con.prepareStatement(query);
            stmt.setString(1, person.getName());
            stmt.setString(2, person.getEmail());
            stmt.setString(3, person.getPhone());
            stmt.setString(4, person.getPicPath());
            stmt.setString(5, ((User) person).getBio()); // Pastikan person adalah instance dari User
            stmt.setString(6, person.getName()); // Where clause untuk mencocokkan nama yang lama
            
            int rowsAffected = stmt.executeUpdate();
            
            return (rowsAffected > 0);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String uploadFile(String name) {        
        JFileChooser jUploadFile = new JFileChooser();
        jUploadFile.setCurrentDirectory(new File(System.getProperty("user.dir")));
        jUploadFile.addChoosableFileFilter(new FileNameExtensionFilter("Images", "jpg", "png"));
        int result = jUploadFile.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jUploadFile.getSelectedFile();
            String jFileName = selectedFile.getName();
            if (jFileName.endsWith(".jpg") || jFileName.endsWith(".png") || jFileName.endsWith(".JPG") || jFileName.endsWith(".PNG")) {
                try {
                    // Extract file name and extension
                    String[] parts = jFileName.split("\\.");
                    String fileNameWithoutExtension = name; // Using user's input name as file name
                    String fileExtension = parts[parts.length - 1];
                    
                    // Construct new file path
                    String newPath = "src/images/" + fileNameWithoutExtension + "." + fileExtension;
                    Path src = selectedFile.toPath();
                    Path dest = new File(newPath).toPath();
                    
                    // Copy file to the new path
                    Files.copy(src, dest, StandardCopyOption.REPLACE_EXISTING);
                    return dest.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error copying file!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Select an image file with extension .jpg or .png!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return null;
    }
    
}
