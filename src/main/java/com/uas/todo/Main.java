package com.uas.todo;

import com.uas.todo.model.Note;
import com.uas.todo.repository.NoteRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String DB_URL = "jdbc:sqlite:google_keep.db";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            if (conn != null) {
                NoteRepository repository = new NoteRepository(conn);
                repository.createTable();

                Scanner scanner = new Scanner(System.in);
                boolean running = true;

                System.out.println("=== Selamat Datang di Google Keep (CLI Clone) ===");

                while (running) {
                    System.out.println("\n--- NAVIGASI UTAMA ---");
                    System.out.println("1. Tampilkan Catatan Utama (Notes) ");
                    System.out.println("2. Tampilkan Arsip (Archive) ");
                    System.out.println("3. Tampilkan Tempat Sampah (Trash) ");
                    System.out.println("4. Keluar Aplikasi ");
                    System.out.print("Pilih halaman (1-4): ");
                    
                    String halaman = scanner.nextLine();

                    switch (halaman) {
                        case "1":
                            handleNotesPage(repository, scanner);
                            break;
                        case "2":
                            handleArchivePage(repository, scanner);
                            break;
                        case "3":
                            handleTrashPage(repository, scanner);
                            break;
                        case "4":
                            running = false;
                            System.out.println("\nTerima kasih! Keluar dari aplikasi.");
                            break;
                        default:
                            System.out.println(" Pilihan salah! Masukkan angka 1 sampai 4.");
                    }
                }
                scanner.close();
            }
        } catch (SQLException e) {
            System.out.println("Terjadi kesalahan database: " + e.getMessage());
        }
    }

    
    private static void handleNotesPage(NoteRepository repository, Scanner scanner) {
        boolean inPage = true;
        while (inPage) {
            System.out.println("\n=======================================");
            System.out.println("          HALAMAN UTAMA: NOTES        ");
            System.out.println("=======================================");
            List<Note> notes = repository.getNotesByStatus("ACTIVE");
            printNotes(notes);

            System.out.println("\nAKSI:");
            System.out.println("a. Tambah Catatan Baru");
            System.out.println("b. Arsipkan Catatan (Pindah ke Archive)");
            System.out.println("c. Buang Catatan (Pindah ke Trash)");
            System.out.println("d. Kembali ke Navigasi Utama");
            System.out.print("Pilih aksi (a-d): ");
            String aksi = scanner.nextLine().toLowerCase();

            switch (aksi) {
                case "a":
                    System.out.print("\nMasukkan Judul Catatan: ");
                    String title = scanner.nextLine();
                    System.out.print("Masukkan Isi Catatan  : ");
                    String content = scanner.nextLine();
                    repository.addNote(title, content);
                    System.out.println(" Sukses! Catatan aktif berhasil ditambahkan.");
                    break;
                case "b":
                    System.out.print("\nMasukkan ID Catatan yang ingin diarsipkan: ");
                    int idSub = Integer.parseInt(scanner.nextLine());
                    repository.updateStatus(idSub, "ARCHIVED");
                    System.out.println("Catatan berhasil dipindah ke Arsip.");
                    break;
                case "c":
                    System.out.print("\nMasukkan ID Catatan yang ingin dibuang: ");
                    int idTrash = Integer.parseInt(scanner.nextLine());
                    repository.updateStatus(idTrash, "TRASH");
                    System.out.println("Catatan dipindah ke Tempat Sampah.");
                    break;
                case "d":
                    inPage = false;
                    break;
                default:
                    System.out.println("Aksi tidak valid!");
            }
        }
    }

    
    private static void handleArchivePage(NoteRepository repository, Scanner scanner) {
        boolean inPage = true;
        while (inPage) {
            System.out.println("\n=======================================");
            System.out.println("         HALAMAN: ARSIP (ARCHIVE)     ");
            System.out.println("=======================================");
            List<Note> notes = repository.getNotesByStatus("ARCHIVED");
            printNotes(notes);

            System.out.println("\nAKSI:");
            System.out.println("a. Kembalikan ke Notes Utama (Unarchive)");
            System.out.println("b. Kembali ke Navigasi Utama");
            System.out.print("Pilih aksi (a-b): ");
            String aksi = scanner.nextLine().toLowerCase();

            if (aksi.equals("a")) {
                System.out.print("\nMasukkan ID Catatan untuk di-unarchive: ");
                int id = Integer.parseInt(scanner.nextLine());
                repository.updateStatus(id, "ACTIVE");
                System.out.println("Catatan dikembalikan ke halaman utama.");
            } else if (aksi.equals("b")) {
                inPage = false;
            } else {
                System.out.println("Aksi tidak valid!");
            }
        }
    }

    
    private static void handleTrashPage(NoteRepository repository, Scanner scanner) {
        boolean inPage = true;
        while (inPage) {
            System.out.println("\n=======================================");
            System.out.println("      HALAMAN: TEMPAT SAMPAH (TRASH)  ");
            System.out.println("=======================================");
            List<Note> notes = repository.getNotesByStatus("TRASH");
            printNotes(notes);

            System.out.println("\nAKSI:");
            System.out.println("a. Pulihkan Catatan (Restore)");
            System.out.println("b. Hapus Permanen");
            System.out.println("c. Kembali ke Navigasi Utama");
            System.out.print("Pilih aksi (a-c): ");
            String aksi = scanner.nextLine().toLowerCase();

            switch (aksi) {
                case "a":
                    System.out.print("\nMasukkan ID Catatan untuk dipulihkan: ");
                    int idRestore = Integer.parseInt(scanner.nextLine());
                    repository.updateStatus(idRestore, "ACTIVE");
                    System.out.println("Catatan berhasil dipulihkan.");
                    break;
                case "b":
                    System.out.print("\nMasukkan ID Catatan untuk DIHAPUS PERMANEN: ");
                    int idPerm = Integer.parseInt(scanner.nextLine());
                    repository.deletePermanently(idPerm);
                    System.out.println("Catatan telah dihapus permanen dari database.");
                    break;
                case "c":
                    inPage = false;
                    break;
                default:
                    System.out.println("Aksi tidak valid!");
            }
        }
    }

    private static void printNotes(List<Note> notes) {
        if (notes.isEmpty()) {
            System.out.println("       (Tidak ada catatan di folder ini)  ");
        } else {
            for (Note note : notes) {
                System.out.println(note);
            }
        }
    }
}