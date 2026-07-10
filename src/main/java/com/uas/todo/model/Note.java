package com.uas.todo.model;

public class Note {
    private int id;
    private String title;
    private String content;

    // Constructor untuk membuat Catatan baru
    public Note(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    // Getter dan Setter (Akses data)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    // Mengubah objek catatan menjadi teks rapi saat dicetak di terminal
    @Override
    public String toString() {
        return id + ". Judul: " + title + "\n   Isi  : " + content + "\n-----------------------";
    }
}