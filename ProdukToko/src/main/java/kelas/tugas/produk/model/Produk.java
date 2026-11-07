package com.tugas.produk.model;

/**
 * Model data produk.
 * Syarat tugas: atribut private, constructor, getter.
 * Validasi: nama tidak boleh kosong, harga harus > 0.
 */
public class Produk {

    private final String kode;
    private final String nama;
    private final String kategori;
    private final double harga;

    public Produk(String kode, String nama, String kategori, double harga) {
        if (nama == null || nama.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama produk tidak boleh kosong");
        }
        if (harga <= 0) {
            throw new IllegalArgumentException("Harga harus lebih dari nol");
        }
        if (kode == null || kode.trim().isEmpty()) {
            throw new IllegalArgumentException("Kode produk tidak boleh kosong");
        }
        if (kategori == null || kategori.trim().isEmpty()) {
            throw new IllegalArgumentException("Kategori harus dipilih");
        }

        this.kode = kode.trim();
        this.nama = nama.trim();
        this.kategori = kategori.trim();
        this.harga = harga;
    }

    // Nama getter harus persis begini karena dipakai PropertyValueFactory di TableView
    public String getKode() {
        return kode;
    }

    public String getNama() {
        return nama;
    }

    public String getKategori() {
        return kategori;
    }

    public double getHarga() {
        return harga;
    }

    @Override
    public String toString() {
        return String.format("Produk{kode='%s', nama='%s', kategori='%s', harga=%,.0f}",
                kode, nama, kategori, harga);
    }
}
