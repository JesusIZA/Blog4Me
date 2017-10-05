package ua.jrc.db.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "USERS")
public class User implements Serializable{
    @Id
    String login;

    @Column(name = "FIRST_NAME")
    String firstName;

    @Column(name = "LAST_NAME")
    String lastName;

    @Column(name = "PASSWORD")
    String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "LINKS",
            joinColumns = @JoinColumn(name = "LOGIN"),
            inverseJoinColumns = @JoinColumn(name = "MID")
    )
    private Set<Post> posts;

    public User() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
