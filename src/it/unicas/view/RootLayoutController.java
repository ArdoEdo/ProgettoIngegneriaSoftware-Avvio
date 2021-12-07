package it.unicas.view;

import it.unicas.MainApp;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class RootLayoutController {
@FXML
    private ImageView imageBeer;
@FXML
private TextField mioLabel;

    // Reference to the main application
    private MainApp mainApp;

    @FXML
    private void initialize(){
        File file = new File("resources/beer.png");
        System.out.println(file.exists());
        Image image = new Image(file.toURI().toString());
        imageBeer.setImage(image);
        initClock();
    }

    public void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;

    }

    @FXML
   private void initClock() {

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
           mioLabel.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    @FXML
    private void handleShowCucina(){
        mainApp.showCucinaOverview();

    }

    @FXML
    private void handleShowOrdine(){
       mainApp.showOrdineOverview();


    }

    @FXML
    private void handleShowBar(){
        mainApp.showBarOverview();


    }
    @FXML
    private void handleShowCassa(){
        mainApp.showCassaOverview();

    }

    @FXML
    private void handleShowAdmin(){
        mainApp.showAdminOverview();

    }






}
