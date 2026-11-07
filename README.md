# Produk Toko (JavaFX + FXML + CSS)

Aplikasi desktop sederhana untuk mengelola daftar produk toko: menambah produk, mencari berdasarkan nama, dan melihat detail produk. Data disimpan di memori selama aplikasi berjalan (tanpa database), jadi akan hilang saat aplikasi ditutup.

## Kebutuhan

- JDK 17 atau lebih baru
- Maven (sudah terpasang di NetBeans)
- Koneksi internet saat pertama kali menjalankan, untuk mengunduh JavaFX dan plugin Maven
- Scene Builder (opsional, untuk mengedit tampilan FXML)

## Cara Menjalankan

**Lewat terminal**

```bash
mvn javafx:run
```

**Lewat NetBeans**

1. Klik kanan project `produk-toko` → **Run Maven** → **Other Goals...**
2. Isi **Goals** dengan `javafx:run`, lalu klik **OK**.
3. Agar tombol Run (F6) juga memakai goal ini: klik kanan project → **Run Maven** → **Edit Project Goals...** → pilih **Run project** → isi **Execute Goals** dengan `javafx:run`.

## Cara Menggunakan Aplikasi

### 1. Menambah produk

1. Isi **Kode Produk** (mis. `P001`).
2. Isi **Nama Produk** (mis. `Teh Botol`).
3. Pilih **Kategori**: `Makanan` atau `Minuman`.
4. Isi **Harga** dengan angka saja (mis. `5000`).
5. Klik **Tambah Produk**.

Jika data valid, produk muncul di tabel, form dikosongkan, dan tampil pesan hijau "Produk berhasil ditambahkan".

Aturan validasi (pesan error tampil dengan warna merah):

| Kondisi | Pesan |
|---|---|
| Nama kosong | Nama produk tidak boleh kosong |
| Harga ≤ 0 | Harga harus lebih dari nol |
| Harga bukan angka | Harga harus berupa angka |
| Kategori belum dipilih | Kategori harus dipilih |
| Kode kosong | Kode produk tidak boleh kosong |

### 2. Mencari produk

1. Ketik sebagian nama produk di kolom pencarian (huruf besar/kecil tidak berpengaruh).
2. Klik **Cari**. Tabel hanya menampilkan produk yang namanya cocok.
3. Jika tidak ada yang cocok, tampil pesan "Produk tidak ditemukan".
4. Klik **Reset** untuk mengosongkan kolom cari dan menampilkan semua produk kembali.

Jika kolom cari kosong lalu klik **Cari**, semua produk ditampilkan.

### 3. Melihat detail produk

1. Klik satu baris produk di tabel.
2. Klik **Lihat Detail**. Jendela baru menampilkan kode, nama, kategori, dan harga (format `Rp 5.000`).
3. Klik **Tutup** untuk menutup jendela detail.

Jika belum ada baris yang dipilih, tampil pesan "Pilih produk dulu".

## Cara Kerja (Struktur Kode)

```
ProdukToko/
├── pom.xml
└── src/main/
    ├── java/com/tugas/produk/
    │   ├── Main.java
    │   ├── model/Produk.java
    │   └── controller/
    │       ├── ProdukController.java
    │       └── DetailController.java
    └── resources/com/tugas/produk/
        ├── main-view.fxml
        ├── detail-view.fxml
        └── style.css
```

Aplikasi memakai pola **MVC**:

| Bagian | File | Peran |
|---|---|---|
| Model | `Produk.java` | Menyimpan data produk (atribut private + getter) dan memvalidasi nama serta harga di constructor. |
| View | `*.fxml`, `style.css` | Tampilan antarmuka (FXML) dan gayanya (CSS). |
| Controller | `ProdukController`, `DetailController` | Menangani aksi pengguna dan menghubungkan View dengan Model. |
| Entry point | `Main.java` | Memuat `main-view.fxml`, memasang stylesheet, dan menampilkan jendela. |

### Alur program

1. **Start.** `Main` memuat `main-view.fxml`. FXML menunjuk `ProdukController` lewat `fx:controller`.
2. **Inisialisasi.** `ProdukController.initialize()` mengisi combo kategori, menghubungkan kolom tabel dengan atribut `Produk`, lalu memasang `daftarProduk` ke tabel.
3. **Tambah.** `onTambah()` membaca isi form, membuat objek `Produk` di dalam try-catch, lalu menambahkannya ke `daftarProduk`. Tabel otomatis ikut diperbarui karena `daftarProduk` adalah `ObservableList`.
4. **Cari.** `onCari()` membuat list baru berisi produk yang namanya mengandung kata kunci. `onReset()` mengembalikan tabel ke `daftarProduk`.
5. **Detail.** `onLihatDetail()` memuat `detail-view.fxml`, mengambil `DetailController` dari loader, lalu memanggil `setProduk(produk)` untuk mengirim **objek** produk. Controller detail mengisi label-labelnya dari objek itu.

### Hubungan FXML dan Controller

- Setiap `fx:id` di FXML harus sama persis dengan nama field `@FXML` di controller (termasuk huruf besar/kecilnya).
- Setiap `onAction="#namaMetode"` di FXML harus punya metode `@FXML` dengan nama yang sama.
- Nama getter di `Produk` (`getKode`, `getNama`, `getKategori`, `getHarga`) dipakai `PropertyValueFactory` untuk mengisi kolom tabel, jadi namanya tidak boleh diubah.

## Mengedit Tampilan dengan Scene Builder

1. Di NetBeans: **Tools → Options → Java → JavaFX**, lalu isi **Scene Builder Home**.
2. Klik kanan file `.fxml` → **Open in Scene Builder**.
3. Edit tampilan, simpan dengan **Ctrl+S**, lalu jalankan ulang aplikasi.

## Pemecahan Masalah

| Masalah | Penyebab / Solusi |
|---|---|
| Dialog "Select Main Class" kosong saat Run | Jalankan dengan goal `javafx:run`, jangan dengan Run biasa. |
| "JavaFX runtime components are missing" | Aplikasi dijalankan tanpa plugin JavaFX. Gunakan `mvn javafx:run`. |
| `NullPointerException` saat start | `fx:id` di FXML tidak sama dengan nama field `@FXML` di controller. |
| Ikon merah di file controller | Dependency belum terunduh. Klik kanan project → **Clean and Build** dengan internet aktif. |
| Perubahan FXML/CSS tidak terlihat | Simpan file, lalu jalankan ulang aplikasi. |
