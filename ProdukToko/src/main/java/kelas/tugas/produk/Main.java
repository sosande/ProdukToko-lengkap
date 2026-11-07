package com.tugas.produk;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Titik masuk aplikasi.
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tugas/produk/main-view.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 820, 620);
        scene.getStylesheets().add(getClass().getResource("/com/tugas/produk/style.css").toExternalForm());

        stage.setTitle("Daftar Produk Toko");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
