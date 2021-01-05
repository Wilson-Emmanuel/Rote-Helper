package com.wilcotech.kanjihelper.kanjihelper.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import org.springframework.stereotype.Component;

/**
 * Created by Wilson
 * on Sun, 20/12/2020.
 */
@Component
public class SlideController {
    @FXML public Label slideNoLbl;
    @FXML public Text kanjiTxt;
    @FXML public Text hiraganaTxt;
    @FXML public Text romajiTxt;
    @FXML public Text englishTxt;
    @FXML public Button cancelBtn;
    @FXML public Button prevBtn;
    @FXML public Button nextBtn;
    @FXML public Label remainingTime;

    @FXML
    public void initialize(){

    }

    public Label getSlideNoLbl() {
        return slideNoLbl;
    }

    public Text getKanjiTxt() {
        return kanjiTxt;
    }

    public Text getHiraganaTxt() {
        return hiraganaTxt;
    }

    public Text getRomajiTxt() {
        return romajiTxt;
    }

    public Text getEnglishTxt() {
        return englishTxt;
    }

    public Button getCancelBtn() {
        return cancelBtn;
    }

    public Button getPrevBtn() {
        return prevBtn;
    }

    public Button getNextBtn() {
        return nextBtn;
    }

    public Label getRemainingTime() {
        return remainingTime;
    }
}
