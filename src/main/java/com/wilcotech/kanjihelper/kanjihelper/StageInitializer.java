package com.wilcotech.kanjihelper.kanjihelper;

import com.wilcotech.kanjihelper.kanjihelper.controllers.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

/**
 * Created by Wilson
 * on Sun, 20/12/2020.
 */
@Component
public class StageInitializer implements ApplicationListener<RoteHelperUIStarter.StageReadyEvent> {

    @Value("classpath:/main.fxml")
    private Resource mainResource;

    @Value("${application.stage.title}")
    private String appTitle;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private MainController mainController;

    private Stage mainStage;
    @Override
    public void onApplicationEvent(RoteHelperUIStarter.StageReadyEvent stageReadyEvent) {

        try{
            FXMLLoader fxmlLoader = new FXMLLoader(mainResource.getURL());
            fxmlLoader.setController(mainController);
            Parent parent = fxmlLoader.load();

            mainStage = stageReadyEvent.getStage();

            Scene scene = new Scene(parent,700,450);
            scene.getStylesheets().add("/main.css");

            //Screen screen = Screen.getPrimary();
            //double width = screen.getVisualBounds().getWidth();
            //double height = screen.getVisualBounds().getHeight();
            mainStage.setScene(scene);
            //mainStage.setWidth(width);
            //mainStage.setHeight(height);
            mainStage.setTitle(appTitle);
            mainStage.getIcons().add(new Image("/images/icon.png"));
            mainStage.centerOnScreen();
            mainStage.show();
        }catch (Exception ex){
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Application Error");
            alert.setContentText("An error occurred while starting the application. Please try again " +
                    "or contact the developer.");
            alert.show();
        }
    }
}
