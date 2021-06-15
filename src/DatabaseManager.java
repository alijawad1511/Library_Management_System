import javax.swing.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public DatabaseManager(Connection connection) {
        this.connection = connection;
    }

    public void createTables() {
        try {
            Statement statement = connection.createStatement();
            //Users Table
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS Users (user_id INT PRIMARY KEY AUTO_INCREMENT,
                                        first_name VARCHAR(20) NOT NULL
                                       ,last_name VARCHAR(20) NOT NULL
                                       ,password VARCHAR(30) NOT NULL
                                       ,dateTime TIMESTAMP default current_timestamp
                                       ,cnic VARCHAR(14)\t  UNIQUE NOT NULL
                                       ,address VARCHAR(255)  NOT NULL
                                       ,username VARCHAR(30) UNIQUE NOT NULL
                                       ,phoneNumber VARCHAR(12) NOT NULL);""");
            //Books Table
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS Books (book_id INT PRIMARY KEY AUTO_INCREMENT,
                                        book_title VARCHAR(255) NOT NULL
                                       ,book_author VARCHAR(255) NOT NULL
                                       ,book_genre VARCHAR(20) NOT NULL
                                       ,book_copies_sold VARCHAR(20)
                                       ,book_rating FLOAT
                                       ,book_release_year INT);""");
            //Levels Table
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS Levels (level_id INT PRIMARY KEY AUTO_INCREMENT,
                                        user_id INT NOT NULL
                                       ,level_experience TINYINT NOT NULL
                                       ,level_status TINYINT AS (level_experience / 2)
                                       ,book_issue_limit SMALLINT AS (level_experience * 2)
                                       ,FOREIGN KEY (user_id) REFERENCES Users(user_id));""");
            //IssuedBook Table
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS IssuedBook (borrow_id INT PRIMARY KEY AUTO_INCREMENT,
                                                              user_id INT
                                                              ,book_id INT
                                                              ,issue_time DATETIME UNIQUE DEFAULT NOW()
                                                              ,due_time DATETIME generated always as (issue_time + interval 7 day) stored
                                                              ,FOREIGN KEY(user_id) REFERENCES Users(user_id)
                                                              ,FOREIGN KEY(book_id) REFERENCES Books(book_id));""");
            //ReturnedBook Table
            statement.execute("""
                            CREATE TABLE IF NOT EXISTS ReturnedBook (return_id INT PRIMARY KEY AUTO_INCREMENT,
                                                                 borrow_id INT
                                                                ,returned_time timestamp default current_timestamp
                                                                ,FOREIGN KEY(borrow_id) REFERENCES IssuedBook(borrow_id));""");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getIssuedBooks(int user_id) {
        String query = """
                SELECT COUNT(*)
                FROM IssuedBook
                WHERE IssuedBook.user_id = ? AND IssuedBook.borrow_id NOT IN (
                        SELECT ReturnedBook.borrow_id
                		FROM ReturnedBook);""";
        try {
            PreparedStatement ppStatement = connection.prepareStatement(query);
            ppStatement.setInt(1, user_id);
            ResultSet rs = ppStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                return -1;
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void increaseLevelExperience(int user_id) throws SQLException {
        String query = "UPDATE Levels " +
                "SET level_experience = level_experience + 2 " +
                "WHERE user_id = ?";
        PreparedStatement ppStatement = connection.prepareStatement(query);
        ppStatement.setInt(1, user_id);
        ResultSet rs = ppStatement.executeQuery();
    }

    public void addIssueBookReceipt(int user_id,int book_id){
        String query = "INSERT INTO IssuedBook (user_id,book_id) VALUES(?,?)";
        try {
            PreparedStatement ppStatement = connection.prepareStatement(query);
            ppStatement.setInt(1, user_id);
            ppStatement.setInt(2, book_id);
            ppStatement.execute();
            this.increaseLevelExperience(user_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void addReturnBookReceipt(int borrow_id){
        String query = "INSERT INTO ReturnedBook (borrow_id) VALUES(?)";
        try {
            PreparedStatement ppStatement = connection.prepareStatement(query);
            ppStatement.setInt(1, borrow_id);
            ppStatement.execute();
            //TODO add consequence for returning late
            this.increaseLevelExperience(Driver.currentUser.getUser_id());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<IssueBook> getIssuedBooks() throws SQLException {
        String query = """
                SELECT Books.book_title,IssuedBook.issue_time,IssuedBook.due_time\s
                FROM IssuedBook
                INNER JOIN Books\s
                ON IssuedBook.book_id = Books.book_id;""";
        PreparedStatement ppStatement = connection.prepareStatement(query);
        ResultSet rs = ppStatement.executeQuery();
        ArrayList<IssueBook> issued_books = new ArrayList<>();
        IssueBook issued_book = null;
        if(rs.next()) {
            do{
                issued_book = new IssueBook();
                issued_book.setBook_title(rs.getString(1));
                issued_book.setIssue_date(rs.getString(2));
                issued_book.setDue_date(rs.getString(3));
                issued_books.add(issued_book);
            }while (rs.next());
            return issued_books;
        }
        return null;
    }

    public void resetPassword(String password) throws SQLException {
        String query = "UPDATE Users SET password = ? WHERE user_id = ?";
        PreparedStatement  ppStatement = connection.prepareStatement(query);
        ppStatement.setString(1,password);
        ppStatement.setInt(2,Driver.currentUser.getUser_id());
        ppStatement.executeUpdate();
    }

    public void decreaseBookCopy(int book_id) throws SQLException {
        String query = "UPDATE Books" +
                "SET book_copies_sold = book_copies_sold - 1 " +
                "WHERE book_id = ?";
        PreparedStatement ppStatement = connection.prepareStatement(query);
        ppStatement.setInt(1, book_id);
        ResultSet rs = ppStatement.executeQuery();
    }

    public void increaseBookCopy(int book_id) throws SQLException {
        String query = "UPDATE Books" +
                "SET book_copies_sold = book_copies_sold + 1 " +
                "WHERE book_id = ?";
        PreparedStatement ppStatement = connection.prepareStatement(query);
        ppStatement.setInt(1, book_id);
        ResultSet rs = ppStatement.executeQuery();
    }

    /*public boolean isBookIssued(int book_id){
        //TODO add query
    }*/

    public ArrayList<ReturnBook> getReturnedBooks() throws SQLException {
        String query = """
                SELECT Books.book_title,IssuedBook.issue_time,IssuedBook.due_time,ReturnedBook.returned_time
                                                      FROM ReturnedBook
                                                      INNER JOIN IssuedBook
                                                      ON IssuedBook.borrow_id = ReturnedBook.borrow_id
                                                      INNER JOIN Books
                                                      ON Books.book_id = IssuedBook.book_id;""";
        PreparedStatement ppStatement = connection.prepareStatement(query);
        ResultSet rs = ppStatement.executeQuery();
        ArrayList<ReturnBook> returned_books = new ArrayList<>();
        ReturnBook returned_book = null;
        if(rs.next()) {
            do{
                returned_book = new ReturnBook();
                returned_book.setBook_title(rs.getString(1));
                returned_book.setIssue_date(rs.getString(2));
                returned_book.setDue_date(rs.getString(3));
                returned_books.add(returned_book);
            }while (rs.next());
            return returned_books;
        }
        return null;
    }


    //Reference SearchBook
    public ArrayList<Book> searchBooks(String keyword){
        String query = "SELECT * FROM Books WHERE book_title LIKE '%"+keyword+"%'";
        try{
            PreparedStatement ppStatement = connection.prepareStatement(query);
            ResultSet rs = ppStatement.executeQuery();
            ArrayList<Book> books = null;
            Book book = null;
            if(rs.next()){
                books = new ArrayList<>();
                do{
                    book = new Book();
                    book.setBook_id(rs.getInt(1));
                    book.setAuthor(rs.getString(3));
                    book.setTitle(rs.getString(2));
                    book.setGenre(rs.getString(4));
                    book.setNoOfCopies(rs.getInt(5));
                    book.setRating(rs.getFloat(6));
                    book.setDateOfRelease(rs.getString(7));
                    books.add(book);
                }while(rs.next());
            }
            return books;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    //Reference Driver
    public boolean isBookTableEmpty(){
        String query = "SELECT COUNT(*) FROM Books";
        try{
            PreparedStatement ppStatement = connection.prepareStatement(query);
            ResultSet rs = ppStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0;
            } else {
                return false;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    //INSERT all books into mySQL Books Table
    //Reference Driver
    public void uploadBooksToDatabase(){
        FileManager fileManager = new FileManager();
        ArrayList<Book> books = null;
        try {
            books = fileManager.readBooksData();
        }catch(IOException e){
            e.printStackTrace();
        }
        if(books != null) {
            String query = "INSERT INTO Books (book_title,book_author,book_genre," +
                    "book_copies_sold,book_rating,book_release_year) VALUES(?,?,?,?,?,?)";
            try {
                for (Book book : books) {
                    PreparedStatement ppStatement = connection.prepareStatement(query);
                    ppStatement.setString(1, book.getTitle());
                    ppStatement.setString(2, book.getAuthor());
                    ppStatement.setString(3, book.getGenre());
                    ppStatement.setString(4, Integer.toString(book.getNoOfCopies()));
                    ppStatement.setString(5, Float.toString(book.getRating()));
                    ppStatement.setString(6, book.getDateOfRelease());
                    ppStatement.execute();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "There was an issue with the File Manager!",
                    "File Upload Error to DB", JOptionPane.WARNING_MESSAGE);
        }
    }

    //Reference
    public int getUserExperience(int user_id){
        String query = """
                SELECT Levels.level_experience
                FROM Levels\s
                WHERE Levels.user_id = ?""";
        try {
            PreparedStatement ppStatement = connection.prepareStatement(query);
            ppStatement.setInt(1,user_id);
            ResultSet rs = ppStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }


    // Reference SignUp
    public void addUser(User user) {
        //User is added
        String query = "INSERT INTO Users (username,password,first_name,last_name,address,phoneNumber,cnic) VALUES(?,?,?,?,?,?,?)";
        try {
            PreparedStatement ppStatement = connection.prepareStatement(query);
            ppStatement.setString(1, user.getEmail());
            ppStatement.setString(2, user.getPassword());
            ppStatement.setString(3, user.getFirstName());
            ppStatement.setString(4, user.getLastName());
            ppStatement.setString(5, user.getAddress());
            ppStatement.setString(6, user.getMobileNumber());
            ppStatement.setString(7, user.getCnic());
            ppStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Level is added
        query = "INSERT INTO Levels (user_id,level_experience) VALUES(?,?)";
        try {
            PreparedStatement ppStatement = connection.prepareStatement(query);
            ppStatement.setInt(1, getId(user.getEmail(),user.getPassword()));
            ppStatement.setInt(3, 2);
            ppStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Reference SignUp
    public boolean checkUserRepetition(String username) {
        String query = "SELECT * FROM Users where username = ?";
        try {
            PreparedStatement ppStatement = connection.prepareStatement(query);
            ppStatement.setString(1, username);
            ResultSet rs = ppStatement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //Reference SignUp
    public boolean checkCnicRepetition(String cnic) {
        String query = "SELECT * FROM Users where cnic = ?";
        try {
            PreparedStatement ppStatement = connection.prepareStatement(query);
            ppStatement.setString(1, cnic);
            ResultSet rs = ppStatement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //Reference Login
    public int getId(String username, String password) {
        String query = "SELECT * FROM Users where username = ? and password = ?";
        try {
            PreparedStatement ppStatement = connection.prepareStatement(query);
            ppStatement.setString(1, username);
            ppStatement.setString(2, password);

            ResultSet rs = ppStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    //Reference Driver && LoginMenu
    public User loadUserInfoFromDataBase(int user_id) throws SQLException {
        //-----------------------------------------------user-MAIL is loaded--------------------------------------------------
        PreparedStatement ppStatement = connection.prepareStatement("SELECT * FROM Users where user_id=?");
        ppStatement.setInt(1, user_id);
        ResultSet rs = ppStatement.executeQuery();

        User user = new User();
        if (rs.next()) {
            user.setUser_id(user_id);
            user.setEmail(rs.getString(8));
            user.setPassword(rs.getString(4));
            user.setFirstName(rs.getString(2));
            user.setLastName(rs.getString(3));
            user.setAddress(rs.getString(7));
            user.setCnic(rs.getString(6));
            user.setMobileNumber(rs.getString(9));
            return user;
        } else {
            return null;
        }
    }

}
