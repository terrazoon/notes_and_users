package com.notes_and_users.models;

import com.notes_and_users.error.validator.ValidUser;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Note {

    @Id
    @GeneratedValue
    private Long id;

    //@ValidUser
    private Long userId;

    @NotEmpty(message = "Please provide a title")
    private String title;

    @NotEmpty(message = "Please provide a note")
    private String note;

    private Long createTime;
    private Long lastUpdateTime;

    // avoid this "No default constructor for entity"
    public Note() {
    }

    public Note(Long userId, String title, String note) {
        this.userId = userId;
        this.title = title;
        this.note = note;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long user_id) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", title='" + title + '\'' +
                ", note='" + note + '\'' +
                ", createTime='" + createTime + '\'' +
                ", lastUpdateTime='" + lastUpdateTime + '\'' +
                '}';
    }
}
