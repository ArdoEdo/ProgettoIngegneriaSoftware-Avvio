package it.unicas.view;

import it.unicas.MainApp;
import it.unicas.model.Ordine;
import it.unicas.model.Prodotto;
import it.unicas.model.Tavolo;
import it.unicas.model.dao.DAOException;
import it.unicas.model.dao.mysql.OrdineDAOMySQLImpl;
import it.unicas.model.dao.mysql.ProdottoDAOMySQLImpl;
import it.unicas.model.dao.mysql.TavoloDAOMySQLImpl;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Duration;

import java.sql.SQLException;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;

public class OrdineOverviewController {
    @FXML
    private TableView<Prodotto> ordineTableView;
    @FXML
    private TableView<Prodotto> riepilogoOrdine;
    @FXML
    private TableColumn<Prodotto, String> nomeColumn;
    @FXML
    private TableColumn<Prodotto, Float> prezzoColumn;
    @FXML
    private TableColumn<Prodotto, String> dxNomeColumn;
    @FXML
    private TableColumn<Prodotto, Integer> quantitaColumn;
    @FXML
    private ComboBox comboBoxLocazione;
    @FXML
    private ComboBox comboBoxTavolo;
    @FXML
    private Button caricaButton;
    @FXML
    private Button aggiungiButton;
    @FXML
    private Button rimuoviButton;
    @FXML
    private Button ordinaButton;




private MainApp mainApp;

//chiamata prima del metodo initialize
public OrdineOverviewController(){
}

//chiamata dopo il caricamento dell fxml
    @FXML
    private void initialize(){


        ordineTableView.setPlaceholder(new Label("Caricare il menu"));
        riepilogoOrdine.setPlaceholder(new Label("I prodotti aggiunti verranno visualizzati qui"));

        //inizializzo la tabella ordineTableView con la colonna nomecolumn
        nomeColumn.setCellValueFactory(cellData -> cellData.getValue().nome_prodottoProperty());
        dxNomeColumn.setCellValueFactory(cellData -> cellData.getValue().nome_prodottoProperty());
        prezzoColumn.setCellValueFactory(cellData -> cellData.getValue().prezzo_prodottoProperty().asObject());
        quantitaColumn.setCellValueFactory(cellData->cellData.getValue().quantita_prodottoProperty().asObject());



    }

    public void setMainApp(MainApp mainApp) {

        this.mainApp = mainApp;

        //  La tabella si è iscritta
        // sull'observableList prodottoData
        ordineTableView.setItems(mainApp.getProdottoData());

        //inizializzo il comboBox menu
        comboBoxLocazione.getItems().clear();
        mainApp.checkRestriction();
        if(mainApp.getModalitaEstiva()==1)
            comboBoxLocazione.getItems().addAll("Bancone","Interno","Esterno");
        else
            comboBoxLocazione.getItems().addAll("Bancone","Interno");
}

@FXML
private void caricaMenu() {

    Prodotto tempProdotto = new Prodotto(null, "", "", 0, .0f,1);
    //(Integer id_prodotto,String nome_prodotto, String tipo_prodotto,Integer alcolico, Float prezzo_prodotto, Integer quantita_prodotto)
    mainApp.checkRestriction();
    tempProdotto.setAlcolico(mainApp.getModalitaRestrizioni());
    caricaButton.setDisable(true);
    List<Prodotto> list = null;
    try {
        System.out.println(mainApp);
        list = ProdottoDAOMySQLImpl.getInstance().select(tempProdotto);
        mainApp.getProdottoData().clear();
        mainApp.getProdottoData().addAll(list);

    } catch (DAOException e) {
        e.printStackTrace();
    }
}
    @FXML
    private void aggiungiButtonPressed() {


        int selected_index = ordineTableView.getSelectionModel().getSelectedIndex();


        //se selezioni un item effettua l'aggiunta
        if(selected_index!=-1) {
            riepilogoOrdine.getItems().add(ordineTableView.getItems().get(selected_index));

            if(riepilogoOrdine.getItems().size()>0 && comboBoxTavolo.getValue()!=null)
                ordinaButton.setDisable(false);

            int sizeRiepilogoOrdine = riepilogoOrdine.getItems().size();
            System.out.println(riepilogoOrdine.getItems().get(sizeRiepilogoOrdine - 1).getNome_prodotto());

            for( int i=0;i<sizeRiepilogoOrdine;++i)  {
                if (sizeRiepilogoOrdine > 1 && i!=sizeRiepilogoOrdine-1){
                    if (riepilogoOrdine.getItems().get(sizeRiepilogoOrdine - 1).getNome_prodotto() ==
                            riepilogoOrdine.getItems().get(i).getNome_prodotto()) {
                        riepilogoOrdine.getItems().remove(sizeRiepilogoOrdine - 1);
                        riepilogoOrdine.getItems().get(i).setQuantita_prodotto( riepilogoOrdine.getItems().get(i).getQuantita_prodotto()+1);
                        System.out.println(riepilogoOrdine.getColumns().get(1).getCellData(i));
                        break;
                    }}
            }
        }
    }

    @FXML
    private void rimuoviButtonPressed(){

        int selected_index = riepilogoOrdine.getSelectionModel().getSelectedIndex();
        if(selected_index != -1) {
            if(riepilogoOrdine.getItems().get(selected_index).getQuantita_prodotto() == 1){
                riepilogoOrdine.getItems().remove(selected_index);
            }
            else {
                riepilogoOrdine.getItems().get(selected_index).setQuantita_prodotto( riepilogoOrdine.getItems().get(selected_index).getQuantita_prodotto()-1);
            }
        }
        if(riepilogoOrdine.getItems().size()==0)
            ordinaButton.setDisable(true);
    }

    @FXML
    private void locazioneSelected(){

        List<Tavolo> list = null;
        Tavolo tavolo = new Tavolo(null, 0, comboBoxLocazione.getValue().toString());

        try {
            System.out.println(mainApp);
            list = TavoloDAOMySQLImpl.getInstance().select(tavolo);
            mainApp.getTavoloData().clear();
            mainApp.getTavoloData().addAll(list);
            System.out.println(comboBoxLocazione.getValue().toString());
            System.out.println(list);
            comboBoxTavolo.getItems().clear();
            for( int i = 0; i<list.size(); i++) {
                comboBoxTavolo.getItems().add(list.get(i).getNumero_tavolo());
            }

        } catch (DAOException e) {
            e.printStackTrace();
        }

        comboBoxTavolo.setDisable(false);

    }

    @FXML
    private void tavoloSelected(){

    if (riepilogoOrdine.getItems().size()>0)
    ordinaButton.setDisable(false);


}

    @FXML
    private void inviaOrdine() {

        Ordine tempListOrdine = new Ordine();
        for (int i = 0; i < riepilogoOrdine.getItems().size(); i++) {
                System.out.println(Integer.parseInt(comboBoxTavolo.getValue().toString()));
                tempListOrdine.setTavolo_numero_tavolo(Integer.parseInt(comboBoxTavolo.getValue().toString()));
                tempListOrdine.setTavolo_locazione_tavolo(comboBoxLocazione.getValue().toString());

                for(int j = 0; j<mainApp.getProdottoData().size();j++){
                    if(mainApp.getProdottoData().get(j).getNome_prodotto() == riepilogoOrdine.getItems().get(i).getNome_prodotto()) {
                        tempListOrdine.setProdotto_id_prodotto(mainApp.getProdottoData().get(j).getId_prodotto());
                        System.out.println(mainApp.getProdottoData().get(j).getNome_prodotto());
                        break;
                    }
                }
                tempListOrdine.setQuantita_prodotto_or(riepilogoOrdine.getItems().get(i).getQuantita_prodotto());
                tempListOrdine.setOrdine_preparato(0);
                System.out.println(tempListOrdine);

            try {
                OrdineDAOMySQLImpl.getInstance().insert(tempListOrdine);
            } catch (DAOException e) {
                e.printStackTrace();
            }
            mainApp.getOrdineData().add(tempListOrdine);

            riepilogoOrdine.getItems().get(i).setQuantita_prodotto(1);

        }
        riepilogoOrdine.getItems().clear();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle("Ordinato!");
        alert.setHeaderText("Ordine effettuato con successo");
        alert.setContentText("Ora è possibile effettuare un nuovo ordine");

        alert.showAndWait();

        ordinaButton.setDisable(true);

    }

}
