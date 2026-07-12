# Dokumentasi & Laporan Pengujian: Google Keep Clone CLI

Aplikasi ini dikembangkan sebagai tugas UAS mata kuliah PIS. Sistem ini merupakan aplikasi manajemen catatan berbasis Command Line Interface (CLI) menggunakan bahasa pemrograman Java dan database SQLite untuk penyimpanan data lokal secara persisten.

## Cara Menjalankan Program
Pastikan JDK dan Maven sudah terinstal di perangkat. Untuk menjalankan program, buka terminal pada direktori proyek dan ketik perintah berikut:

mvn compile exec:java "-Dexec.mainClass=com.uas.todo.Main"

Saat pertama kali dijalankan, sistem akan otomatis membuat file database `google_keep.db` jika belum tersedia.

---

## Deskripsi Fitur & Alur Navigasi
Aplikasi ini memiliki 5 menu utama dengan sub-menu untuk setiap aksi pengelolaannya:

### 1. Menu Notes (1)
Menampilkan daftar catatan yang berstatus `ACTIVE`. Sub-menu aksi yang tersedia:
- **a. Tambah Catatan**: Memasukkan judul dan isi untuk menyimpan catatan baru ke database.
- **b. Edit Catatan**: Memasukkan ID catatan, lalu memperbarui judul dan isi catatan.
- **c. Arsipkan**: Memasukkan ID catatan untuk memindahkannya ke menu Archive (ubah status menjadi `ARCHIVED`).
- **d. Buang ke Trash**: Memasukkan ID catatan untuk membuangnya sementara ke tempat sampah (ubah status menjadi `TRASH`).
- **e. Kembali**: Kembali ke Menu Utama.

### 2. Menu Archive (2)
Menampilkan daftar catatan yang berstatus `ARCHIVED`. Sub-menu aksi yang tersedia:
- **a. Unarchive**: Memasukkan ID catatan untuk mengembalikannya ke daftar aktif di menu Notes (ubah status menjadi `ACTIVE`).
- **b. Kembali**: Kembali ke Menu Utama.

### 3. Menu Trash (3)
Menampilkan daftar catatan yang berstatus `TRASH`. Sub-menu aksi yang tersedia:
- **a. Restore**: Memasukkan ID catatan untuk memulihkannya kembali ke menu Notes (ubah status menjadi `ACTIVE`).
- **b. Hapus Permanen**: Memasukkan ID catatan untuk menghapusnya secara permanen dari database (`DELETE` query).
- **c. Kembali**: Kembali ke Menu Utama.

### 4. Menu Search (4)
- Pengguna memasukkan kata kunci (*keyword*), dan sistem akan mencari serta menampilkan catatan yang mengandung kata kunci tersebut pada bagian judul maupun isi catatan.
- Menekan Enter untuk kembali ke Menu Utama.

### 5. Menu Keluar (5)
- Menutup sesi dan menghentikan jalannya aplikasi secara aman.

---

## Laporan Pengujian (Testing)

### 1. Pengujian Fungsional (Manual via CLI)
Saya telah menguji seluruh alur navigasi dari Menu Utama hingga Sub-menu untuk memastikan tidak ada kecacatan logika maupun *crash* pada terminal:

- **Navigasi Menu Utama**: Input angka `1` sampai `5` berjalan sesuai dengan routing halaman yang ditentukan. Input di luar angka tersebut berhasil dimitigasi dengan pesan validasi.
- **Fitur CRUD pada Notes**: 
  - Penambahan catatan baru melalui opsi `a` berhasil menyimpan data secara real-time ke dalam SQLite.
  - Pengeditan catatan melalui opsi `b` dengan memasukkan ID yang valid berhasil memperbarui teks pada database.
- **Perpindahan Status (State Management)**:
  - Catatan yang diarsipkan (opsi `c` di menu Notes) hilang dari daftar Notes dan berhasil muncul saat menu Archive dibuka.
  - Fitur *Unarchive* dan *Restore* berhasil mengembalikan catatan ke dalam daftar utama Notes.
- **Penghapusan Permanen**: Memilih opsi `b` pada menu Trash dan memasukkan ID catatan terbukti menghapus baris data sepenuhnya dari database.
- **Pencarian Data (Search)**: Tes pencarian menggunakan potongan kata berhasil menyeleksi dan menampilkan daftar catatan yang relevan.

### 2. Pengujian Unit (Automated)
Pengujian juga dilakukan secara otomatis menggunakan **JUnit 5** dan **Mockito** untuk memvalidasi lapisan *backend* (repository):
- **NoteRepositoryTest**: Memastikan eksekusi query SQL (Insert, Select by Status, Update Status, Update Note, Delete Permanently, dan Search) berfungsi dengan tepat sesuai logika sistem.
- **Hasil Pengujian**: Seluruh *test case* pada pengujian unit ini telah dijalankan dengan status akhir **100% Passed (Berhasil)**.