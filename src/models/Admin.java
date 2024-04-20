/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

public class Admin extends Person{
    String division;

    public Admin(String division, int id, String password, String name, String email, String phone, String picPath) {
        super(id, password, name, email, phone, picPath);
        this.division = division;
    }

    public String getDivision() {
        return division;
    }
    
}