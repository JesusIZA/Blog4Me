package ua.jrc.db.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "POSTS")
public class Post implements Serializable{
    @Id
    String mid;

    @Column(name = "TEXT")
    String text;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "posts")
    private Set<User> users;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "posts")
    private Set<Blog> blogs;

    public Post() {
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(Set<Blog> blogs) {
        this.blogs = blogs;
    }

    @Override
    public String toString() {
        return "Post{" +
                "mid='" + mid + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
