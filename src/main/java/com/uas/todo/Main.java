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

                System.out.println("GOOGLE KEEP CLI");

                while (running) {
                    System.out.println("\nMenu Utama:");
                    System.out.println("1. Notes");
                    System.out.println("2. Archive");
                    System.out.println("3. Trash");
                    System.out.println("4. Search");
                    System.out.println("5. Keluar");
                    System.out.print("Pilih menu (1-5): ");
                    
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
                            handleSearchPage(repository, scanner);
                            break;
                        case "5":
                            running = false;
                            System.out.println("\nKeluar dari aplikasi.");
                            break;
                        default:
                            System.out.println("Pilihan tidak ada. Masukkan angka 1 sampai 5.");
                    }
                }
                scanner.close();
            }
        } catch (SQLException e) {
            System.out.println("Error database: " + e.getMessage());
        }
    }

    private static void handleNotesPage(NoteRepository repository, Scanner scanner) {
        boolean inPage = true;
        while (inPage) {
            System.out.println("\n-- NOTES --");
            List<Note> notes = repository.getNotesByStatus("ACTIVE");
            printNotes(notes);

            System.out.println("Aksi:");
            System.out.println("a. Tambah Catatan");
            System.out.println("b. Edit Catatan");
            System.out.println("c. Arsipkan");
            System.out.println("d. Buang ke Trash");
            System.out.println("e. Kembali");
            System.out.print("Pilih (a-e): ");
            String aksi = scanner.nextLine().toLowerCase();

            switch (aksi) {
                case "a":
                    System.out.print("Judul: ");
                    String title = scanner.nextLine();
                    System.out.print("Isi  : ");
                    String content = scanner.nextLine();
                    repository.addNote(title, content);
                    System.out.println("Catatan berhasil disimpan.");
                    break;
                case "b":
                    System.out.print("ID Catatan yang mau diedit: ");
                    int idEdit = Integer.parseInt(scanner.nextLine());
                    System.out.print("Judul baru: ");
                    String newTitle = scanner.nextLine();
                    System.out.print("Isi baru  : ");
                    String newContent = scanner.nextLine();
                    repository.updateNote(idEdit, newTitle, newContent);
                    System.out.println("Catatan berhasil diupdate.");
                    break;
                case "c":
                    System.out.print("ID Catatan yang mau diarsipkan: ");
                    int idSub = Integer.parseInt(scanner.nextLine());
                    repository.updateStatus(idSub, "ARCHIVED");
                    System.out.println("Catatan dipindah ke Archive.");
                    break;
                case "d":
                    System.out.print("ID Catatan yang mau dibuang: ");
                    int idTrash = Integer.parseInt(scanner.nextLine());
                    repository.updateStatus(idTrash, "TRASH");
                    System.out.println("Catatan dipindah ke Trash.");
                    break;
                case "e":
                    inPage = false;
                    break;
                default:
                    System.out.println("Pilihan salah.");
            }
        }
    }

    private static void handleArchivePage(NoteRepository repository, Scanner scanner) {
        boolean inPage = true;
        while (inPage) {
            System.out.println("\n-- ARCHIVE --");
            List<Note> notes = repository.getNotesByStatus("ARCHIVED");
            printNotes(notes);

            System.out.println("Aksi:");
            System.out.println("a. Unarchive (Kembalikan ke Notes)");
            System.out.println("b. Kembali");
            System.out.print("Pilih (a-b): ");
            String aksi = scanner.nextLine().toLowerCase();

            if (aksi.equals("a")) {
                System.out.print("ID Catatan: ");
                int id = Integer.parseInt(scanner.nextLine());
                repository.updateStatus(id, "ACTIVE");
                System.out.println("Catatan dikembalikan ke Notes.");
            } else if (aksi.equals("b")) {
                inPage = false;
            } else {
                System.out.println("Pilihan salah.");
            }
        }
    }

    private static void handleTrashPage(NoteRepository repository, Scanner scanner) {
        boolean inPage = true;
        while (inPage) {
            System.out.println("\n-- TRASH --");
            List<Note> notes = repository.getNotesByStatus("TRASH");
            printNotes(notes);

            System.out.println("Aksi:");
            System.out.println("a. Restore (Kembalikan ke Notes)");
            System.out.println("b. Hapus Permanen");
            System.out.println("c. Kembali");
            System.out.print("Pilih (a-c): ");
            String aksi = scanner.nextLine().toLowerCase();

            switch (aksi) {
                case "a":
                    System.out.print("ID Catatan: ");
                    int idRestore = Integer.parseInt(scanner.nextLine());
                    repository.updateStatus(idRestore, "ACTIVE");
                    System.out.println("Catatan berhasil dipulihkan.");
                    break;
                case "b":
                    System.out.print("ID Catatan yang mau dihapus permanen: ");
                    int idPerm = Integer.parseInt(scanner.nextLine());
                    repository.deletePermanently(idPerm);
                    System.out.println("Catatan dihapus permanen.");
                    break;
                case "c":
                    inPage = false;
                    break;
                default:
                    System.out.println("Pilihan salah.");
            }
        }
    }

    private static void handleSearchPage(NoteRepository repository, Scanner scanner) {
        System.out.println("\n-- SEARCH --");
        System.out.print("Kata kunci: ");
        String keyword = scanner.nextLine();

        List<Note> results = repository.searchNotes(keyword);
        System.out.println("\nHasil pencarian:");
        printNotes(results);

        System.out.println("\nTekan Enter untuk kembali...");
        scanner.nextLine();
    }

    private static void printNotes(List<Note> notes) {
        if (notes.isEmpty()) {
            System.out.println("(Kosong)");
        } else {
            for (Note note : notes) {
                System.out.println(note);
            }
        }
    }
}