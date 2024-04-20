/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.time.LocalDate;

public class Review {
    private int idUser;
    private int rating;
    private LocalDate date;
    private String content;
    
    public Review(int idUser, int rating, LocalDate date, String content) {
        this.idUser = idUser;
        this.rating = rating;
        this.date = date;
        this.content = content;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }       

    @Override
    public String toString() {
        return "Review{" + "idUser=" + idUser + ", rating=" + rating + ", date=" + date + ", content=" + content + '}';
    }
}
