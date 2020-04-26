package com.notes_and_users.models;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Entity
public class Note {

    @Id
    @GeneratedValue
    private Long id;

    private Long userId;

    @NotEmpty(message = "Please provide a title")
    @Length(max = 50, message = "The title cannot be more than 50 characters")
    private String title;

    @NotEmpty(message = "Please provide a note")
    @Length(max = 1000, message = "The note cannot be more than 1000 characters")
    private String note;

    private Long createTime;
    private Long lastUpdateTime;

    public Note() {

        Long myTime = System.currentTimeMillis();
        this.createTime = myTime;
        this.lastUpdateTime = myTime;

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

    public void setUserId(Long userId) {
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


    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
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
