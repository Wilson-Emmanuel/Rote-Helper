package com.wilcotech.kanjihelper.kanjihelper.custom_components;

import com.wilcotech.kanjihelper.kanjihelper.controllers.SlideController;
import com.wilcotech.kanjihelper.kanjihelper.domain.models.Entry;
import com.wilcotech.kanjihelper.kanjihelper.domain.models.Settings;
import com.wilcotech.kanjihelper.kanjihelper.utilities.Algorithms.DecreasingRandom;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.springframework.util.ResourceUtils;

import java.util.List;

/**
 * Created by Wilson
 * on Sun, 20/12/2020.
 */
public class DialogComponent  {
    private Stage owner;
    private Stage dialog;
    private SlideController slideController;
    private List<Entry> entries;
    private Settings settings;
    private int currentEntry =0;
    private Timeline timeline;
    private DecreasingRandom<Entry> entryDecreasingRandom;

    private StringProperty slideNoLabel = new SimpleStringProperty(this,"");
    private StringProperty kanji = new SimpleStringProperty(this,"");
    private StringProperty hiragana = new SimpleStringProperty(this,"");
    private StringProperty english = new SimpleStringProperty(this,"");
    private StringProperty romaji = new SimpleStringProperty(this,"");
    private StringProperty remainingTime = new SimpleStringProperty(this,"");


    public DialogComponent(Stage owner, List<Entry> entries, Settings settings){
        this.owner = owner;
        this.entries = entries;
        this.settings = settings;

        slideController = new SlideController();
        try{
            FXMLLoader loader = new FXMLLoader(ResourceUtils.getURL("classpath:slide.fxml"));
            loader.setController(slideController);
            Parent slideNode = loader.load();
            slideNode.getStyleClass().add("mainbg");
            Scene scene = new Scene(slideNode,600,445);
            scene.getStylesheets().addAll(owner.getScene().getStylesheets());
            dialog = new Stage();
            dialog.setScene(scene);
            dialog.setTitle(owner.getTitle()+" - Slide play");
            dialog.centerOnScreen();
            dialog.getIcons().addAll(owner.getIcons());
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(owner);
            //dialog.initStyle(StageStyle.UNDECORATED);

            slideController.getSlideNoLbl().textProperty().bind(slideNoLabel);
            slideController.getKanjiTxt().textProperty().bind(kanji);
            slideController.getHiraganaTxt().textProperty().bind(hiragana);
            slideController.getRomajiTxt().textProperty().bind(romaji);
            slideController.getEnglishTxt().textProperty().bind(english);
            slideController.getRemainingTime().textProperty().bind(remainingTime);


            slideController.getNextBtn().setOnAction(actionEvent -> {
                next();
            });
            slideController.getPrevBtn().setOnAction(actionEvent -> {
                prev();
            });
            slideController.getCancelBtn().setOnAction(actionEvent -> {
                if(dialog.isShowing()){
                    closeTimeline();
                    dialog.close();
                }
            });

            dialog.setOnCloseRequest(windowEvent -> {
                closeTimeline();
            });

            //setup timeline for background execution
            if(settings.isAnimate())
                setupTimeline();

            dialog.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
                if(settings.isRandom()){
                    keyEvent.consume();
                    return;
                }
                if(keyEvent.getCode() == KeyCode.LEFT || keyEvent.getCode() == KeyCode.PAGE_DOWN || keyEvent.getCode() == KeyCode.KP_LEFT){
                    prev();
                }else if(keyEvent.getCode() == KeyCode.RIGHT || keyEvent.getCode() == KeyCode.PAGE_UP || keyEvent.getCode() == KeyCode.KP_RIGHT){
                    next();
                }
                keyEvent.consume();
            });


        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }

    }



    private void setupTimeline() {

        //Disable control buttons when animation is enabled
        if(settings.isRandom()){
            slideController.getNextBtn().setDisable(true);
            slideController.getPrevBtn().setDisable(true);

            entryDecreasingRandom = new DecreasingRandom<>();
            entryDecreasingRandom.addAll(entries);
        }

        timeline = new Timeline(new KeyFrame(
                Duration.ZERO,
                actionEvent -> {
                    if(!settings.isRandom()){
                        next();
                    }else{
                        Entry random = entryDecreasingRandom.getRandom();
                        showSlide(random);
                    }
                }
        ), new KeyFrame(Duration.seconds(settings.getWaitTime()), actionEvent -> {}));//you can put as many keyframes as possible; each with a duration and event handler

        if(settings.isFinite())
            timeline.setCycleCount(settings.getRepeatCount() * entries.size());
        else
            timeline.setCycleCount(Animation.INDEFINITE);

        timeline.setOnFinished(actionEvent -> dialog.close());
    }

    private void prev(){
        if(currentEntry ==0)
            currentEntry = entries.size()-1;
        else
            currentEntry--;

        showEntries();
    }
    private void next(){
        if(currentEntry == entries.size()-1)
            currentEntry = 0;
        else
            currentEntry++;
        showEntries();
    }
    public void play(){
        if(timeline != null){
            //when it's not random, start from the beginning and animate
            currentEntry =entries.size()-1;
            timeline.play();
        }else{
            showEntries();
        }
            dialog.showAndWait();
    }
    private void showEntries(){
        Entry cur = entries.get(currentEntry);
        showSlide(cur);
    }
    private void showSlide(Entry cur){
        if(settings.isKanji())
            kanji.setValue(cur.getKanji());
        if(settings.isRomaji())
            romaji.setValue(cur.getRomaji());
        if(settings.isHiragana())
            hiragana.setValue(cur.getHiragana());
        if(settings.isEnglish())
            english.setValue(cur.getEnglish());

        if(!settings.isRandom())
            slideNoLabel.setValue((currentEntry+1)+"/"+entries.size());
        else{
            slideNoLabel.setValue("");
        }
    }
    private void closeTimeline() {
        if(timeline != null && (timeline.getStatus() == Animation.Status.RUNNING || timeline.getStatus() == Animation.Status.PAUSED))
            timeline.stop();
    }

}
