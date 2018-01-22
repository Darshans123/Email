/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Darshan
 */
@Named(value = "register")
@RequestScoped
public class register {

    private String emailId;
    private String name;
    private String pwd;

    public String getEmailId() {
        return emailId;
    }

    public String getName() {
        return name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    
    public String register() {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            
        }
        catch (Exception e)
        {
            return ("Internal Error! Please try again later.");
        }
        final String DATABASE_URL = "jdbc:mysql://mis-sql.uhcl.edu/sirigerid9653";
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try
        {
            conn = DriverManager.getConnection(DATABASE_URL, "sirigerid9653", "1496340");
            st = conn.createStatement();
            rs = st.executeQuery("Select * from register where id = '" + emailId + "'");
            if (rs.next())
            {
                return ("The email ID already exists");
            }
            else
            {
                if (emailId.matches(".*[a-zA-Z].*") && emailId.matches(".*[0-9].*"))
                {
                    st.executeUpdate("Insert into register values ('" + emailId + "', '" + pwd + "', '" + name + "')");
                    return ("Your email is created successfully!!");
                }
                else
                {
                    return ("Email ID must have both LETTERS and DIGITS");
                }
            }
        }
        catch(SQLException e)
        {
           e.printStackTrace();
           return ("Internal Error! Please try again later.");
        }
        finally
        {
            try
            {
                conn.close();
                st.close();
                rs.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    
}
