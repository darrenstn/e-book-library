/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import models.Admin;
import models.Person;
import models.User;


public class Access {
    public void login(String name, String password) {
        DatabaseHandler.getInstance().connect();
        String query = "SELECT * FROM person WHERE name='" + name + "' AND password='" + password + "'";
        Person person = null;
        try {
            Statement stmt = DatabaseHandler.getInstance().con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                String checkAdmin = "SELECT * FROM admin WHERE id='" + rs.getInt("id") + "'";
                Statement stmtCheckAdmin = DatabaseHandler.getInstance().con.createStatement();
                ResultSet rsCheckAdmin = stmtCheckAdmin.executeQuery(checkAdmin);
                if (rsCheckAdmin.next()) {
                    person = new Admin(rsCheckAdmin.getString("division"), rs.getInt("id"), rs.getString("password"), rs.getString("name"), rs.getString("email"), rs.getString("phone"), rs.getString("pic_path"));
                }
                
                if (person == null) {
                    String checkUser = "SELECT * FROM user WHERE id='" + rs.getInt("id") + "'";
                    Statement stmtCheckUser = DatabaseHandler.getInstance().con.createStatement();
                    ResultSet rsCheckUser = stmtCheckUser.executeQuery(checkUser);
                    if (rsCheckUser.next()) {
                        if (rsCheckUser.getInt("warning")<3) {
                            person = new User(rs.getInt("id"), rs.getString("password"), rs.getString("name"), rs.getString("email"), rs.getString("phone"), rs.getString("pic_path"), rsCheckUser.getString("bio"), rsCheckUser.getInt("warning"));
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SingletonManager.getInstance().setPerson(person);
    }
    
    public void logoff(){
        SingletonManager.getInstance().setPerson(null);
    }
}
