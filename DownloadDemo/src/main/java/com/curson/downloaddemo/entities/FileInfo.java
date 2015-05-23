package com.curson.downloaddemo.entities;

import java.io.Serializable;

/**
 * 文件信息
 */
public class FileInfo implements Serializable {

    private int id;
    private int length;
    private int finished;
    private String url;
    private String fileName;

    public FileInfo() {
    }

    public FileInfo(String fileName, int finished, int id, int length, String url) {
        this.fileName = fileName;
        this.finished = finished;
        this.id = id;
        this.length = length;
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "fileName='" + fileName + '\'' +
                ", id=" + id +
                ", length=" + length +
                ", finished=" + finished +
                ", url='" + url + '\'' +
                '}';
    }
}
