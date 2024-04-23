/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package main;
import controllers.AccessController;
import controllers.AdminController;
import controllers.BookController;
import controllers.SessionManager;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.ArrayList;
import models.Admin;
import models.Review;
import models.Book;
import models.BookQueue;
import models.BorrowedBook;
import models.enums.Category;
import models.enums.Genre;
import models.enums.SearchType;
import models.Person;
import models.User;
import views.GUIHome;

/**
 *
 * @author marti
 */
public class Main {

    public static void main(String[] args) {
        new GUIHome();
    }
    
}