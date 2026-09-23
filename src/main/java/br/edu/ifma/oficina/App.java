package br.edu.ifma.oficina;

import javafx.application.Application;
import javafx.scene.control.TextFormatter;
import java.util.function.UnaryOperator;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Optional;
import java.text.NumberFormat;  //Formatacao de dinheiro - valor em real BRL
import java.util.Locale;

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
        
        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

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
        colValor.setCellValueFactory(new PropertyValueFactory<>("valor")); // Localidade da moeda.
        NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance(
        		new Locale("pt", "BR")
        		);
        colValor.setCellFactory(coluna -> new TableCell<OrdemServico, Double>(){
        	@Override
        	protected void updateItem(Double valor, boolean vazio) {
        		super.updateItem(valor, vazio);
        		if (vazio || valor == null) {
        			setText(null);
        		} else {
        			setText(formatoMoeda.format(valor));
        		}
        	}
        });
        colValor.setPrefWidth(120); //###################
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
        //####### Formatação do campo de valores 
        UnaryOperator<TextFormatter.Change> filtroValor = alteracao -> {

            String novoTexto = alteracao.getControlNewText();

            if (novoTexto.matches("^$|\\d{1,9}([,.]\\d{0,2})?")) {
                return alteracao;
            }

            return null;
        };

        campoValor.setTextFormatter(
                new TextFormatter<>(filtroValor)
        );
     // CABEÇALHO
        Label icone = new Label("🔧");
        icone.getStyleClass().add("logo-icone");

        Label titulo = new Label("MOBILIUM");
        titulo.getStyleClass().add("titulo-principal");

        Label subtitulo = new Label("GESTÃO DE OFICINA MECÂNICA");
        subtitulo.getStyleClass().add("subtitulo");

        VBox textosLogo = new VBox(2, titulo, subtitulo);

        HBox cabecalho = new HBox(12, icone, textosLogo);
     // FORMULÁRIO

     Label tituloFormulario = new Label("Nova ordem de serviço");
     tituloFormulario.getStyleClass().add("titulo-secao");

     Label labelCliente = new Label("Cliente");
     Label labelVeiculo = new Label("Veículo");
     Label labelServico = new Label("Serviço");
     Label labelValor = new Label("Valor");

     labelCliente.getStyleClass().add("label-campo");
     labelVeiculo.getStyleClass().add("label-campo");
     labelServico.getStyleClass().add("label-campo");
     labelValor.getStyleClass().add("label-campo");

     GridPane formulario = new GridPane();

     formulario.setHgap(15);
     formulario.setVgap(8);


     // Cliente e veículo

     formulario.add(labelCliente, 0, 0);
     formulario.add(labelVeiculo, 1, 0);

     formulario.add(campoCliente, 0, 1);
     formulario.add(campoVeiculo, 1, 1);


     // Serviço e valor

     formulario.add(labelServico, 0, 2);
     formulario.add(labelValor, 1, 2);

     formulario.add(campoServico, 0, 3);
     formulario.add(campoValor, 1, 3);


     // Permite que os campos ocupem o espaço disponível

     campoCliente.setMaxWidth(Double.MAX_VALUE);
     campoVeiculo.setMaxWidth(Double.MAX_VALUE);
     campoServico.setMaxWidth(Double.MAX_VALUE);
     campoValor.setMaxWidth(Double.MAX_VALUE);

     GridPane.setHgrow(campoCliente, Priority.ALWAYS);
     GridPane.setHgrow(campoVeiculo, Priority.ALWAYS);
     GridPane.setHgrow(campoServico, Priority.ALWAYS);
     GridPane.setHgrow(campoValor, Priority.ALWAYS);


     // Estilo do botão cadastrar

     botaoCadastrar.getStyleClass().add("botao-principal");

     HBox acoesFormulario = new HBox(10, botaoCadastrar);


     // Card do formulário

     VBox cardFormulario = new VBox(
             15,
             tituloFormulario,
             formulario,
             acoesFormulario
     );

     cardFormulario.getStyleClass().add("card");



     // TABELA


     Label tituloTabela = new Label("Ordens de serviço");
     tituloTabela.getStyleClass().add("titulo-secao");

     botaoEditar.getStyleClass().add("botao-editar");
     botaoExcluir.getStyleClass().add("botao-excluir");

     HBox acoesTabela = new HBox(
             10,
             botaoEditar,
             botaoExcluir
     );

     VBox cardTabela = new VBox(
             15,
             tituloTabela,
             tabela,
             acoesTabela
     );

     cardTabela.getStyleClass().add("card");

     VBox.setVgrow(tabela, Priority.ALWAYS);


     // LAYOUT PRINCIPAL

     VBox raiz = new VBox(
             20,
             cabecalho,
             cardFormulario,
             cardTabela
     );

     raiz.setPadding(new Insets(25));

     // link do css
     
     Scene cena = new Scene(raiz, 900, 650);

     cena.getStylesheets().add(
             getClass().getResource("/styles.css").toExternalForm()
     );

     janela.setTitle("Mobilium - Oficina e Ordens de Serviço");

     janela.setScene(cena);

     janela.setMinWidth(800);
     janela.setMinHeight(600);

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
            avisar("Informe um valor válido.");
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

        NumberFormat moeda = NumberFormat.getCurrencyInstance(
                new Locale("pt", "BR")
        );

        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);

        confirmacao.setTitle("Confirmar exclusão");

        confirmacao.setHeaderText(
                "Deseja excluir esta ordem de serviço?"
        );

        confirmacao.setContentText(
                "Cliente: " + selecionada.getCliente() +
                "\nVeículo: " + selecionada.getVeiculo() +
                "\nServiço: " + selecionada.getServico() +
                "\nValor: " + moeda.format(selecionada.getValor())
        );

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