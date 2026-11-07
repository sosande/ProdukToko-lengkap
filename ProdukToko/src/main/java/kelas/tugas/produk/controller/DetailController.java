package com.tugas.produk.controller;

import com.tugas.produk.model.Produk;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Controller halaman detail (detail-view.fxml).
 * Data TIDAK ditulis langsung di sini, tetapi diterima lewat setProduk().
 */
public class DetailController {

    @FXML private Label lblKode;
    @FXML private Label lblNama;
    @FXML private Label lblKategori;
    @FXML private Label lblHarga;
    @FXML private Button btnTutup;

    private Produk produk;

    /** Dipanggil oleh ProdukController setelah FXML di-load. */
    public void setProduk(Produk produk) {
        this.produk = produk;

        lblKode.setText(produk.getKode());
        lblNama.setText(produk.getNama());
        lblKategori.setText(produk.getKategori());
        lblHarga.setText(String.format("Rp %,.0f", produk.getHarga()));
    }

    @FXML
    private void onTutup() {
        Stage stage = (Stage) btnTutup.getScene().getWindow();
        stage.close();
    }
}
