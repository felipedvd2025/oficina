package br.edu.ifma.oficina;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Optional;

public class App extends Application {

    private final ObservableList<OrdemServico> ordens = FXCollections.observableArrayList();

    private TableView<OrdemServico> tabela;
    private TextField campoCliente;
    private TextField campoVeiculo;
    private TextField campoServico;
    private TextField campoValor;
    private Button botaoCadastrar;

    private OrdemServico emEdicao = null;

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

        campoCliente = new TextField();
        campoCliente.setPromptText("Cliente");

        campoVeiculo = new TextField();
        campoVeiculo.setPromptText("Veículo");

        campoServico = new TextField();
        campoServico.setPromptText("Serviço");

        campoValor = new TextField();
        campoValor.setPromptText("Valor");

        botaoCadastrar = new Button("Cadastrar");
        botaoCadastrar.setOnAction(e -> cadastrar());

        Button botaoEditar = new Button("Editar");
        botaoEditar.setOnAction(e -> editar());

        Button botaoExcluir = new Button("Excluir");
        botaoExcluir.setOnAction(e -> excluir());

        HBox formulario = new HBox(10, campoCliente, campoVeiculo, campoServico,
                                   campoValor, botaoCadastrar, botaoEditar, botaoExcluir);

        VBox raiz = new VBox(10, formulario, tabela);
        raiz.setPadding(new Insets(15));
        
        //link do css
        Scene cena = new Scene(raiz, 880, 420);
        cena.getStylesheets().add(
        		getClass().getResource("/styles.css").toExternalForm()
        );
        

        janela.setTitle("Oficina - Ordens de Serviço");
        janela.setScene(cena);
        janela.show();
    }

    private void cadastrar() {
        String cliente = campoCliente.getText().trim();
        String veiculo = campoVeiculo.getText().trim();
        String servico = campoServico.getText().trim();

        if (cliente.isEmpty() || veiculo.isEmpty() || servico.isEmpty()) {
            avisar("Preencha cliente, veículo e serviço.");
            return;
        }

        double valor;
        try {
            valor = Double.parseDouble(campoValor.getText().trim().replace(",", "."));
        } catch (NumberFormatException ex) {
            avisar("Valor inválido. Use apenas números, como 150.00");
            return;
        }

        if (emEdicao == null) {
            ordens.add(new OrdemServico(cliente, veiculo, servico, valor));
        } else {
            emEdicao.setCliente(cliente);
            emEdicao.setVeiculo(veiculo);
            emEdicao.setServico(servico);
            emEdicao.setValor(valor);
            tabela.refresh();
        }

        limparFormulario();
    }

    private void editar() {
        OrdemServico selecionada = tabela.getSelectionModel().getSelectedItem();

        if (selecionada == null) {
            avisar("Selecione uma linha da tabela para editar.");
            return;
        }

        emEdicao = selecionada;
        campoCliente.setText(selecionada.getCliente());
        campoVeiculo.setText(selecionada.getVeiculo());
        campoServico.setText(selecionada.getServico());
        campoValor.setText(String.valueOf(selecionada.getValor()));
        botaoCadastrar.setText("Salvar");
    }

    private void excluir() {
        OrdemServico selecionada = tabela.getSelectionModel().getSelectedItem();

        if (selecionada == null) {
            avisar("Selecione uma linha da tabela para excluir.");
            return;
        }

        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION,
                "Excluir a ordem de " + selecionada.getCliente() + "?");
        confirmacao.setHeaderText(null);

        Optional<ButtonType> resposta = confirmacao.showAndWait();
        if (resposta.isPresent() && resposta.get() == ButtonType.OK) {
            ordens.remove(selecionada);
            limparFormulario();
        }
    }

    private void limparFormulario() {
        campoCliente.clear();
        campoVeiculo.clear();
        campoServico.clear();
        campoValor.clear();
        emEdicao = null;
        botaoCadastrar.setText("Cadastrar");
        tabela.getSelectionModel().clearSelection();
    }

    private void avisar(String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.WARNING, mensagem);
        alerta.setHeaderText(null);
        alerta.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}