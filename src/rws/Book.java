
/*Copyright  © 2017 BUWANEKA DE SILVA

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files
(the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge,
publish, distribute, sublicense, and / or sell copies of the Software, and to permit persons to whom the Software is furnished to
do so, subject to the following conditions :The above copyright notice and this permission notice shall be included in all copies
or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR
IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.*/
package rws;

import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author bpdesilva
 */
public class Book {

    private int RefId;
    private String BookName;
    private String Genre;
    private String AuthName;
    private int AuthId;

    public String getAuthName() {
        return AuthName;
    }

    public void setAuthName(String AuthName) {
        this.AuthName = AuthName;
    }

    public int getRefId() {
        return RefId;
    }

    public void setRefId(int RefId) {
        this.RefId = RefId;
    }

    public String getBookName() {
        return BookName;
    }

    public void setBookName(String BookName) {
        this.BookName = BookName;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String Genre) {
        this.Genre = Genre;
    }

    public int getAuthId() {
        return AuthId;
    }

    public void setAuthId(int AuthId) {
        this.AuthId = AuthId;
    }
//insert book into database
    public void Addbook(Book nwbk) {
        try {
            Statement stmt = DBConnection.connect().createStatement();
            stmt.executeUpdate("INSERT INTO book(ReferenceId,BookName,Genre,Availability,Status,AId) values(" + nwbk.getRefId() + ",'" + nwbk.getBookName() + "','" + nwbk.getGenre() + "','Available','Existing','" + nwbk.getAuthId() + "')");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage() + "\nInsert data unsuccessful!");
            ex.printStackTrace();
        }
    }
//search for books by ID
    public void SearchBookById(String bookReference) {
        try {
            Statement s = DBConnection.connect().createStatement();
            ResultSet rs = s.executeQuery("SELECT b.ReferenceId,b.BookName,b.Genre,a.idAuthor,a.AuthorName FROM BOOK b JOIN Author a ON a.idAuthor=b.AId WHERE b.ReferenceId='" + bookReference + "'");

            if (rs.next()) {
                this.RefId = rs.getInt(1);
                this.BookName = rs.getString(2);
                this.Genre = rs.getString(3);
                this.AuthId = rs.getInt(4);
                this.AuthName = rs.getString(5);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage() + "\nUpdate data unsuccessful!");
        }
    }
//search for books by name
    public void SearchBookByName(String bookName) {

        DBConnection db;
        Connection dbcon;

        try {
            db = new DBConnection();
            dbcon = db.connect();

            Statement s = DBConnection.connect().createStatement();
            ResultSet rs = s.executeQuery("SELECT b.ReferenceId,b.BookName,b.Genre,a.idAuthor,a.AuthorName FROM BOOK b JOIN Author a ON a.idAuthor=b.AId WHERE b.BookName='" + bookName + "'");

            if (rs.next()) {
                this.RefId = rs.getInt(1);
                this.BookName = rs.getString(2);
                this.Genre = rs.getString(3);
                this.AuthId = rs.getInt(4);
                this.AuthName = rs.getString(5);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage() + "\nUpdate data unsuccessful!");
        }
    }
//update books using refernce id
    public void UpdateBookbyRefID(Book nwbk) {
        try {
            Statement stmt = DBConnection.connect().createStatement();
            stmt.executeUpdate("UPDATE BOOK SET BookName='" + nwbk.getBookName() + "',Genre='" + nwbk.getGenre() + "',AId='" + nwbk.getAuthId() + "' WHERE ReferenceId='" + nwbk.getRefId() + "'");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage() + "\nUpdate data unsuccessful!");
        }
    }
//update book using book name
    public void UpdateBookbyBookName(Book nwbk) {
        try {
            Statement stmt = DBConnection.connect().createStatement();
            stmt.executeUpdate("UPDATE book SET ReferenceID='" + nwbk.getRefId() + "',Genre='" + nwbk.getGenre() + "',AId='" + nwbk.getAuthId() + "' WHERE BookName='" + nwbk.getBookName() + "'");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage() + "\nUpdate data unsuccessful!");
        }
    }
//
    public void DeleteBookByRefID(Book nwbk) {
        try {
            Statement stmt = DBConnection.connect().createStatement();
            stmt.executeUpdate("Delete from book WHERE ReferenceId='" + nwbk.getRefId() + "'");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage() + "\nDelete data unsuccessful!");
        }
    }
//get book availability
    public String getBookAvailability(int referenceId){
        String availability = "Borrowed";
        try {
            Statement s = DBConnection.connect().createStatement();
            ResultSet rs = s.executeQuery("SELECT Availability FROM `lms`.`book` WHERE `ReferenceId`='" + referenceId + "'");

            while (rs.next()) {
                availability = rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return availability;
    }
//get book status    
    public String getBookStatus(int referenceId){
        String status = "Reserved";
        try {
            Statement s = DBConnection.connect().createStatement();
            ResultSet rs = s.executeQuery("SELECT Status FROM `lms`.`book` WHERE `ReferenceId`='" + referenceId + "'");

            while (rs.next()) {
                status = rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }
}
