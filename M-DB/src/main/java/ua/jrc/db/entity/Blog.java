package ua.jrc.db.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "BLOGS")
public class Blog implements Serializable{
    @Id
    String bid;

    @Column(name = "SUBJECT")
    String subject;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "LINKS",
            joinColumns = @JoinColumn(name = "BID"),
            inverseJoinColumns = @JoinColumn(name = "MID")
    )
    private Set<Post> posts;

    public Blog() {
    }

    public Blog(String bid, String subject) {
        this.bid = bid;
        this.subject = subject;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "bid='" + bid + '\'' +
                ", subject='" + subject + '\'' +
                '}';
    }
}
