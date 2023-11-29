package com.example.android13;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GameRecord extends LitePalSupport {
    private Long id;
    private String title;
    private Date date;
    private List<String> role;  // Mark whether the previous round is white chess or not.
    private List<String> command;
    private int index;

    public GameRecord() {
        this.title = "";
        this.date = null;
        this.role = new ArrayList<>();
        this.command = new ArrayList<>();
        this.index = 0;
    }

    public GameRecord(String title, Date now) {
        this.title = title;
        this.date = now;
        this.role = new ArrayList<>();
        this.command = new ArrayList<>();
        this.index = 0;
    }

    public List<String> getRole() {
        return role;
    }

    public void setRole(List<String> role) {
        this.role = role;
    }

    public int getIndex() {
        if (index < role.size()) {
            return index;
        } else {
            return -1;
        }
    }

    public void addIndex(){
        index++;
    }

    public void subIndex(){
        index--;
    }

    public void addRole(boolean isWhite) {
        if (isWhite) {
            role.add("white");
        } else {
            role.add("black");
        }
    }

    public String getLastRole(){
        String s = this.role.get(this.role.size()-1);
        this.role.remove(this.role.size()-1);
        return s;
    }

    public String getLastCommand(){
        String s = this.command.get(this.command.size()-1);
        this.command.remove(this.command.size()-1);
        return s;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<String> getCommand() {
        return command;
    }

    public void setCommand(List<String> command) {
        this.command = command;
    }

    public void addNewCommand(String str) {
        this.command.add(str);
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "GameRecord{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", date=" + date +
                ", role=" + role +
                ", command=" + command +
                ", index=" + index +
                '}';
    }
}
