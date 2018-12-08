
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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author bpdesilva
 */
public class Borrow {

    private int id;
    private int BId;
    private int MId;
    private String BorrowedDate;
    private String ReturnDate;
    private String Fine;
    private String status;
    private ArrayList br = new ArrayList();
    private ArrayList brHis = new ArrayList();

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
     * @return the BId
     */
    public int getBId() {
        return BId;
    }

    /**
     * @param BId the BId to set
     */
    public void setBId(int BId) {
        this.BId = BId;
    }

    /**
     * @return the MId
     */
    public int getMId() {
        return MId;
    }

    /**
     * @param MId the MId to set
     */
    public void setMId(int MId) {
        this.MId = MId;
    }

    /**
     * @return the BorrowedDate
     */
    public String getBorrowedDate() {
        return BorrowedDate;
    }

    /**
     * @param BorrowedDate the BorrowedDate to set
     */
    public void setBorrowedDate(String BorrowedDate) {
        this.BorrowedDate = BorrowedDate;
    }

    /**
     * @return the ReturnDate
     */
    public String getReturnDate() {
        return ReturnDate;
    }

    /**
     * @param ReturnDate the ReturnDate to set
     */
    public void setReturnDate(String ReturnDate) {
        this.ReturnDate = ReturnDate;
    }

    /**
     * @return the Fine
     */
    public String getFine() {
        return Fine;
    }

    /**
     * @param Fine the Fine to set
     */
    public void setFine(String Fine) {
        this.Fine = Fine;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
//Get all the borrowed details
    public ArrayList getAllBorrows() {
        if (!br.isEmpty()) {
            br.clear();
        }
        try {
            Statement s = DBConnection.connect().createStatement();
            ResultSet rs;
            if (MId == 0) {
                rs = s.executeQuery("SELECT * FROM `lms`.`borrow`");
            } else {
                rs = s.executeQuery("SELECT * FROM `lms`.`borrow` WHERE `MId`=" + MId);
            }

            while (rs.next()) {
                int idBorrow = rs.getInt(1);
                int BId = rs.getInt(2);
                int MId = rs.getInt(3);
                String ReturnDate = rs.getString(5);
                double Fine = rs.getDouble(6);
                String Fine_Calculated_for = rs.getString(7);
                String borrowedDate = rs.getString(4);

                String[] borrowalData = new String[7];
                borrowalData[0] = "" + idBorrow;
                borrowalData[1] = "" + BId;
                borrowalData[2] = "" + MId;
                borrowalData[3] = ReturnDate;
                borrowalData[4] = borrowedDate;
                borrowalData[5] = "" + Fine;
                borrowalData[6] = Fine_Calculated_for;
                br.add(borrowalData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return br;
    }
// add new borrowal to database
    public int addBorrow() {
        Member m = new Member();
        Book b = new Book();

        int userNoOfBorrows = 0;
        int userNoOfReservations = 0;
        String bookAvailability;
        String bookStatus;

        if (MId != 0) {
            userNoOfBorrows = m.getNumberOfBorrows(MId);
            userNoOfReservations = m.getNumberOfReservations(MId);
            bookAvailability = b.getBookAvailability(BId);
            bookStatus = b.getBookStatus(BId);

            if (userNoOfBorrows < 2) {
                if (bookAvailability.equalsIgnoreCase("Available")) {
                    if (bookStatus.equalsIgnoreCase("Existing")
                            || bookStatus.equalsIgnoreCase("Reserved for " + MId)) {

                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                        String BorrowedDate = dateFormat.format(new Date());
                        String ReturnDate = dateFormat.format(new Date(new Date().getYear(), new Date().getMonth(), new Date().getDate() + 14));
                        userNoOfBorrows++;
                        userNoOfReservations--;
                        try {
                            Statement s = DBConnection.connect().createStatement();
                            s.executeUpdate("INSERT INTO `lms`.`borrow` ( `BId`, `MId`, `BorrowedDate`, `ReturnDate`, `Fine`, `Fine_Calculated_for`) VALUES ('" + BId + "', '" + MId + "', '" + BorrowedDate + "','" + ReturnDate + "','0.00', '" + BorrowedDate + "');");
                            s.executeUpdate("UPDATE `lms`.`member` SET `No_of_Borrows`='" + userNoOfBorrows + "' WHERE `idMember`=" + MId);
                            s.executeUpdate("UPDATE `lms`.`book` SET `Availability`='Borrowed', `Status`='Existing' WHERE `ReferenceId`=" + BId);
                            if (bookStatus.equalsIgnoreCase("Reserved for " + MId)) {
                                Reserve r = new Reserve();
                                r.setMId(MId);
                                r.setBId(BId);
                                r.getReserve();
                                r.deleteReserve();
                            }
                            return 4;
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } else {
                        return 3;
                    }
                } else {
                    return 2;
                }
            } else {
                return 1;
            }
        }
        return 0;
    }

//Delete borrow status    
    public void deleteBorrow(int idBorrow, int bRef) {
        Member m = new Member();
        int userNoOfBorrows = 0;

        userNoOfBorrows = m.getNumberOfBorrows(MId);
        if (userNoOfBorrows > 0) {
            userNoOfBorrows--;
        } else {
            userNoOfBorrows = 0;
        }

        try {
            Statement s = DBConnection.connect().createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM `borrow` WHERE `idBorrow` = '" + idBorrow + "';");
            String memberId = null;
            String bookRefId = null;
            String borrowedDate = null;
            String returnedDate = null;
            while (rs.next()) {
                memberId = rs.getString(3);
                bookRefId = rs.getString(2);
                borrowedDate = rs.getString(4);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                returnedDate = dateFormat.format(new Date());
            }
            s.executeUpdate("INSERT INTO `lms`.`history`(`MId`,`BookRefId`,`BorrowedDate`,`ReturnedDate`) VALUES ('" + memberId + "','" + bookRefId + "','" + borrowedDate + "','" + returnedDate + "')");
            s.executeUpdate("DELETE FROM `lms`.`borrow` WHERE `idBorrow` = '" + idBorrow + "';");
            s.executeUpdate("UPDATE `lms`.`book` SET `Availability`='Available' WHERE `ReferenceId`=" + bRef);
            s.executeUpdate("UPDATE `lms`.`member` SET `No_of_Borrows`='" + userNoOfBorrows + "' WHERE `idMember`=" + MId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

 //Calculate fines for overdues
    public double calculateFine() {
        double fine = 0.0;
        double fineRate = 5.0;

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date rdate = dateFormat.parse(getReturnDate());
            Date today = new Date();
            String todayString = dateFormat.format(today);
            long rdiff = today.getTime() - rdate.getTime();
            long diffDays = rdiff / (24 * 60 * 60 * 1000);

            if (rdiff > 0) {
                fine = diffDays * fineRate;
            } else {
                fine = 0.0;
            }

            Statement s = DBConnection.connect().createStatement();
            s.executeUpdate("UPDATE borrow SET Fine='" + fine
                    + "',Fine_Calculated_for='" + todayString + "' WHERE `idBorrow`='" + getId() + "'");
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }

        return fine;
    }
//set up fines for user
    public void setFines() {
        getAllBorrows();
        for (int i = 0; i < br.size(); i++) {
            String[] borrowData = (String[]) br.get(i);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String todayString = dateFormat.format(new Date());
            if (!todayString.equalsIgnoreCase(borrowData[5])) {
                setId(Integer.parseInt(borrowData[0]));
                setReturnDate(borrowData[3]);
                calculateFine();
            }
        }
    }

    //get borrow history
    public ArrayList getAllBorrowsHistory() {
        if (!brHis.isEmpty()) {
            brHis.clear();
        }
        ResultSet rs;
        try {
            Statement s = DBConnection.connect().createStatement();
            if (MId == 0) {
                rs = s.executeQuery("SELECT * FROM `lms`.`history`");
            } else {
                rs = s.executeQuery("SELECT * FROM `lms`.`history` WHERE `MId`=" + MId);
            }

            while (rs.next()) {
                int idBorrow = rs.getInt(1);
                int BId = rs.getInt(3);
                int MId = rs.getInt(2);
                String ReturnDate = rs.getString(5);
                String borrowedDate = rs.getString(4);

                String[] borrowalData = new String[5];
                borrowalData[0] = "" + idBorrow;
                borrowalData[1] = "" + BId;
                borrowalData[2] = "" + MId;
                borrowalData[3] = "" + ReturnDate;
                borrowalData[4] = borrowedDate;
                brHis.add(borrowalData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return brHis;
    }
}