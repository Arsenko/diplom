package com.example.notes;

import java.util.Date;
import java.util.List;
import java.util.Map;

interface NoteStore {
    List<Map<String, String>> getData(String admin, String name);
    Note getNote(String noteId);
    void addNote(String headText, String bodyText, String creatorName, Date date);
    void udpateNote(String headText, String bodyText, String noteId,Date date);
    void deleteNote(String noteId);
}
