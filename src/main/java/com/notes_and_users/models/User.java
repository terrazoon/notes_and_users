package com.notes_and_users.models;

import com.notes_and_users.error.InvalidEmailException;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty(message = "Please provide a name")
    private String name;

    @NotEmpty(message = "Please provide an email")
    @Column(name = "EMAIL", unique = true)
    private String email;

    @NotEmpty(message = "Please provide a password")
    @Length(min = 8, message = "The password must be at least 8 characters")
    private String password;

    private Long createTime;
    private Long lastUpdateTime;
    private String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
    private Pattern pattern = Pattern.compile(regex);

    // TODO if we add a UserController later may need to change the way we handle emails
    private User() {
    }


    public User(Long id, String name, String email, String password) {
        if (isBadEmail(email)) {
            throw new InvalidEmailException(email);
        }
        this.id = id;
        this.name = name;
        this.email = email;
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
        Long myTime = System.currentTimeMillis();
        this.createTime = myTime;
        this.lastUpdateTime = myTime;

    }

    public User(String name, String email, String password) {
        if (isBadEmail(email)) {
            throw new InvalidEmailException(email);
        }
        this.name = name;
        this.email = email;
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
        Long myTime = System.currentTimeMillis();
        this.createTime = myTime;
        this.lastUpdateTime = myTime;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    void setEmail(String email) {
        if (isBadEmail(email)) {
            throw new InvalidEmailException(email);
        }
        this.email = email;
    }

    String getPassword() {
        return password;
    }

    void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", createTime='" + createTime + '\'' +
                ", lastUpdateTime='" + lastUpdateTime + '\'' +
                '}';
    }

    private boolean isBadEmail(String email) {
        Matcher matcher = pattern.matcher(email);
        return !matcher.matches();
    }
}
