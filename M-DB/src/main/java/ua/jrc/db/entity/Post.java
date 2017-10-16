package ua.jrc.db.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "POSTS")
public class Post implements Serializable{
    @Id
    String mid;

    @Column(name = "BID")
    String bid;

    @Column(name = "LOGIN")
    String login;

    @Column(name = "TEXT")
    String text;

    public Post() {
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Post{" +
                "mid='" + mid + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
