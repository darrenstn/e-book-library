/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.time.LocalDateTime;

public class BookQueue {
    private int idUser;
    private LocalDateTime date;
    
    public BookQueue(int idUser, LocalDateTime date) {
        this.idUser = idUser;
        this.date = date;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    } 

    @Override
    public String toString() {
        return "BookQueue{" + "idUser=" + idUser + ", date=" + date + '}';
    }
    
}