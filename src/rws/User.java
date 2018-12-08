
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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author bpdesilva
 */
public class User {

    private int id;
    private String uname;
    private String pass;
    private String type;
    private int UId;
    private String status;
    ArrayList lg = new ArrayList();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the uname
     */
    public String getUname() {
        return uname;
    }

    /**
     * @param uname the uname to set
     */
    public void setUname(String uname) {
        this.uname = uname;
    }

    /**
     * @return the pass
     */
    public String getPass() {
        return pass;
    }

    /**
     * @param pass the pass to set
     */
    public void setPass(String pass) {
        this.pass = pass;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the UId
     */
    public int getUId() {
        return UId;
    }

    /**
     * @param UId the UId to set
     */
    public void setUId(int UId) {
        this.UId = UId;
    }

    //validate user
    public String getUser(boolean isMember) {
        String usersName = null;
        try {
            Statement s = DBConnection.connect().createStatement();
            ResultSet rs;
            if (isMember) {
                rs = s.executeQuery("SELECT * FROM `lms`.`member` WHERE `idMember`='" + UId + "';");
            } else {
                rs = s.executeQuery("SELECT * FROM `lms`.`librarian` WHERE `idLibrarian`='" + UId + "';");
            }

            if (rs.next()) {
                usersName = rs.getString(2) + " " + rs.getString(3);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usersName;
    }
//insert login to database
    public void addUser() {
        try {
            Statement s = DBConnection.connect().createStatement();
            s.executeUpdate("INSERT INTO `lms`.`users` (`Username`, `Password`, `UserType`, `UId`) VALUES ('" + getUname() + "', '" + getPass() + "', '" + getType() + "', '" + getUId() + "');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//Delete login details
    public void deleteUser(int idUser) {
        try {
            Statement s = DBConnection.connect().createStatement();
            s.executeUpdate("DELETE FROM users WHERE idUser = '" + idUser + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//get login credentials 
    public boolean login() {
        try {
            Statement s = DBConnection.connect().createStatement();
            ResultSet rs = s.executeQuery("SELECT UserType,UId FROM `users` WHERE `Username` = '" + uname + "'AND Password='" + pass + "'");
            if (rs.next()) {
                String uType = rs.getString(1);
                int uId = rs.getInt(2);
                this.setType(uType);
                this.setUId(uId);
                JOptionPane.showMessageDialog(null, "Welcome!");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Incorrect Username or Password!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
//get login list
    public ArrayList getlogins() {
        try {
            Statement s = DBConnection.connect().createStatement();
            ResultSet rs = s.executeQuery("SELECT UserType,UId,idUsers FROM `lms`.`users`");
            ArrayList userData = new ArrayList();
            while (rs.next()) {
                String[] login = {rs.getString(1), rs.getString(2), rs.getString(3)};
                userData.add(login);
            }
            for (int i = 0; i < userData.size(); i++) {
                String[] login = (String[]) userData.get(i);
                String userType = login[0];
                String userId = login[1];
                String query = null;
                if ("Librarian".equals(userType)) {
                    query = "SELECT `FirstName`, `LastName` FROM `librarian` WHERE idLibrarian='" + userId + "'";
                } else if ("Member".equals(userType)) {
                    query = "SELECT `FirstName`, `LastName` FROM `member` WHERE idMember='" + userId + "'";
                }
                ResultSet rs2 = s.executeQuery(query);
                rs2.next();

                String[] user = new String[4];
                user[0] = userType;
                user[1] = userId;
                user[2] = rs2.getString(1) + " " + rs2.getString(2);
                user[3] = login[2];
                lg.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lg;
    }

}
