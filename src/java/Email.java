/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Darshan
 */
public class Email {
    private int id;
    private String sender;
    private String senderName;
    private String receiver;
    private String title;
    private String content;
    private String dateTime;
    private String status;
    private int noti;
    
    public Email(int _id, String _s, String _sn, String _r, String _t, String _c, String _d, String _st, int _n)
    {
        id = _id;
        sender = _s;
        senderName = _sn;
        receiver = _r;
        title = _t;
        content = _c;
        dateTime = _d;
        status = _st;
        noti = _n;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
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

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setNoti(int noti) {
        this.noti = noti;
    }

    public int getId() {
        return id;
    }

    public String getSender() {
        return sender;
    }

    public String getSenderName() {
        return senderName;
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

    public String getDateTime() {
        return dateTime;
    }

    public String getStatus() {
        return status;
    }

    public int getNoti() {
        return noti;
    }
    
}
