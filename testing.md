# Dokumentasi Pengujian

Pengujian dilakukan secara langsung melalui terminal untuk memastikan setiap fitur pada aplikasi dapat berjalan dengan baik.

---

## Cara Menjalankan Program

Pastikan JDK dan Apache Maven sudah terinstal.

Masuk ke folder project kemudian jalankan perintah berikut.

```bash
mvn compile exec:java "-Dexec.mainClass=com.uas.todo.Main"
```

Saat pertama kali dijalankan, aplikasi akan otomatis membuat database SQLite dengan nama:

```
google_keep.db
```

---

## 1. Pengujian Menu Notes

Pengujian dilakukan dengan menambahkan catatan baru melalui menu Notes. Setelah judul dan isi dimasukkan, data berhasil tersimpan dan langsung muncul pada daftar catatan aktif.

Selanjutnya dilakukan pengujian edit catatan dengan memilih salah satu ID yang tersedia. Setelah judul dan isi diubah, perubahan berhasil tersimpan.

Fitur Arsipkan juga diuji dengan memilih ID catatan. Catatan berhasil berpindah dari menu Notes ke menu Archive. Kemudian fitur Buang ke Trash diuji dan catatan berhasil berpindah ke menu Trash.

---

## 2. Pengujian Menu Archive

Pada menu Archive dilakukan pengujian fitur Unarchive. Catatan yang sebelumnya diarsipkan berhasil dikembalikan ke menu Notes dan sudah tidak muncul lagi pada daftar Archive.

---

## 3. Pengujian Menu Trash

Pengujian dilakukan menggunakan fitur Restore dan Hapus Permanen.

Fitur Restore berhasil mengembalikan catatan ke menu Notes, sedangkan fitur Hapus Permanen berhasil menghapus data dari database sehingga catatan tidak dapat ditampilkan kembali.

---

## 4. Pengujian Search

Pengujian dilakukan dengan memasukkan beberapa kata kunci yang terdapat pada judul maupun isi catatan. Sistem berhasil menampilkan catatan yang sesuai dengan kata kunci yang dimasukkan. Ketika kata kunci tidak ditemukan, sistem menampilkan hasil pencarian kosong.

---

## Kesimpulan

Berdasarkan hasil pengujian yang telah dilakukan, seluruh fitur utama aplikasi dapat berjalan sesuai dengan fungsinya. Seluruh proses pengelolaan catatan, mulai dari menambah, mengedit, mengarsipkan, memindahkan ke Trash, mengembalikan catatan, menghapus permanen, hingga pencarian data dapat digunakan tanpa kendala. Data juga tersimpan dengan baik menggunakan database SQLite sehingga tetap tersedia ketika aplikasi dijalankan kembali.