
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author bpdesilva
 */
public class Reserve {

    private int idReserve;
    private int BId;
    private int MId;
    private String SpecialRemark;
    private String ReservedDate;

    /**
     * @return the id
     */
    public int getId() {
        return idReserve;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.idReserve = id;
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
     * @return the ReservedDate
     */
    public String getReservedDate() {
        return ReservedDate;
    }

    /**
     * @param ReservedDate the ReservedDate to set
     */
    public void setReservedDate(String ReservedDate) {
        this.ReservedDate = ReservedDate;
    }
// get reserved books
    public void getReserve() {
        try {
            Statement s = DBConnection.connect().createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM `lms`.`reserve` WHERE `BId`=" + getBId() + " AND `MId`=" + getMId() + ";");

            if (rs.next()) {
                this.setId(Integer.parseInt(rs.getString(1)));
                this.setBId(Integer.parseInt(rs.getString(2)));
                this.setMId(Integer.parseInt(rs.getString(3)));
                this.setReservedDate(rs.getString(4));
                this.setSpecialRemark(rs.getString(5));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//  add reservation  
    public int addReserve() {
        Member m = new Member();
        Book b = new Book();

        int numberOfReservations;
        String bookStatus;

        if (MId != 0) {
            numberOfReservations = m.getNumberOfReservations(getMId());
            bookStatus = b.getBookStatus(BId);

            if (numberOfReservations < 2) {
                if (bookStatus.equalsIgnoreCase("Existing")) {
                    numberOfReservations++;
                    try {
                        Statement s = DBConnection.connect().createStatement();
                        s.executeUpdate("INSERT INTO `lms`.`reserve` (`BId`, `MId`, `ReservedDate`, `Special_Remark`) VALUES ('" + getBId() + "', '" + getMId() + "', '" + getReservedDate() + "', '" + getSpecialRemark() + "');");
                        s.executeUpdate("UPDATE `lms`.`book` SET `Status`='Reserved for " + getMId() + "' WHERE `ReferenceId`=" + getBId());
                        s.executeUpdate("UPDATE `lms`.`member` SET `No_of_Reservations`='" + numberOfReservations + "' WHERE `idMember`=" + getMId());
                        return 3;
                    } catch (SQLException e) {
                        e.printStackTrace();
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

//  delete reservation  
    public void deleteReserve() {
        Member m = new Member();
        int userNoOfReservations = 0;

        userNoOfReservations = m.getNumberOfReservations(MId);
        if (userNoOfReservations > 0) {
            userNoOfReservations--;
        } else {
            userNoOfReservations = 0;
        }

        try {
            Statement s = DBConnection.connect().createStatement();
            s.executeUpdate("DELETE FROM `lms`.`reserve` WHERE `idReserve` = '" + idReserve + "';");
            s.executeUpdate("UPDATE `lms`.`member` SET `No_of_Reservations`='" + userNoOfReservations + "' WHERE `idMember`=" + MId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//expire reservation
    public void expireReservations() {
        try {
            Statement s = DBConnection.connect().createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM `lms`.`reserve`");
            ArrayList reservationsList = new ArrayList();
            while (rs.next()) {
                String[] reservation = new String[4];
                reservation[0] = "" + rs.getInt(1);
                reservation[1] = "" + rs.getInt(2);
                reservation[2] = "" + rs.getInt(3);
                reservation[3] = rs.getString(4);
                reservationsList.add(reservation);
            }
            
            for(int i=0; i<reservationsList.size(); i++){
                String[] reservation = (String[]) reservationsList.get(i);
                idReserve = Integer.parseInt(reservation[0]);
                BId = Integer.parseInt(reservation[1]);
                MId = Integer.parseInt(reservation[2]);
                ReservedDate = reservation[3];
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date reservedDate = dateFormat.parse(ReservedDate);
                Date today = new Date();

                long dateRange = (today.getTime() - reservedDate.getTime()) / (24 * 60 * 60 * 1000);
                if (dateRange > 7) {
                    s.executeUpdate("UPDATE `lms`.`book` SET `Status`='Existing' WHERE `idBook`=" + BId);
                    deleteReserve();
                }
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
    }

    public String getSpecialRemark() {
        return SpecialRemark;
    }

    public void setSpecialRemark(String SpecialRemark) {
        this.SpecialRemark = SpecialRemark;
    }
}
