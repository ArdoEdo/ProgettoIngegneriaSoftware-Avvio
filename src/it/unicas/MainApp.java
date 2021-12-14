package it.unicas;

import it.unicas.model.Ordine;
import it.unicas.model.ProdottiOrdinati;
import it.unicas.model.Prodotto;
import it.unicas.model.Tavolo;
import it.unicas.view.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;


public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    /**
     * Lista osservabile di Prodotti
     */
    private ObservableList<Prodotto> prodottoData= FXCollections.observableArrayList();
    private ObservableList<Tavolo> tavoloData= FXCollections.observableArrayList();
    private ObservableList<Ordine> ordineData = FXCollections.observableArrayList();
    private ObservableList<ProdottiOrdinati> prodottiOrdinatiData = FXCollections.observableArrayList();
    private Integer modalitaRestrizioni=0; //gestisce  la presenza di alcolici nel menu ordinabile
    private Integer modalitaEstiva=0;    //gestisce la presenza di tavoli esterni dal locale

    public Integer getModalitaRestrizioni() {
        return modalitaRestrizioni;
    }

    public void setModalitaRestrizioni(Integer modalitaRestrizioni) {
        this.modalitaRestrizioni = modalitaRestrizioni;
    }

    public Integer getModalitaEstiva() {
        return modalitaEstiva;
    }

    public void setModalitaEstiva(Integer modalitaEstiva) {
        this.modalitaEstiva = modalitaEstiva;
    }

    /**
     * Costruttore
     */
    public MainApp(){}
    public ObservableList<Prodotto> getProdottoData(){return prodottoData;}
    public ObservableList<Tavolo> getTavoloData(){return tavoloData;}
    public ObservableList<Ordine> getOrdineData(){return ordineData;}
    public ObservableList<ProdottiOrdinati> getProdottiOrdinatiData(){return prodottiOrdinatiData;}


    @Override
    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("PubApp");
        initRootLayout();
        showOrdineOverview();

        this.primaryStage.setResizable(false);
        this.primaryStage.setWidth(900);
        this.primaryStage.setHeight(550);
        this.primaryStage.show();

    }


    public void initRootLayout()  {
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/MyDash.fxml"));
            rootLayout=(BorderPane) loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            primaryStage.setOnCloseRequest(event -> {
                event.consume();
                handleExit();
            });

            // Give the controller access to the main app.
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);
            System.out.println(this.toString());
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void handleExit() {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Are you sure?");
        alert.setHeaderText("Exit");
        alert.setContentText("Exit from application.");

        ButtonType buttonTypeOne = new ButtonType("Yes");
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne){
            System.exit(0);
        }

    }


    public void showOrdineOverview() {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Ordine.fxml"));
           // AnchorPane anchorPane = (AnchorPane) loader.load();
            rootLayout.setCenter( loader.load());

            // Get controller and set the mainapp reference.
            OrdineOverviewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
            //return false;
        }
    }

    public void showCucinaOverview() {

        try {
            // Load the fxml file and create a new stage for the popup.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Cucina.fxml"));
            rootLayout.setCenter(loader.load());

            // Set the Colleghis into the controller.
            CucinaOverviewController controller = loader.getController();
            controller.setMainApp(this);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showBarOverview() {


        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Bar.fxml"));
            // AnchorPane anchorPane = (AnchorPane) loader.load();
            rootLayout.setCenter( loader.load());

            // Get controller and set the mainapp reference.
            BarOverviewController controller = loader.getController();
            controller.setMainApp(this);



        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public void showCassaOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Cassa.fxml"));
            rootLayout.setCenter( loader.load());

            // Get controller and set the mainapp reference.
            CassaOverviewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();

        }
    }
    public void showAdminOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/AdminOverview.fxml"));
            rootLayout.setCenter(loader.load());

            // Get controller and set the mainapp reference.
            AdminOverviewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public void showAdminOverview2() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/AdminOverview2Test.fxml"));
            rootLayout.setCenter( loader.load());

            // Get controller and set the mainapp reference.
            AdminOverviewController2 controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public void showPagamentoDialog(ProdottiOrdinati prodottiOrdinati){

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Pagamento.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Modalità di pagamento");
            dialogStage.initModality(Modality.WINDOW_MODAL); //con questa modalità non è possibile interagire con la finestra in background
            dialogStage.initOwner(primaryStage); //necessario a causa della modalità anche settare il primary stage come proprietario della finestra
            dialogStage.setResizable(false);

            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            PagamentoOverviewController controller = loader.getController();
            controller.setMainApp(this);
            controller.setProdottiOrdinati_rim(prodottiOrdinati);

            // Set the dialog icon.
            //dialogStage.getIcons().add(new Image("file:resources/images/calendar.png"));

            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void checkRestriction() {
        if(LocalDateTime.now().getMonth().ordinal()>= 5 && LocalDateTime.now().getMonth().ordinal()<=8){
            setModalitaEstiva(1);} //inserire tavoli all'esterno
        if(getModalitaEstiva()==1 && (LocalDateTime.now().getHour()>=22 || LocalDateTime.now().getHour()<=6)) {
            setModalitaRestrizioni(1); //eliminare alcolici dal menu
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }


    public static void main(String[] args) {
        launch(args);
	// write your code here
    }


}
