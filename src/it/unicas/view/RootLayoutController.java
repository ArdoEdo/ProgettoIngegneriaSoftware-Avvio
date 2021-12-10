package it.unicas.view;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import it.unicas.MainApp;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import javax.swing.text.IconView;
import java.io.File;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class RootLayoutController {
@FXML
    private ImageView imageBeer;
@FXML
private Button restrictionWarning;
@FXML
private FontAwesomeIconView warningIcon;
@FXML
private Label oraCorrenteText;

    // Reference to the main application
    private MainApp mainApp;

    @FXML
    private void initialize(){
        File file = new File("resources/beer.png");
        System.out.println(file.exists());
        Image image = new Image(file.toURI().toString());
        imageBeer.setImage(image);

        //clock
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            if(currentTime.getMinute()<10)
            oraCorrenteText.setText(currentTime.getHour() + ":0" + currentTime.getMinute());
            else
                oraCorrenteText.setText(currentTime.getHour() + ":" + currentTime.getMinute());
            mainApp.checkRestriction();
            if(mainApp.getModalitaRestrizioni()==1)
                restrictionWarning.setOpacity(1);

        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();

    }

    public void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;
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
