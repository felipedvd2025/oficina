package br.edu.ifma.oficina;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    private final ObservableList<OrdemServico> ordens = FXCollections.observableArrayList();

    private TableView<OrdemServico> tabela;

    @Override
    public void start(Stage janela) {

        tabela = new TableView<>();

        TableColumn<OrdemServico, Integer> colNumero = new TableColumn<>("Nº");
        colNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        colNumero.setPrefWidth(60);

        TableColumn<OrdemServico, String> colCliente = new TableColumn<>("Cliente");
        colCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        colCliente.setPrefWidth(160);

        TableColumn<OrdemServico, String> colVeiculo = new TableColumn<>("Veículo");
        colVeiculo.setCellValueFactory(new PropertyValueFactory<>("veiculo"));
        colVeiculo.setPrefWidth(160);

        TableColumn<OrdemServico, String> colServico = new TableColumn<>("Serviço");
        colServico.setCellValueFactory(new PropertyValueFactory<>("servico"));
        colServico.setPrefWidth(220);

        TableColumn<OrdemServico, Double> colValor = new TableColumn<>("Valor");
        colValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        colValor.setPrefWidth(100);

        tabela.getColumns().add(colNumero);
        tabela.getColumns().add(colCliente);
        tabela.getColumns().add(colVeiculo);
        tabela.getColumns().add(colServico);
        tabela.getColumns().add(colValor);

        tabela.setItems(ordens);
        tabela.setPlaceholder(new Label("Nenhuma ordem cadastrada."));

        VBox raiz = new VBox(10, tabela);
        raiz.setPadding(new Insets(15));

        janela.setTitle("Oficina - Ordens de Serviço");
        janela.setScene(new Scene(raiz, 780, 420));
        janela.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}