package com.example.notes;

public class Note {
    private String headText;
    private String bodyText;
    private long date;

    public Note(String headText,String bodyText,long date){
        this.headText=headText;
        this.bodyText=bodyText;
        this.date=date;
    }

    public String getHeadText() {
        return headText;
    }

    public String getBodyText() {
        return bodyText;
    }

    public long getDate() {
        return date;
    }
}
