package com.uas.todo.repository;

import com.uas.todo.model.Note;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NoteRepository {
    private Connection connection;

    public NoteRepository(Connection connection) {
        this.connection = connection;
    }

    // Membuat tabel dengan kolom status tambahan bawaan "ACTIVE"
    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS notes (" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                     "title TEXT NOT NULL, " +
                     "content TEXT NOT NULL, " +
                     "status TEXT DEFAULT 'ACTIVE'" +
                     ");";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addNote(String title, String content) {
        String sql = "INSERT INTO notes(title, content, status) VALUES(?, ?, 'ACTIVE')";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, content);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Mengambil catatan berdasarkan status tertentu (Notes, Archive, atau Trash)
    public List<Note> getNotesByStatus(String status) {
        List<Note> notes = new ArrayList<>();
        String sql = "SELECT * FROM notes WHERE status = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, status);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    notes.add(new Note(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getString("status")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notes;
    }

    // Mengubah status catatan (Pindah ke Trash atau Archive)
    public void updateStatus(int id, String newStatus) {
        String sql = "UPDATE notes SET status = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, newStatus);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Hapus permanen jika dihapus dari folder Trash
    public void deletePermanently(int id) {
        String sql = "DELETE FROM notes WHERE id = ? AND status = 'TRASH'";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}