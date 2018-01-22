/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Darshan
 */
@Named(value = "login")
@SessionScoped
public class login implements Serializable {

    /**
     * Creates a new instance of login
     */
    private String id;
    private String psw;
    private emailAccount theLoginAccount;

    public String getId() {
        return id;
    }

    public String getPsw() {
        return psw;
    }

    public emailAccount getTheLoginAccount() {
        return theLoginAccount;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public void setTheLoginAccount(emailAccount theLoginAccount) {
        this.theLoginAccount = theLoginAccount;
    }
    
    public String login() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } 
        catch (Exception e) {
            return "Internal Error";
        }
        final String DATABASE_URL = "jdbc:mysql://mis-sql.uhcl.edu/sirigerid9653";
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        
        try {
            conn = DriverManager.getConnection(DATABASE_URL, "sirigerid9653", "1496340");
            st = conn.createStatement();
            rs = st.executeQuery("Select * from register where id = '" + id + "' and psw = '" + psw + "'");
            if (rs.next())
            {
                theLoginAccount = new emailAccount(id, psw, rs.getString(3));
                return "welcome";
            }
            else
            {
                return "loginNotOk";
            }
        } 
        catch (SQLException e) {
            e.printStackTrace();
            return "Internal Error";
        }
        finally
        {
            try {
                conn.close();
                st.close();
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
