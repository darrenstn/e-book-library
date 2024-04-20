/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.time.LocalDateTime;

import models.enums.Category;
import models.enums.Genre;

public class BorrowedBook extends Book {
    int idListBorrow;
    LocalDateTime dateReturn;
    LocalDateTime dateBorrow;

    public BorrowedBook(int idListBorrow, LocalDateTime dateReturn, LocalDateTime dateBorrow, String isbn, int year, String title, Genre genre, Category category, String author, int stock, String picPath) {
        super(isbn, year, title, genre, category, author, stock, picPath);
        this.idListBorrow = idListBorrow;
        this.dateReturn = dateReturn;
        this.dateBorrow = dateBorrow;
    }

    public int getIdListBorrow() {
        return idListBorrow;
    }

    public void setIdListBorrow(int idListBorrow) {
        this.idListBorrow = idListBorrow;
    }

    public LocalDateTime getDateReturn() {
        return dateReturn;
    }

    public void setDateReturn(LocalDateTime dateReturn) {
        this.dateReturn = dateReturn;
    }

    public LocalDateTime getDateBorrow() {
        return dateBorrow;
    }

    public void setDateBorrow(LocalDateTime dateBorrow) {
        this.dateBorrow = dateBorrow;
    }

}
