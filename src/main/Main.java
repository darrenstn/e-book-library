/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package main;
import controllers.Access;
import controllers.AdminController;
import controllers.BookController;
import controllers.SingletonManager;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.ArrayList;
import models.Admin;
import models.Review;
import models.Book;
import models.BookQueue;
import models.History;
import models.BorrowedBook;
import models.enums.Category;
import models.enums.Genre;
import models.enums.SearchType;
import models.Person;
import models.User;
import views.Home;

/**
 *
 * @author Darren
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Home();
        // TODO code application logic here
//        BookController bc = new BookController();
//        Access a = new Access();
//        a.login("Admin User", "adminpass");
//        if (SingletonManager.getInstance().getPerson()!=null) {
//            System.out.println("Berhasil Login");
//            System.out.println(((Admin) SingletonManager.getInstance().getPerson()).getDivision());
//        } else {
//            System.out.println("Gagal Login");
//        }
//        
//        AdminController ac = new AdminController();
//        ArrayList<Person> allUser = ac.getAllUser();
//        if(allUser!=null){
//            System.out.println("Dapat diakses");
//            for(Person personTmp : allUser){
//                System.out.println("User id : " + personTmp.getId());
//            }
//        } else {
//            System.out.println("Person Not Authorized");
//        }
//        Book bookTmp = bc.searchBook("9876543210");
//        System.out.println(ac.addBook(new Book("isbn", 2000, "testBook", Genre.ACTION, Category.FICTION, "testAuthor", 10, "testPath")));
//        ArrayList<Book> book;
//        System.out.println(bc.addBookReview(new Review(SingletonManager.getInstance().getPerson().getId(), 3, LocalDate.now(), "test Review", null), "1234567890", (User) SingletonManager.getInstance().getPerson()));
//        System.out.println(bc.borrowBook(bookTmp, (User) SingletonManager.getInstance().getPerson()));
//        System.out.println(bc.addBookQueue(bookTmp, (User) SingletonManager.getInstance().getPerson()));
//        ArrayList<BookQueue> bookQueue = bc.getBookQueue(bookTmp);
//        for(BookQueue bookQueueTmpL : bookQueue) {
//            System.out.println( bookQueueTmpL.toString());
//        }
//        System.out.println(bc.addBookQueue(bookTmp, (User) SingletonManager.getInstance().getPerson()));
//        ArrayList<History> bookHistory = bc.getBookHistory(bookTmp);
//        for(History bookTmpL : bookHistory) {
//            System.out.println(bookTmpL.toString());
//        }
//////        book = bc.searchBook(SearchType.GENRE, "ACTION", (User) SingletonManager.getInstance().getPerson());
//////        for(Book bookTmpL : book){
//////            System.out.println(bookTmpL.getTitle());
//////        }
//////        Book test = new BorrowedBook(1, "isbn", 2000, "test", Genre.ACTION, Category.FICTION, "tst", 10, "test", null, null, null, LocalDateTime.now(), LocalDateTime.now());
//////        System.out.println(test.getIsbn());
////        System.out.println(bc.ableToBorrow(bookTmp, (User) SingletonManager.getInstance().getPerson()));
//        book = bc.getListBorrow((User) SingletonManager.getInstance().getPerson());
//        for(Book bookTmpL : book) {
//            if(bookTmpL.getIsbn().equals("9876543210")) {
//                bookTmp = bookTmpL;
//            }
//        }
//        System.out.println("=============");
//        bookHistory = bc.getBookHistory(bookTmp);
//        for(History bookTmpL : bookHistory) {
//            System.out.println(bookTmpL.toString());
//        }
//        System.out.println(bc.returnBook((BorrowedBook) bookTmp));
//        System.out.println(bc.addBookQueue(bookTmp, (User) SingletonManager.getInstance().getPerson()));
//        System.out.println(bc.borrowBook(bookTmp, (User) SingletonManager.getInstance().getPerson()));
        
//        System.out.println(bc.ableToBorrow(bookTmp, (User) SingletonManager.getInstance().getPerson()));
//        bookHistory = bc.getBookHistory(bookTmp);
//        for(History bookTmpL : bookHistory) {
//            System.out.println(bookTmpL.toString());
//        }
    }
    
}