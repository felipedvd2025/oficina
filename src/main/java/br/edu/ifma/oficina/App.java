package br.edu.ifma.oficina;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage janela) {
        Label texto = new Label("Oficina - Teste");
        Scene cena = new Scene(new StackPane(texto), 400, 200);

        janela.setTitle("Oficina");
        janela.setScene(cena);
        janela.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}