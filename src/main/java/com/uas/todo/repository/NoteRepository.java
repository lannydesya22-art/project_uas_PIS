package com.uas.todo.repository;

import com.uas.todo.model.Note;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NoteRepository {
    private Connection connection;

    // Constructor ini menerima Connection langsung dari luar.
    // Trik ini wajib dipakai supaya database-nya bisa di-MOCKING saat testing!
    public NoteRepository(Connection connection) {
        this.connection = connection;
    }

    // Fungsi untuk membuat tabel catatan ala Google Keep jika belum ada
    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS notes (" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                     "title TEXT NOT NULL, " +
                     "content TEXT NOT NULL" +
                     ");";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Fitur: Menambahkan Catatan Baru
    public void addNote(String title, String content) {
        String sql = "INSERT INTO notes(title, content) VALUES(?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, content);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Fitur: Mengambil Semua Catatan dari Database
    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<>();
        String sql = "SELECT * FROM notes";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                notes.add(new Note(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("content")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notes;
    }
}