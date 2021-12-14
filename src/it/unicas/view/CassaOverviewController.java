package it.unicas.view;

import it.unicas.MainApp;
import it.unicas.model.ProdottiOrdinati;
import it.unicas.model.dao.DAOException;
import it.unicas.model.dao.mysql.ProdottiOrdinatiDAOMySQLImpl;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.util.List;


public class CassaOverviewController {

    @FXML
    private ComboBox locazioneComboBox;
    @FXML
    private TableView<ProdottiOrdinati> cassaTableView;
    @FXML
    private TableColumn<ProdottiOrdinati, String> nomeColumn;
    @FXML
    private TableColumn<ProdottiOrdinati, Integer> quantitaColumn;
    @FXML
    private TableColumn<ProdottiOrdinati, Float> prezzoColumn;
    @FXML
    private TextField inserisciTavolo;
    @FXML
    private Button trovaTavoloButton;
    @FXML
    private Button pagamentoButton;
    @FXML
    private TextField totaleTextField;






    public MainApp mainApp;
    public CassaOverviewController(){};



    @FXML
    private void initialize(){
        locazioneComboBox.getItems().removeAll(locazioneComboBox.getItems());
        locazioneComboBox.getItems().addAll("Bancone","Interno","Esterno");
        cassaTableView.setPlaceholder(new Label("Per cercare gli ordini inserire la locazione e il numero del tavolo"));

        nomeColumn.setCellValueFactory(cellData -> cellData.getValue().nome_prodottoProperty());
        quantitaColumn.setCellValueFactory(cellData-> cellData.getValue().quantita_prodotto_orProperty().asObject());
        prezzoColumn.setCellValueFactory(cellData->cellData.getValue().prezzo_prodottoProperty().asObject());
        pagamentoButton.setDisable(true);
    }

    public void setMainApp(MainApp mainApp) {

        this.mainApp = mainApp;
        mainApp.getProdottiOrdinatiData().clear();
        cassaTableView.setItems(mainApp.getProdottiOrdinatiData());

    }

    @FXML
    private void caricaConto(){
        ProdottiOrdinati tempProdottiOrdinati = new ProdottiOrdinati ();
        tempProdottiOrdinati.setTavolo_locazione_tavolo(locazioneComboBox.getValue().toString());
        tempProdottiOrdinati.setTavolo_numero_tavolo(Integer.parseInt(inserisciTavolo.getText()));
        List<ProdottiOrdinati> list = null;
        Float prezzoTotaleTavolo = Float.valueOf(0);
        try {

            list = ProdottiOrdinatiDAOMySQLImpl.getInstance().select(tempProdottiOrdinati);
            mainApp.getProdottiOrdinatiData().clear();
            mainApp.getProdottiOrdinatiData().addAll(list);

        } catch (DAOException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < mainApp.getProdottiOrdinatiData().size(); i++) {
            prezzoTotaleTavolo += mainApp.getProdottiOrdinatiData().get(i).getPrezzo_prodotto()*mainApp.getProdottiOrdinatiData().get(i).getQuantita_prodotto_or();
        }
        totaleTextField.setText(prezzoTotaleTavolo.toString());
        if(list.size()>0){
            pagamentoButton.setDisable(false);
        }

    }

    @FXML
    private void trovaTavoloButtonPressed(){

        if(inserisciTavolo.getText().isEmpty()){
            Alert mes1 = new Alert(Alert.AlertType.WARNING);
        mes1.setContentText("Non è stato inserito nessun tavolo ");
        mes1.setTitle("Errore");
        mes1.setHeaderText("Ricerca fallita");
        mes1.show();
        }else
        caricaConto();
    }

    @FXML
    private void locazioneSelected(){
        trovaTavoloButton.setDisable(false);
    }

    @FXML
    private void pagamentoButtonPressed(){

        ProdottiOrdinati tempProdottiOrdinati = new ProdottiOrdinati ();
        tempProdottiOrdinati.setTavolo_locazione_tavolo(locazioneComboBox.getValue().toString());
        tempProdottiOrdinati.setTavolo_numero_tavolo(Integer.parseInt(inserisciTavolo.getText()));

        mainApp.showPagamentoDialog(tempProdottiOrdinati);

        totaleTextField.clear();
    }
}
