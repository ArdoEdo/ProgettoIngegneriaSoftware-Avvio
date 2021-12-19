package it.unicas.view;

import it.unicas.MainApp;
import it.unicas.model.Ordine;
import it.unicas.model.ProdottiOrdinati;
import it.unicas.model.Prodotto;
import it.unicas.model.dao.DAOException;
import it.unicas.model.dao.mysql.ProdottiOrdinatiDAOMySQLImpl;
import it.unicas.model.dao.mysql.ProdottoDAOMySQLImpl;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.util.List;

public class BarOverviewController {

    @FXML
    private TableView<ProdottiOrdinati> barTableView;
    @FXML
    private TableColumn<ProdottiOrdinati, String> nomeColumn;
    @FXML
    private TableColumn<ProdottiOrdinati, Integer> quantitaColumn;
    @FXML
    private TableColumn<ProdottiOrdinati, Integer> numeroTavoloColumn;
    @FXML
    private TableColumn<ProdottiOrdinati, String> locazioneTavoloColumn;
    @FXML
    private Button preparatoButton;


    private MainApp mainApp;
    public BarOverviewController(){}

    @FXML
    private void initialize(){
        barTableView.setPlaceholder(new Label("Non sono stati effettuati ordini"));

        nomeColumn.setCellValueFactory(cellData -> cellData.getValue().nome_prodottoProperty());
        quantitaColumn.setCellValueFactory(cellData-> cellData.getValue().quantita_prodotto_orProperty().asObject());
        numeroTavoloColumn.setCellValueFactory(cellData-> cellData.getValue().tavolo_numero_tavoloProperty().asObject());

        locazioneTavoloColumn.setCellValueFactory(cellData -> cellData.getValue().tavolo_locazione_tavoloProperty());
    }

    public void setMainApp(MainApp mainApp) {

        this.mainApp = mainApp;
        barTableView.setItems(mainApp.getProdottiOrdinatiData());
        caricaOrdineBar();

    }

    @FXML
    private void caricaOrdineBar() {
        ProdottiOrdinati tempProdottiOrdinati = new ProdottiOrdinati();
        tempProdottiOrdinati.setTipo_prodotto("bevanda");
        System.out.println(tempProdottiOrdinati.getTipo_prodotto());
        //caricaButton.setDisable(true);
        List<ProdottiOrdinati> list = null;
        try {

            list = ProdottiOrdinatiDAOMySQLImpl.getInstance().join(tempProdottiOrdinati);
            mainApp.getProdottiOrdinatiData().clear();
            mainApp.getProdottiOrdinatiData().addAll(list);
            System.out.println(list);

        } catch (DAOException e) {
            e.printStackTrace();
        }
        if(list.size()>0){
            preparatoButton.setDisable(false);
        }

    }

    @FXML
    private void preparatoButtonPressed(){
        int selected_index = barTableView.getSelectionModel().getSelectedIndex();
        ProdottiOrdinati tempProdottiOrdinati = new ProdottiOrdinati();
        if(selected_index != -1) {
            tempProdottiOrdinati.setId_ordine(mainApp.getProdottiOrdinatiData().get(selected_index).getId_ordine());
            try {
                ProdottiOrdinatiDAOMySQLImpl.getInstance().update(tempProdottiOrdinati);
            } catch (DAOException e) {
                e.printStackTrace();
            }

            barTableView.getItems().remove(selected_index);

        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Errore");
            alert.setHeaderText("Nessun ordine selezionato");
            alert.setContentText("Selezionare l'ordine e poi premere 'Preparato'");
            alert.showAndWait();

        }
        if(barTableView.getItems().size()==0)
            barTableView.setDisable(true);
    }


}
