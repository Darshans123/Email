
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.faces.context.FacesContext;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Darshan
 */
public class emailAccount {
    private String id;
    private String psw;
    private String name;
    private ArrayList<Email> inboxEmail;
    private ArrayList<Email> sentEmail;
    private ArrayList<Email> deletedEmail;
    private int getEmailId;
    private String receiver;
    private String title;
    private String content;
    private String reContent;
    private String fwContent;
    private boolean noti;

    public void setReContent(String reContent) {
        this.reContent = reContent;
    }

    public void setFwContent(String fwContent) {
        this.fwContent = fwContent;
    }

    public String getReContent() {
        return reContent;
    }

    public String getFwContent() {
        return fwContent;
    }

    public boolean isNoti() {
        return noti;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setNoti(boolean noti) {
        this.noti = noti;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public boolean getNoti() {
        return noti;
    }
    

    public void setGetEmailId(int getEmailId) {
        this.getEmailId = getEmailId;
    }
    public int getGetEmailId() {
        return getEmailId;
    }

    public String getId() {
        return id;
    }

    public String getPsw() {
        return psw;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Email> getInboxEmail() {
        return inboxEmail;
    }

    public ArrayList<Email> getSentEmail() {
        return sentEmail;
    }

    public ArrayList<Email> getDeletedEmail() {
        return deletedEmail;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInboxEmail(ArrayList<Email> inboxEmail) {
        this.inboxEmail = inboxEmail;
    }

    public void setSentEmail(ArrayList<Email> sentEmail) {
        this.sentEmail = sentEmail;
    }

    public void setDeletedEmail(ArrayList<Email> deletedEmail) {
        this.deletedEmail = deletedEmail;
    }

    public emailAccount(String _id, String _psw, String _name) {
        id = _id;
        psw = _psw;
        name = _name;
        inboxEmail = new ArrayList<Email> ();
        sentEmail = new ArrayList<Email> ();
        deletedEmail = new ArrayList<Email> ();
        final String DATABASE_URL = "jdbc:mysql://mis-sql.uhcl.edu/sirigerid9653";
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(DATABASE_URL, "sirigerid9653", "1496340");
            st = conn.createStatement();
            rs = st.executeQuery("Select * from email where sender = '" + id + "' or receiver = '" + id + "' order by emailid desc");
            while (rs.next()) {
                if (rs.getString(4).equals(id)) {

                    if (rs.getString(8).equals("deleted")) {
                        deletedEmail.add(new Email(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getInt(9)));
                    } else {
                        if (rs.getString(8).equals("unread"))
                        {
                        inboxEmail.add(new Email(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5) + " (new) ", rs.getString(6), rs.getString(7), rs.getString(8), rs.getInt(9)));
                        }
                        else
                        {
                        inboxEmail.add(new Email(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getInt(9)));
                        }
                    }
                } else {
                    sentEmail.add(new Email(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getInt(9)));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
                st.close();
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String signOut() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index";
    }

    public String getEmailbyID(int i) {
        getEmailId = i;
        return "viewEmail";
    }
    public String getSentEmailbyID(int i) {
        getEmailId = i;
        return "viewSentEmail";
    }
    public String getDelEmailbyID(int i) {
        getEmailId = i;
        return "viewDelEmail";
    }
    public Email readSentEmail()
    {
        for (Email e : sentEmail)
        {
            if (e.getId() == getEmailId)
            {
                return e;
            }
        }
        return null;
    }
    public Email readDeletedEmail()
    {
        for (Email e : deletedEmail)
        {
            if (e.getId() == getEmailId)
            {
                return e;
            }
        }
        return null;
    }
    public Email readSelectedEmail() {
        final String DATABASE_URL = "jdbc:mysql://mis-sql.uhcl.edu/sirigerid9653";
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        int cID = 0;
        int nID = 0;
        try {
            conn = DriverManager.getConnection(DATABASE_URL, "sirigerid9653", "1496340");
            st = conn.createStatement();
            rs = st.executeQuery("Select * from nextaccountnumber");
            while (rs.next())
            {
                cID = rs.getInt(1);
                nID = rs.getInt(1) + 1;
            }
            for (Email e : inboxEmail) {
                if (e.getId() == getEmailId) 
                {
                    if (e.getStatus().equals("unread"))
                    {
                        e.setTitle(e.getTitle().replace(" (new) ", ""));
                        st.executeUpdate("Update email set status = '" + "read" + "' where emailid = '" + getEmailId + "'");
                        e.setStatus("read");
                        if(e.getNoti() == 1)
                        {
                            String con = "Your message to " + id + " has been read!";
                            String tit = "Notification!";
                            st.executeUpdate("Update email set notification = '" + 0 + "' where emailid = '" + getEmailId + "'");
                            e.setNoti(0);
                            st.executeUpdate("Update nextaccountnumber set nextid = '" + nID + "'");
                            st.executeUpdate("Insert into email values ('" + cID + "', '" + id + "', '" + name + "', '" + e.getSender() + "', '" + tit + "', '" + con + "', '" + DateAndTime.DateTime() + "', '" + "unread" + "', '" + 0 + "')");
                            sentEmail.add(0, new Email(cID, id, name, e.getSender(), tit, con, DateAndTime.DateTime(), "unread", 0));
                        }
                    }
                    return e;
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
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
        
        return null;
    }
    
    public String compose()
    {
        int cID = 0;
        int nID = 0;
        final String DATABASE_URL = "jdbc:mysql://mis-sql.uhcl.edu/sirigerid9653";
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        ResultSet rs1 = null;
        try
        {
            conn = DriverManager.getConnection(DATABASE_URL, "sirigerid9653", "1496340");
            st = conn.createStatement();
            rs = st.executeQuery("Select * from nextaccountnumber");
            while(rs.next())
            {
                cID = rs.getInt(1);
                nID = rs.getInt(1) + 1;
            }
            rs1 = st.executeQuery("Select * from register where id = '" + receiver + "'");
            if (rs1.next())
            {
                if (noti == true)
                {
                st.executeUpdate("Insert into email values ('" + cID + "', '" + id + "', '" + name + "', '"+ receiver + "', '" + title + "', '" + content + "', '" + DateAndTime.DateTime() + "', '" + "unread" + "', '" + 1 + "')");
                }
                else
                {
                st.executeUpdate("Insert into email values ('" + cID + "', '" + id + "', '" + name + "', '"+ receiver + "', '" + title + "', '" + content + "', '" + DateAndTime.DateTime() + "', '" + "unread" + "', '" + 0 + "')");
                }
                st.executeUpdate("Update nextaccountnumber set nextId = '" + nID + "'");
                sentEmail.add(0, new Email(cID, id, name, receiver, title, content, DateAndTime.DateTime(), "unread", 1));
                return "Email is sent successfully!";
            }
            else
            {
                return "The receiver ID is not match with our database. Please try again!";
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return "Internal error. Please try again later!";
        }
        finally
        {
            try {
                conn.close();
                st.close();
                rs.close();
                rs1.close();
            } 
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public String reply()
    {
        int cID = 0;
        int nID = 0;
        String tit = "";
        String cont = "";
        String rec = "";
        final String DATABASE_URL = "jdbc:mysql://mis-sql.uhcl.edu/sirigerid9653";
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(DATABASE_URL, "sirigerid9653", "1496340");
            st = conn.createStatement();
            rs = st.executeQuery("Select * from nextaccountnumber");
            while (rs.next())
            {
                cID = rs.getInt(1);
                nID = rs.getInt(1) + 1;
            }
            for (Email e : inboxEmail)
            {
                if (e.getId() == getEmailId)
                {
                    if (e.getTitle().startsWith("RE: "))
                    {
                        tit = e.getTitle();
                    }
                    else
                    {
                        tit = "RE: " + e.getTitle();
                    }
                    cont = reContent + "<br/>" + "------------------------------------------------------------------" + "<br/>" + e.getContent();
                    rec = e.getSender();
                    sentEmail.add(0, e);
                }
            }
            st.executeUpdate("Insert into email values ('" + cID + "', '" + id + "', '" + name + "', '" + rec + "', '" + tit + "', '" + cont + "', '" + DateAndTime.DateTime() + "', '" + "unread" + "', '" + 0 + "')");
            st.executeUpdate("Update nextaccountnumber set nextid = '" + nID + "'");
            return "Email has been replied successfully!";
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
            return "Internal Error. Please try again later!";
        }
        finally{
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
    
    public String forward()
    {
        int cID = 0;
        int nID = 0;
        String tit = "";
        String cont = "";
        final String DATABASE_URL = "jdbc:mysql://mis-sql.uhcl.edu/sirigerid9653";
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(DATABASE_URL, "sirigerid9653", "1496340");
            st = conn.createStatement();
            rs = st.executeQuery("Select * from nextaccountnumber");
            while (rs.next())
            {
                cID = rs.getInt(1);
                nID = rs.getInt(1) + 1;
            }
            for (Email e : inboxEmail)
            {
                if (e.getId() == getEmailId)
                {
                    if (e.getTitle().startsWith("FW: "))
                    {
                        tit = e.getTitle();
                    }
                    else
                    {
                        tit = "FW: " + e.getTitle();
                    }
                     cont = fwContent + "<br/>" + "------------------------------------------------------------------" + "<br/>" + e.getContent();
                    sentEmail.add(0, e);
                }
            }
            st.executeUpdate("Insert into email values ('" + cID + "', '" + id + "', '" + name + "', '" + receiver + "', '" + tit + "', '" + cont + "', '" + DateAndTime.DateTime() + "', '" + "unread" + "', '" + 0 + "')");
            st.executeUpdate("Update nextaccountnumber set nextid = '" + nID + "'");
            return "Email has been forwarded successfully!";
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
            return "Internal Error. Please try again later!";
        }
        finally{
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
    public String delete()
    {
        final String DATABASE_URL = "jdbc:mysql://mis-sql.uhcl.edu/sirigerid9653";
        Connection conn = null;
        Statement st = null;
        try {
            conn = DriverManager.getConnection(DATABASE_URL, "sirigerid9653", "1496340");
            st = conn.createStatement();
            for (Email e : inboxEmail)
            {
                if (e.getId() == getEmailId)
                {
                    st.executeUpdate("Update email set status = '" + "deleted" + "' where emailID = '" + getEmailId + "'");
                    inboxEmail.remove(e);
                    deletedEmail.add(0, e);
                    return "Email has been deleted successfully!";
                }
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
            return "Internal Error. Please try again later!";
        }
        finally{
            try
            {
                conn.close();
                st.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        return "";
    }
}
