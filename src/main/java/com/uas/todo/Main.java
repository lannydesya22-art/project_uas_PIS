package com.uas.todo;

import com.uas.todo.model.Note;
import com.uas.todo.repository.NoteRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    // Nama file database SQLite yang akan dibuat otomatis di folder project
    private static final String DB_URL = "jdbc:sqlite:google_keep.db";

    public static void main(String[] args) {
        // Membuka koneksi ke database otomatis menggunakan try-with-resources
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            if (conn != null) {
                NoteRepository repository = new NoteRepository(conn);
                repository.createTable(); // Membuat tabel catatan jika belum ada

                Scanner scanner = new Scanner(System.in);
                boolean running = true;

                System.out.println("=== Selamat Datang di Google Keep (CLI Clone) ===");

                while (running) {
                    System.out.println("\nMENU UTAMA:");
                    System.out.println("1. Tambah Catatan Baru 📝");
                    System.out.println("2. Tampilkan Semua Catatan 📋");
                    System.out.println("3. Keluar Aplikasi 🚪");
                    System.out.print("Pilih opsi (1-3): ");
                    
                    String pilihan = scanner.nextLine();

                    switch (pilihan) {
                        case "1":
                            System.out.print("\nMasukkan Judul Catatan: ");
                            String title = scanner.nextLine();
                            System.out.print("Masukkan Isi Catatan  : ");
                            String content = scanner.nextLine();
                            
                            repository.addNote(title, content);
                            System.out.println("✓ Sukses! Catatan disimpan ke SQLite.");
                            break;

                        case "2":
                            System.out.println("\n=======================================");
                            System.out.println("         DAFTAR CATATAN GOOGLE KEEP    ");
                            System.out.println("=======================================");
                            List<Note> notes = repository.getAllNotes();
                            
                            if (notes.isEmpty()) {
                                System.out.println("       (Belum ada catatan disimpan)    ");
                            } else {
                                for (Note note : notes) {
                                    System.out.println(note);
                                }
                            }
                            break;

                        case "3":
                            running = false;
                            System.out.println("\nTerima kasih! Keluar dari aplikasi.");
                            break;

                        default:
                            System.out.println("⚠️ Pilihan salah! Silakan masukkan angka 1, 2, atau 3.");
                    }
                }
                scanner.close();
            }
        } catch (SQLException e) {
            System.out.println("Terjadi kesalahan database: " + e.getMessage());
        }
    }
}