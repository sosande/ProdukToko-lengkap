package com.tugas.produk.controller;

import com.tugas.produk.model.Produk;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller halaman utama (main-view.fxml).
 * Semua @FXML di bawah harus sama persis dengan fx:id di FXML / Scene Builder.
 */
public class ProdukController {

    // ===== Komponen form tambah =====
    @FXML private TextField txtKode;
    @FXML private TextField txtNama;
    @FXML private ComboBox<String> cmbKategori;
    @FXML private TextField txtHarga;
    @FXML private Button btnTambah;

    // ===== Komponen pencarian =====
    @FXML private TextField txtCari;
    @FXML private Button btnCari;
    @FXML private Button btnReset;

    // ===== Tabel =====
    @FXML private TableView<Produk> tblProduk;
    @FXML private TableColumn<Produk, String> colKode;
    @FXML private TableColumn<Produk, String> colNama;
    @FXML private TableColumn<Produk, String> colKategori;
    @FXML private TableColumn<Produk, Double> colHarga;

    // ===== Lainnya =====
    @FXML private Button btnDetail;
    @FXML private Label lblPesan;

    // Data disimpan selama aplikasi berjalan (tanpa database)
    private final ObservableList<Produk> daftarProduk = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        cmbKategori.setItems(FXCollections.observableArrayList("Makanan", "Minuman"));
        setupTabel();
        tblProduk.setItems(daftarProduk);
    }

    private void setupTabel() {
        colKode.setCellValueFactory(new PropertyValueFactory<>("kode"));
        colNama.setCellValueFactory(new PropertyValueFactory<>("nama"));
        colKategori.setCellValueFactory(new PropertyValueFactory<>("kategori"));
        colHarga.setCellValueFactory(new PropertyValueFactory<>("harga"));

        // Tampilkan harga dengan format rupiah, nilai aslinya tetap double
        colHarga.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Double harga, boolean empty) {
                super.updateItem(harga, empty);
                setText(empty || harga == null ? null : String.format("Rp %,.0f", harga));
            }
        });
    }

    @FXML
    private void onTambah() {
        String kode = txtKode.getText();
        String nama = txtNama.getText();
        String kategori = cmbKategori.getValue();
        String teksHarga = txtHarga.getText() == null ? "" : txtHarga.getText().trim();

        if (kategori == null) {
            tampilkanPesan("Kategori harus dipilih", true);
            return;
        }

        double harga;
        try {
            harga = Double.parseDouble(teksHarga);
        } catch (NumberFormatException e) {
            tampilkanPesan("Harga harus berupa angka", true);
            return;
        }

        Produk produk;
        try {
            produk = new Produk(kode, nama, kategori, harga);
        } catch (IllegalArgumentException e) {
            tampilkanPesan(e.getMessage(), true);
            return;
        }

        daftarProduk.add(produk);
        bersihkanForm();
        tampilkanPesan("Produk berhasil ditambahkan", false);
    }

    @FXML
    private void onCari() {
        String kataKunci = txtCari.getText() == null ? "" : txtCari.getText().trim().toLowerCase();

        if (kataKunci.isEmpty()) {
            tblProduk.setItems(daftarProduk);
            return;
        }

        ObservableList<Produk> hasil = FXCollections.observableArrayList();
        for (Produk p : daftarProduk) {
            if (p.getNama().toLowerCase().contains(kataKunci)) {
                hasil.add(p);
            }
        }
        tblProduk.setItems(hasil);

        if (hasil.isEmpty()) {
            tampilkanPesan("Produk tidak ditemukan", true);
        } else {
            tampilkanPesan(hasil.size() + " produk ditemukan", false);
        }
    }

    @FXML
    private void onReset() {
        txtCari.clear();
        tblProduk.setItems(daftarProduk);
        lblPesan.setText("");
    }

    @FXML
    private void onLihatDetail() {
        Produk produkTerpilih = tblProduk.getSelectionModel().getSelectedItem();
        if (produkTerpilih == null) {
            tampilkanPesan("Pilih produk dulu", true);
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tugas/produk/detail-view.fxml"));
            Parent root = loader.load();

            DetailController detailController = loader.getController();
            detailController.setProduk(produkTerpilih); // kirim OBJEK, bukan teks

            Scene scene = new Scene(root, 380, 300);
            scene.getStylesheets().add(getClass().getResource("/com/tugas/produk/style.css").toExternalForm());

            Stage stage = new Stage();
            stage.setTitle("Detail Produk");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            tampilkanPesan("Gagal membuka detail: " + e.getMessage(), true);
        }
    }

    private void bersihkanForm() {
        txtKode.clear();
        txtNama.clear();
        txtHarga.clear();
        cmbKategori.getSelectionModel().clearSelection();
        cmbKategori.setValue(null);
    }

    private void tampilkanPesan(String pesan, boolean error) {
        lblPesan.setText(pesan);
        lblPesan.getStyleClass().removeAll("pesan-error", "pesan-sukses");
        lblPesan.getStyleClass().add(error ? "pesan-error" : "pesan-sukses");
    }
}
