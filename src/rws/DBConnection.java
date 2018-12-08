package rws;

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
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {

    static String DB = "LMS";
    static String UN = "root";
    static String PW = "";

    private static Connection con = null;

    public static Connection connect() {
        if (con == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/" + DB, UN, PW);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return con;
    }
    
    public static void closeConnection(){
        try {
            Connection con = connect();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
