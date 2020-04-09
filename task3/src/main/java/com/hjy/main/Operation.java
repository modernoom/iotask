package com.hjy.main;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Date;

/**
 * 用户操作实体类
 * @author huangjinyong
 */
public class Operation {
    private IntegerProperty id=new SimpleIntegerProperty();
    private StringProperty dir=new SimpleStringProperty();
    private StringProperty line=new SimpleStringProperty();
    private StringProperty content=new SimpleStringProperty();
    private StringProperty statusString=new SimpleStringProperty();
    private IntegerProperty status=new SimpleIntegerProperty();
    private Date time;

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(Integer id) {
        this.id.set(id);
    }

    public String getDir() {
        return dir.get();
    }

    public StringProperty dirProperty() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir.set(dir);
    }

    public String getLine() {
        return line.get();
    }

    public StringProperty lineProperty() {
        return line;
    }

    public void setLine(String line) {
        this.line.set(line);
    }

    public String getContent() {
        return content.get();
    }

    public StringProperty contentProperty() {
        return content;
    }

    public void setContent(String content) {
        this.content.set(content);
    }

    public String getStatusString() {
        return statusString.get();
    }

    public StringProperty statusStringProperty() {
        return statusString;
    }

    public void setStatusString(String statusString) {
        this.statusString.set(statusString);
    }

    public int getStatus() {
        return status.get();
    }

    public IntegerProperty statusProperty() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status.set(status);
        if(status==1){
            setStatusString("操作成功");
        }
        if(status==0){
            setStatusString("操作失败");
        }
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
