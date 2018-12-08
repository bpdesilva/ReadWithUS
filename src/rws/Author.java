
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
import java.util.ArrayList;
/**
 *
 * @author bpdesilva
 */
public class Author {

    private int authId;
    private String authName;
    private String authInitals;
    ArrayList aul = new ArrayList();


    public int getAuthId() {
        return authId;
    }

    public void setAuthId(int authId) {
        this.authId = authId;
    }

    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName;
    }

    public String getAuthInitals() {
        return authInitals;
    }

    public void setAuthInitals(String authInitals) {
        this.authInitals = authInitals;
    }
//insert author
    public void addAuth(Author auth) {
        try {
            Statement s = DBConnection.connect().createStatement();
            s.executeUpdate("INSERT INTO `lms`.`author` (`AuthorName`, `AuthorInitals`) VALUES ('" + getAuthName() + "', '" + getAuthInitals() + "');");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
//get list of authors
    public ArrayList getAuth() {
        try {
            Statement s = DBConnection.connect().createStatement();
            ResultSet rs = s.executeQuery("SELECT idAuthor,AuthorName FROM `lms`.`author`");
           
            while (rs.next()) {
                String[] auth = {String.format("%d", rs.getInt(1)), rs.getString(2)};
                aul.add(auth);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return aul;

    }
     
}
