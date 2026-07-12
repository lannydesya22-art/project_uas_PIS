# Google Keep Clone CLI (UAS PIS)

## Deskripsi

Google Keep Clone CLI merupakan project berbasis Command Line Interface (CLI) yang dibuat sebagai tugas Ujian Akhir Semester mata kuliah Pengujian dan Implementasi Sistem (PIS). Project ini digunakan untuk mengelola catatan seperti menambah, mengubah, mengarsipkan, membuang ke trash, mencari, dan menghapus catatan secara permanen.

Data catatan disimpan menggunakan database SQLite sehingga seluruh data tetap tersimpan meskipun aplikasi ditutup.

---

## Teknologi yang Digunakan

- Java
- Apache Maven
- SQLite
- JUnit 5
- Mockito

---


## Fitur Aplikasi

### 1. Notes

Menu ini menampilkan seluruh catatan yang masih aktif.

Fitur yang tersedia:

- Tambah catatan
- Edit catatan
- Arsipkan catatan
- Buang ke Trash
- Kembali ke menu utama

---

### 2. Archive

Menampilkan seluruh catatan yang sudah diarsipkan.

Fitur:

- Mengembalikan catatan ke Notes (Unarchive)
- Kembali ke menu utama

---

### 3. Trash

Menampilkan seluruh catatan yang berada di tempat sampah.

Fitur:

- Restore catatan
- Hapus permanen
- Kembali ke menu utama

---

### 4. Search

Digunakan untuk mencari catatan berdasarkan kata kunci yang terdapat pada judul maupun isi catatan.

---

### 5. Keluar

Menutup aplikasi dengan aman.

---

## Struktur Penyimpanan

Semua data disimpan pada file SQLite:

```
google_keep.db
```

Database akan dibuat secara otomatis apabila belum tersedia.

---

## Pengujian

Dokumentasi pengujian dapat dilihat pada file **testing.md**.