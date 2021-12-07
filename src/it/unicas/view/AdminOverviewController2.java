package it.unicas.view;
import it.unicas.MainApp;
import it.unicas.model.ProdottiOrdinati;
import it.unicas.model.Prodotto;
import it.unicas.model.Tavolo;
import it.unicas.model.dao.DAOException;
import it.unicas.model.dao.mysql.OrdineDAOMySQLImpl;
import it.unicas.model.dao.mysql.ProdottiOrdinatiDAOMySQLImpl;
import it.unicas.model.dao.mysql.ProdottoDAOMySQLImpl;
import it.unicas.model.dao.mysql.TavoloDAOMySQLImpl;
import javafx.fxml.FXML;

import javafx.scene.control.*;

import java.sql.SQLException;
import java.util.List;

public class AdminOverviewController2 {


    @FXML
    private TextField nome_prod;
    @FXML
    private TextField tipo_prod;
    @FXML
    private TextField prezzo;
    @FXML
    private ComboBox comboBoxLocazione;
    @FXML
    private CheckBox alcolico;
    @FXML
    private ComboBox comboBoxTavolo;
    @FXML
    private Button rimuovi_tav;
    @FXML
    private Button rimuovi_prod;
    @FXML
    private TableView<Prodotto> prodottiTableView;
    @FXML
    private TableColumn<Prodotto, String> nomeColumn;
    @FXML
    private TableColumn<Prodotto, Float> prezzoColumn;

    private MainApp mainApp;


    public void AdminOverviewController2() {
    }


    @FXML
    private void initialize() {

        //inizializzo il comboBox menu

        prodottiTableView.setPlaceholder(new Label("Lista Prodotti"));
        comboBoxLocazione.getItems().clear();
        comboBoxLocazione.getItems().addAll("Bancone", "Interno", "Esterno");
        //inizializzo la tabella ordineTableView con la colonna nomecolumn
        nomeColumn.setCellValueFactory(cellData -> cellData.getValue().nome_prodottoProperty());
        prezzoColumn.setCellValueFactory(cellData -> cellData.getValue().prezzo_prodottoProperty().asObject());

    }

    public void setMainApp(MainApp mainApp) {

        this.mainApp = mainApp;
        //  La tabella si è iscritta
        // sull'observableList prodottoData
        prodottiTableView.setItems(mainApp.getProdottoData());

    }

    @FXML
    private void locazioneSelected() {

        List<Tavolo> list = null;
        Tavolo tavolo = new Tavolo(null, null, comboBoxLocazione.getValue().toString());
        try {
            System.out.println(mainApp);
            list = TavoloDAOMySQLImpl.getInstance().select(tavolo);
            mainApp.getTavoloData().clear();
            mainApp.getTavoloData().addAll(list);
            System.out.println(comboBoxLocazione.getValue().toString());
            System.out.println(list);
            comboBoxTavolo.getItems().clear();
            for (int i = 0; i < list.size(); i++) {
                comboBoxTavolo.getItems().add(list.get(i).getNumero_tavolo());
            }

        } catch (DAOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        comboBoxTavolo.setDisable(false);

    }

    @FXML
    private void AddTavolo() throws DAOException, SQLException {
        String LocTav = comboBoxLocazione.getValue().toString();
        Integer Spin = Integer.parseInt(comboBoxTavolo.getValue().toString());


        Tavolo tempTavolo = new Tavolo(Spin, null, LocTav);
        try {
            TavoloDAOMySQLImpl.getInstance().insert(tempTavolo);
        } catch (DAOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        locazioneSelected();
    }

    @FXML
    private void tavoloSelected() {

        rimuovi_tav.setDisable(false);

    }

    @FXML
    private void RimTavolo() {
        String LocTav = comboBoxLocazione.getValue().toString();
        Integer Spin = Integer.parseInt(comboBoxTavolo.getValue().toString());

        Tavolo tempTavolo = new Tavolo(Spin, null, LocTav);

        Alert mes = new Alert(Alert.AlertType.INFORMATION);
        System.out.println(tempTavolo);

        try {
            TavoloDAOMySQLImpl.getInstance().delete(tempTavolo);

        } catch (DAOException e) {
            e.printStackTrace();

        } catch (SQLException e) {
            Alert mes1 = new Alert(Alert.AlertType.WARNING);

            e.printStackTrace();
            mes1.setContentText("ERRORE! ORDINI IN CORSO PER IL TAVOLO DA ELIMINARE");
            mes1.setTitle("ERRORE!");
            mes1.show();

        }
        locazioneSelected();

    }

    @FXML
    private void rimuoviButtonMenu() {

        int selected_index = prodottiTableView.getSelectionModel().getSelectedIndex();
        System.out.println(selected_index);
        System.out.println(mainApp.getProdottoData().get(selected_index).getNome_prodotto());

        Prodotto tempProdotto = new Prodotto();
        if (selected_index != -1) {
            tempProdotto.setId_prodotto(mainApp.getProdottoData().get(selected_index).getId_prodotto());
            tempProdotto.setNome_prodotto(mainApp.getProdottoData().get(selected_index).getNome_prodotto());
            tempProdotto.setTipo_prodotto(mainApp.getProdottoData().get(selected_index).getTipo_prodotto());
            tempProdotto.setAlcolico(mainApp.getProdottoData().get(selected_index).isAlcolico());
            tempProdotto.setPrezzo_prodotto(mainApp.getProdottoData().get(selected_index).getPrezzo_prodotto());
            tempProdotto.setQuantita_prodotto(mainApp.getProdottoData().get(selected_index).getQuantita_prodotto());

            System.out.println(tempProdotto);
            try {
                System.out.println("TRY");
                ProdottoDAOMySQLImpl.getInstance().delete(tempProdotto);

            } catch (DAOException e) {
                e.printStackTrace();

            } catch (SQLException e) {

            }

        }
        if (prodottiTableView.getItems().size() == 0)
            rimuovi_prod.setDisable(true);

    }


    @FXML
    private void aggiungiButtonMenu() {


        Alert mes = new Alert(Alert.AlertType.INFORMATION);

        String nomeProd = nome_prod.getText();
        String tipoProd = tipo_prod.getText();
        String prezzoProd = prezzo.getText();
        // Integer Alcool = alcolico.isSelected();

        Prodotto tempProdotto = new Prodotto(null, nomeProd, tipoProd, 1, Float.parseFloat(prezzoProd), 1);
        System.out.println(tempProdotto);
        if (nomeProd.length() != 0 && tipoProd.length() != 0 && prezzoProd.length() != 0) {

            mes.setContentText("Premere 'Carica' per aggiornare la lista");
            mes.setTitle("Inserimento prodotto effettuato");
            mes.show();
            try {
                ProdottoDAOMySQLImpl.getInstance().insert(tempProdotto);

            } catch (DAOException e) {
                e.printStackTrace();

            } catch (SQLException e) {
                Alert mes1 = new Alert(Alert.AlertType.WARNING);

            }
        } else {
            mes.setContentText("Completare tutti i campi di 'Aggiungi prodotto'!");
            mes.setTitle("Errore");
            mes.show();

        }


    }


    @FXML
    private void caricaBut() {
        Prodotto temp = new Prodotto(0, "", "", 1, .0f, 1);
        List<Prodotto> list = null;
        try {
            System.out.println(mainApp);
            list = ProdottoDAOMySQLImpl.getInstance().select(temp);
            mainApp.getProdottoData().clear();
            mainApp.getProdottoData().addAll(list);

        } catch (DAOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
