/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

public class User extends Person{
    private String bio;
    private final int warning;

    public User(int id, String password, String name, String email, String phone, String picPath, String bio, int warning) {
        super(id, password, name, email, phone, picPath);
        this.bio = bio;
        this.warning = warning;
    }    

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public int getWarning() {
        return warning;
    }

}
