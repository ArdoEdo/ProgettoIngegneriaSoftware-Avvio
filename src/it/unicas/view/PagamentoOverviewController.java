package it.unicas.view;

import it.unicas.MainApp;
import it.unicas.model.ProdottiOrdinati;
import it.unicas.model.dao.DAOException;
import it.unicas.model.dao.mysql.ProdottiOrdinatiDAOMySQLImpl;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Random;

public class PagamentoOverviewController {

    @FXML
    private TableView<ProdottiOrdinati> pagamentoTableView;
    @FXML
    private TableColumn<ProdottiOrdinati, String> nomeColumn;
    @FXML
    private TableColumn<ProdottiOrdinati, Integer> quantitaColumn;
    @FXML
    private TableColumn<ProdottiOrdinati, Float> prezzoColumn;
    @FXML
    private TextField totaleTextField;

    public PagamentoOverviewController() {
    }

    ;
    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public MainApp mainApp;
    private ProdottiOrdinati temp_prodottiOrdinati;

    @FXML
    private void initialize() {
        nomeColumn.setCellValueFactory(cellData -> cellData.getValue().nome_prodottoProperty());
        quantitaColumn.setCellValueFactory(cellData -> cellData.getValue().quantita_prodotto_orProperty().asObject());
        prezzoColumn.setCellValueFactory(cellData -> cellData.getValue().prezzo_prodottoProperty().asObject());
    }

    public void setMainApp(MainApp mainApp) {

        this.mainApp = mainApp;

        pagamentoTableView.setItems(mainApp.getProdottiOrdinatiData());
        Float prezzoTotaleTavolo = Float.valueOf(0);
        for (int i = 0; i < mainApp.getProdottiOrdinatiData().size(); i++) {
            prezzoTotaleTavolo += mainApp.getProdottiOrdinatiData().get(i).getPrezzo_prodotto() * mainApp.getProdottiOrdinatiData().get(i).getQuantita_prodotto_or();
        }
        totaleTextField.setText(prezzoTotaleTavolo.toString());
    }


    public void setProdottiOrdinati_rim(ProdottiOrdinati prodottiOrdinati) {
        temp_prodottiOrdinati = prodottiOrdinati;
    }


    @FXML
    private void pagamentoContantiPressed() {
        try {
            ProdottiOrdinatiDAOMySQLImpl.getInstance().delete(temp_prodottiOrdinati);
            mainApp.getProdottiOrdinatiData().clear();
        } catch (DAOException e) {
            e.printStackTrace();
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle("Pagamento effettuato");
        alert.setHeaderText("Pagamento in contanti eseguito");
        alert.setContentText("Grazie e Arrivederci!");

        alert.showAndWait();

        totaleTextField.clear();
    }

    @FXML
    private void pagamentoCartaPressed() {

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        PasswordField text1 = new PasswordField();

        Button button = new Button("Conferma");
        final String[] pin = new String[1];

        Label label1 = new Label(" Inserire il pin della carta");
        Label label2 = new Label("PIN:");

        GridPane layout = new GridPane();

        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.setVgap(5);
        layout.setHgap(5);

        layout.add(text1, 1, 1);
        layout.add(button, 1, 3);
        layout.add(label1, 1, 0);
        layout.add(label2, 0, 1);
        Scene scene = new Scene(layout, 250, 150);

        button.setOnAction(event -> {
            Random random = new Random();
            Double rand_number;
            rand_number = random.nextDouble();
            System.out.println("Numero randomico generato" + rand_number);
            pin[0] = text1.getText();
            System.out.println("Premuto il tasto conferma, il pin è: " + pin[0]);
            System.out.println(rand_number<1);
            System.out.println(pin[0].equals("1234"));
            System.out.println(pin[0]);

            if(pin[0].equals("1234") && rand_number>0.5) {
                try {
                    ProdottiOrdinatiDAOMySQLImpl.getInstance().delete(temp_prodottiOrdinati);
                    mainApp.getProdottiOrdinatiData().clear();
                } catch (DAOException e) {
                    e.printStackTrace();
                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initOwner(this.dialogStage);
                alert.setTitle("Pagamento effettuato");
                alert.setHeaderText("Pagamento con carta eseguito");
                alert.setContentText("Grazie e Arrivederci!");

                alert.showAndWait();
                stage.close();

                totaleTextField.clear();

            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(this.dialogStage);
                alert.setTitle("Pagamento respinto");
                alert.setHeaderText("Pagamento con carta fallito");
                alert.setContentText("Reinserire il pin o usare un altro metodo di pagamento");
                alert.showAndWait();

            }
        });
        stage.setTitle("Pagamento con carta");
        stage.setScene(scene);
        stage.showAndWait();
    }
}
