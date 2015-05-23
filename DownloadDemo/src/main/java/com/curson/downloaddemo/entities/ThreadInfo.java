package com.curson.downloaddemo.entities;

/**
 * 线程信息
 * Created by Curson on 15/4/9.
 */
public class ThreadInfo {

    private int id;
    private String url;
    private int start;
    private int end;
    private int finished;//线程进度

    @Override
    public String toString() {
        return "ThreadInfo{" +
                "end=" + end +
                ", id=" + id +
                ", url='" + url + '\'' +
                ", start=" + start +
                ", finished=" + finished +
                '}';
    }

    public ThreadInfo() {
    }

    public ThreadInfo(int end, int finished, int id, int start, String url) {

        this.end = end;
        this.finished = finished;
        this.id = id;
        this.start = start;
        this.url = url;
    }

    public int getEnd() {

        return end;
    }

    public void setEnd(int end) {
        this.end = end;
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

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
