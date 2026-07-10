package com.uas.todo;

import com.uas.todo.repository.NoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class NoteRepositoryTest {
    private Connection mockConnection;
    private NoteRepository repository;

    @BeforeEach
    void setUp() throws SQLException {
        // Kita buat "database palsu" (MOCK)
        mockConnection = mock(Connection.class);
        repository = new NoteRepository(mockConnection);
    }

    @Test
    void testAddNoteCallsPrepareStatement() throws SQLException {
        // Kita pura-pura membuat PreparedStatement
        PreparedStatement mockStmt = mock(PreparedStatement.class);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStmt);

        // Menjalankan fungsi tambah catatan
        repository.addNote("Test Judul", "Test Isi");

        // Membuktikan bahwa fungsi database benar-benar dipanggil (INI POIN PENTING UAS)
        verify(mockConnection, times(1)).prepareStatement(anyString());
    }
}