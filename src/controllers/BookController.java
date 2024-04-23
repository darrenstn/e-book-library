/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import models.Book;
import models.BookQueue;
import models.BorrowedBook;
import models.enums.Category;
import models.enums.Genre;
import models.enums.SearchType;
import models.Review;
import models.User;

/**
 *
 * @author Darren
 */
public class BookController {
    public ArrayList<Book> getAllBooks(){
        updateListBorrow();
        DatabaseHandler.getInstance().connect();
        ArrayList<Book> result = new ArrayList<>();
        
        String query = "SELECT * FROM book";

        try {
            Statement stmt = DatabaseHandler.getInstance().con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Genre genreTmp = Genre.ACTION;
                for(Genre genreLoop : Genre.values()) {
                    if(genreLoop.toString().equals(rs.getString("genre"))) {
                        genreTmp = genreLoop;
                    }
                }
                
                Category categoryTmp = Category.FICTION;
                for(Category categoryLoop : Category.values()) {
                    if(categoryLoop.toString().equals(rs.getString("category"))) {
                        categoryTmp = categoryLoop;
                    }
                }
                result.add(new Book(rs.getString("isbn"), rs.getInt("year"), rs.getString("title"), genreTmp, categoryTmp, rs.getString("author"), rs.getInt("stock"), rs.getString("pic_path")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    //Method searchBook berdasarkan tipe(dari enum), search merupakan value dari enum(hanya bisa satu), dan user bisa diisi null atau jika diambil dari user yang login(bisa ambil dari singleton manager) maka akan mencari buku yang saat ini tidak dipinjam
    public ArrayList<Book> searchBook(SearchType type, String search, User user){
        updateListBorrow();
        DatabaseHandler.getInstance().connect();
        ArrayList<Book> result = new ArrayList<>();
        String searchType = type.toString().toLowerCase();
        
        String query = "SELECT * FROM book WHERE " + searchType + "='" + search + "'";
        if(user != null) {
            query += " AND isbn NOT IN (SELECT isbn FROM listborrow WHERE id_user=" + user.getId() + " AND date_return IS NULL)";
        }
        try {
            Statement stmt = DatabaseHandler.getInstance().con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Genre genreTmp = Genre.ACTION;
                for(Genre genreLoop : Genre.values()) {
                    if(genreLoop.toString().equals(rs.getString("genre"))) {
                        genreTmp = genreLoop;
                    }
                }
                
                Category categoryTmp = Category.FICTION;
                for(Category categoryLoop : Category.values()) {
                    if(categoryLoop.toString().equals(rs.getString("category"))) {
                        categoryTmp = categoryLoop;
                    }
                }
                result.add(new Book(rs.getString("isbn"), rs.getInt("year"), rs.getString("title"), genreTmp, categoryTmp, rs.getString("author"), rs.getInt("stock"), rs.getString("pic_path")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    //Method searchBook dengan parameter isbn hanya mengembalikan satu buku
    public Book searchBook(String isbn){
        DatabaseHandler.getInstance().connect();
        Book result = null;
        
        String query = "SELECT * FROM book WHERE isbn='" + isbn + "';";

        try {
            Statement stmt = DatabaseHandler.getInstance().con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Genre genreTmp = Genre.ACTION;
                for(Genre genreLoop : Genre.values()) {
                    if(genreLoop.toString().equals(rs.getString("genre"))) {
                        genreTmp = genreLoop;
                    }
                }
                
                Category categoryTmp = Category.FICTION;
                for(Category categoryLoop : Category.values()) {
                    if(categoryLoop.toString().equals(rs.getString("category"))) {
                        categoryTmp = categoryLoop;
                    }
                }
                result = new Book(rs.getString("isbn"), rs.getInt("year"), rs.getString("title"), genreTmp, categoryTmp, rs.getString("author"), rs.getInt("stock"), rs.getString("pic_path"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    //Method getPopularBook untuk mendapatkan 5 buku terbanyak dipinjam
    public ArrayList<Book> getPopularBook(){
        updateListBorrow();
        DatabaseHandler.getInstance().connect();
        ArrayList<Book> result = new ArrayList<>();
        
        String query = "SELECT lb.isbn, (SELECT b.`year` FROM book b WHERE isbn = lb.isbn) AS `year`, (SELECT b.title FROM book b WHERE isbn = lb.isbn) AS title, (SELECT b.genre FROM book b WHERE isbn = lb.isbn) AS genre, (SELECT b.category FROM book b WHERE isbn = lb.isbn) AS category, (SELECT b.author FROM book b WHERE isbn = lb.isbn) AS author, (SELECT b.stock FROM book b WHERE isbn = lb.isbn) AS stock, (SELECT b.pic_path FROM book b WHERE isbn = lb.isbn) AS pic_path FROM listborrow lb GROUP BY lb.isbn ORDER BY COUNT(lb.isbn) DESC LIMIT 5;";
        try {
            Statement stmt = DatabaseHandler.getInstance().con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Genre genreTmp = Genre.ACTION;
                for(Genre genreLoop : Genre.values()) {
                    if(genreLoop.toString().equals(rs.getString("genre"))) {
                        genreTmp = genreLoop;
                    }
                }
                
                Category categoryTmp = Category.FICTION;
                for(Category categoryLoop : Category.values()) {
                    if(categoryLoop.toString().equals(rs.getString("category"))) {
                        categoryTmp = categoryLoop;
                    }
                }
                result.add(new Book(rs.getString("isbn"), rs.getInt("year"), rs.getString("title"), genreTmp, categoryTmp, rs.getString("author"), rs.getInt("stock"), rs.getString("pic_path")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    //Method getBorrowedBook, mencari satu buku yang sedang dipinjam oleh user
    public BorrowedBook getBorrowedBook (Book book, User user) {
        ArrayList<Book> listBorrow = getListBorrow(user);
        BorrowedBook result = null;
        
        for(Book bookTmpL : listBorrow) {
            if(bookTmpL.getIsbn().equals(book.getIsbn())) {
                result = ((BorrowedBook)bookTmpL);
            }
        }
        return result;
    }
    
    //Method getListBorrow dengan parameter user, mengembalikan buku(BorrowedBook) yang sedang dipinjam oleh user
    public ArrayList<Book> getListBorrow (User user) {
        updateListBorrow();
        DatabaseHandler.getInstance().connect();
        ArrayList<Book> result = new ArrayList<>();
        String querySelect = "SELECT lb.id_list_borrow, lb.isbn, (SELECT year FROM book WHERE isbn = lb.isbn) AS book_year, (SELECT title FROM book WHERE isbn = lb.isbn) AS book_title, (SELECT genre FROM book WHERE isbn = lb.isbn) AS book_genre, (SELECT category FROM book WHERE isbn = lb.isbn) AS book_category, (SELECT author FROM book WHERE isbn = lb.isbn) AS book_author, (SELECT stock FROM book WHERE isbn = lb.isbn) AS book_stock, (SELECT pic_path FROM book WHERE isbn = lb.isbn) AS book_pic_path, lb.date_borrow, lb.date_return FROM listborrow lb WHERE lb.id_user='" + user.getId() + "' AND TIMESTAMPDIFF(SECOND, lb.date_borrow, NOW())<432000 AND lb.date_return IS NULL";
    
        try {
            
            Statement stmt = DatabaseHandler.getInstance().con.createStatement();
            ResultSet rs = stmt.executeQuery(querySelect);
            while (rs.next()) {
                Genre genreTmp = Genre.ACTION;
                for(Genre genreLoop : Genre.values()) {
                    if(genreLoop.toString().equals(rs.getString("book_genre"))) {
                        genreTmp = genreLoop;
                    }
                }
                
                Category categoryTmp = Category.FICTION;
                for(Category categoryLoop : Category.values()) {
                    if(categoryLoop.toString().equals(rs.getString("book_category"))) {
                        categoryTmp = categoryLoop;
                    }
                }

                int dateReturnResult = rs.getInt("date_return");//Necessary to test null
                LocalDateTime dateReturnResultFinal = null;

                if(!rs.wasNull()) {
                    dateReturnResultFinal = rs.getTimestamp("date_return").toLocalDateTime();
                }
                result.add(new BorrowedBook(rs.getInt("id_list_borrow"), dateReturnResultFinal, rs.getTimestamp("date_borrow").toLocalDateTime(), rs.getString("isbn"), rs.getInt("book_year"), rs.getString("book_title"), genreTmp, categoryTmp, rs.getString("book_author"), rs.getInt("book_stock"), rs.getString("book_pic_path")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public boolean checkIfBookCurrentlyBorrowed (Book book, User user) {
        updateListBorrow();
        DatabaseHandler.getInstance().connect();
        String querySelect = "SELECT id_user FROM listborrow WHERE isbn='"+ book.getIsbn() +"' AND date_return IS NULL AND id_user = " + user.getId() + ";";
        
        try {
            Statement stmt = DatabaseHandler.getInstance().con.createStatement();
            ResultSet rsSelect = stmt.executeQuery(querySelect);
            
            return rsSelect.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    //Method untuk meminjam buku, dilakukan ableToBorrow(), jika bisa maka langsung insert data ke database
    public boolean borrowBook (Book book, User user) {
        if (!ableToBorrow(book, user)) {return false;}
        DatabaseHandler.getInstance().connect();
        String query = "INSERT INTO listborrow (isbn, id_user, date_borrow) VALUES(?,?,?)";
        try {
            PreparedStatement stmt =  DatabaseHandler.getInstance().con.prepareStatement(query);
            stmt.setString(1, book.getIsbn());
            stmt.setInt(2, user.getId());
            stmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            stmt.executeUpdate();

            return (true);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return (false);
        }
    }
    //Method untuk mengantri jika diluar method ini dilakukan pengecekan ableToBorrow dan hasilnya false, maka tampilkan tombol mengantri dan isinya memanggil method ini
    public boolean addBookQueue (Book book, User user) {
        if (ableToBorrow(book, user)) {return false;}
        if (!ableToQueue(book, user)) {return false;}
        DatabaseHandler.getInstance().connect();
        String query = "INSERT INTO bookqueue (isbn, id_user, date) VALUES(?,?,?)";
        try {
            PreparedStatement stmt =  DatabaseHandler.getInstance().con.prepareStatement(query);
            stmt.setString(1, book.getIsbn());
            stmt.setInt(2, user.getId());
            stmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            stmt.executeUpdate();
            return (true);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return (false);
        }
    }
    //Method ableToBorrow mengecek apakah bisa meminjam buku tersebut
    public boolean ableToBorrow(Book book, User user) {
        updateListBorrow();
        DatabaseHandler.getInstance().connect();
        String querySelect = "SELECT COUNT(id_list_borrow) AS result FROM listborrow WHERE isbn='"+ book.getIsbn() +"' AND date_return IS NULL";
        
        try {
            Statement stmt = DatabaseHandler.getInstance().con.createStatement();
            ResultSet rsSelect = stmt.executeQuery(querySelect);
            
            while(rsSelect.next()) {
                if(rsSelect.getInt("result")< book.getStock()) {
                    String querySelect2 = "SELECT id_user FROM listborrow WHERE isbn='"+ book.getIsbn() +"' AND date_return IS NULL AND id_user = " + user.getId() + ";";
                    Statement stmt2 = DatabaseHandler.getInstance().con.createStatement();
                    ResultSet rsSelect2 = stmt2.executeQuery(querySelect2);
                    return !rsSelect2.next();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean ableToQueue (Book book, User user) {
        updateListBorrow();
        DatabaseHandler.getInstance().connect();
        String querySelect = "SELECT id_list_borrow FROM listborrow WHERE isbn='"+ book.getIsbn() +"' AND date_return IS NULL AND id_user = " + user.getId() + ";";
        
        try {
            Statement stmt = DatabaseHandler.getInstance().con.createStatement();
            ResultSet rsSelect = stmt.executeQuery(querySelect);
            if(!rsSelect.next()) {
                String querySelect2 = "SELECT id_user FROM bookqueue WHERE isbn='" + book.getIsbn() + "' AND id_user = " + user.getId() + ";";
                Statement stmt2 = DatabaseHandler.getInstance().con.createStatement();
                ResultSet rsSelect2 = stmt2.executeQuery(querySelect2);
                if(!rsSelect2.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    //Method getBookQueue mendapatkan queue buku
    public ArrayList<BookQueue> getBookQueue (Book book) {
        updateListBorrow();
        DatabaseHandler.getInstance().connect();
        ArrayList<BookQueue> result = new ArrayList<>();
        
        String query = "SELECT * FROM bookqueue WHERE isbn='" + book.getIsbn() + "'";
        try {
            Statement stmt = DatabaseHandler.getInstance().con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                result.add(new BookQueue(rs.getInt("id_user"), rs.getTimestamp("date").toLocalDateTime()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    //Method getBookReview mendapatkan ulasan buku
    public ArrayList<Review> getBookReview (Book book) {
        DatabaseHandler.getInstance().connect();
        ArrayList<Review> result = new ArrayList<>();
        
        String query = "SELECT * FROM review WHERE isbn='" + book.getIsbn() + "'";
        try {
            Statement stmt = DatabaseHandler.getInstance().con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                result.add(new Review(rs.getInt("id_user"), rs.getInt("rating"), rs.getDate("date").toLocalDate(), rs.getString("content")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    //Method addBookReview untuk menambah review buku
    public boolean addBookReview (Review review, String isbn, User user) {
        if(checkUnableToReview(user, isbn)){return false;}
        DatabaseHandler.getInstance().connect();
        
        String query = "INSERT INTO review (id_user, isbn, content, rating, date) VALUES(?,?,?,?,?)";
        try {
            PreparedStatement stmt =  DatabaseHandler.getInstance().con.prepareStatement(query);
            stmt.setInt(1, user.getId());
            stmt.setString(2, isbn);
            stmt.setString(3, review.getContent());
            stmt.setInt(4, review.getRating());
            stmt.setDate(5, java.sql.Date.valueOf(review.getDate()));
            stmt.executeUpdate();

            return (true);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return (false);
        }
        
    }
    //Method checkUnableToReview untuk melakukan pengecekan apakah user bisa memberi review, pengecekan pernah pinjam dan pernah review atau tidak
    public boolean checkUnableToReview(User user, String isbn) {
        updateListBorrow();
        DatabaseHandler.getInstance().connect();
        String querySelect = "SELECT id_user FROM listborrow WHERE isbn='"+ isbn +"' AND id_user="+ user.getId() +" AND date_return IS NOT NULL;";
        
        try {
            Statement stmt = DatabaseHandler.getInstance().con.createStatement();
            ResultSet rsSelect = stmt.executeQuery(querySelect);
            
            if(rsSelect.next()) {
                String querySelect2 = "SELECT id_user FROM review WHERE isbn='"+ isbn +"' AND id_user = " + user.getId() + ";";
                Statement stmt2 = DatabaseHandler.getInstance().con.createStatement();
                ResultSet rsSelect2 = stmt2.executeQuery(querySelect2);
                return rsSelect2.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
    //Method pembantu getListBorrow dan ableToBorrow, untuk update listborrow database jika ada listborrow yang lebih dari 5 hari, dilakukan pengecekan queue buku tersebut
    private void updateListBorrow () {
        DatabaseHandler.getInstance().connect();
        String querySelect = "SELECT id_list_borrow, isbn FROM listborrow WHERE TIMESTAMPDIFF(SECOND, date_borrow, NOW())>=432000 AND date_return IS NULL;";
        
        try {
            Statement stmt = DatabaseHandler.getInstance().con.createStatement();
            ResultSet rsSelect = stmt.executeQuery(querySelect);
            while(rsSelect.next()) {
                String queryUpdate = "UPDATE listborrow SET date_return = TIMESTAMPADD(DAY, 5, date_borrow) WHERE id_list_borrow = " + rsSelect.getInt("id_list_borrow") + ";";
                Statement stmt2 = DatabaseHandler.getInstance().con.createStatement();
                stmt2.execute(queryUpdate);
                
                String queryUpdate2 = "CALL updateListBorrow('" + rsSelect.getString("isbn") + "');";
                Statement stmt3 = DatabaseHandler.getInstance().con.createStatement();
                stmt3.execute(queryUpdate2);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    //Method returnBook yang merupakan aksi dari user(bukan karena lewat 5 hari), setelah return, dilakukan juga pengecekan queue
    public boolean returnBook (BorrowedBook borrowedBook) {
        DatabaseHandler.getInstance().connect();
        String querySelect = "SELECT id_list_borrow, isbn FROM listborrow WHERE id_list_borrow = " + borrowedBook.getIdListBorrow() + ";";
        
        try {
            Statement stmt = DatabaseHandler.getInstance().con.createStatement();
            ResultSet rsSelect = stmt.executeQuery(querySelect);
            while(rsSelect.next()) {
                String queryUpdate = "UPDATE listborrow SET date_return = NOW() WHERE id_list_borrow = " + rsSelect.getInt("id_list_borrow") + ";";
                Statement stmt2 = DatabaseHandler.getInstance().con.createStatement();
                Statement stmt3 = DatabaseHandler.getInstance().con.createStatement();
                String queryUpdate2 = "CALL updateListBorrow('" + rsSelect.getString("isbn") + "');";
                stmt2.execute(queryUpdate);
                stmt3.execute(queryUpdate2);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
     
}