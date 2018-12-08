
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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author bpdesilva
 */
public class Librarian {

    private int id;
    private String fname;
    private String lname;
    private String nic;
    private String email;
    private String phone;
    ArrayList lib = new ArrayList();

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
     * @return the fname
     */
    public String getFname() {
        return fname;
    }

    /**
     * @param fname the fname to set
     */
    public void setFname(String fname) {
        this.fname = fname;
    }

    /**
     * @return the lname
     */
    public String getLname() {
        return lname;
    }

    /**
     * @param lname the lname to set
     */
    public void setLname(String lname) {
        this.lname = lname;
    }

    /**
     * @return the nic
     */
    public String getNic() {
        return nic;
    }

    /**
     * @param nic the nic to set
     */
    public void setNic(String nic) {
        this.nic = nic;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
//insert a librarian to database
    public void addLibrarian() {
        try {
            String sql = "INSERT INTO `lms`.`librarian` ( `FirstName`, `LastName`, `NIC`,`Email`,`PhoneNo`) VALUES ('" + fname + "', '" + lname + "', '" + nic + "','" + email + "','" + phone + "');";
            PreparedStatement ps = DBConnection.connect().prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();

            int generatedKey = 0;
            if (rs.next()) {
                generatedKey = rs.getInt(1);
                this.setId(generatedKey);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//search libraian by id
    public boolean searchById(int idLibrarian) {
        try {
            Statement s = DBConnection.connect().createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM `lms`.`librarian` WHERE `idLibrarian` = '" + idLibrarian + "';");
            if (rs.next()) {
                this.id = rs.getInt(1);
                this.fname = rs.getString(2);
                this.lname = rs.getString(3);
                this.nic = rs.getString(4);
                this.email = rs.getString(5);
                this.phone = rs.getString(6);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //search librarian using email
    public boolean searchByEmail(String email){
        try {
            Statement s = DBConnection.connect().createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM `lms`.`librarian` WHERE `Email` = '" + email + "';");
            if (rs.next()) {
                this.id = rs.getInt(1);
                this.fname = rs.getString(2);
                this.lname = rs.getString(3);
                this.nic = rs.getString(4);
                this.email = rs.getString(5);
                this.phone = rs.getString(6);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    //search librarian using nic
    public boolean searchByNic(String nicNo){
        try {
            Statement s = DBConnection.connect().createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM `lms`.`librarian` WHERE `NIC` = '" + nicNo + "';");
            if (rs.next()) {
                this.id = rs.getInt(1);
                this.fname = rs.getString(2);
                this.lname = rs.getString(3);
                this.nic = rs.getString(4);
                this.email = rs.getString(5);
                this.phone = rs.getString(6);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    //search librarian using phone number
        public boolean searchByPhno(String phone) {
        try {
            Statement s = DBConnection.connect().createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM `lms`.`librarian` WHERE `PhoneNo` = '" + phone + "';");

            if (rs.next()) {
                this.id = rs.getInt(1);
                this.fname = rs.getString(2);
                this.lname = rs.getString(3);
                this.nic = rs.getString(4);
                this.email = rs.getString(5);
                this.phone = rs.getString(6);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    //update librarian  
    public boolean updateLibrarian() {
        try{
            Statement s = DBConnection.connect().createStatement();
            s.executeUpdate("UPDATE `lms`.`librarian` SET `FirstName` = '"+fname+"', `LastName` = '"+lname+"', `NIC` = '"+nic+"', `Email` = '"+email+"', `PhoneNo` = '"+phone+"' WHERE `idLibrarian` = '"+id+"'");
            return true;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //get a list of librarians
    public ArrayList getLibrarians() {
        try {
            Statement s = DBConnection.connect().createStatement();
            ResultSet rs = s.executeQuery("SELECT idLibrarian,FirstName,LastName FROM `lms`.`librarian`");

            while (rs.next()) {
                String[] librarian = new String[2];
                librarian[0] = rs.getString(1);
                librarian[1] = rs.getString(2) + " " + rs.getString(3);
                lib.add(librarian);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lib;
    }
}
