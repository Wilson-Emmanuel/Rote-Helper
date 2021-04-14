package com.wilcotech.kanjihelper.kanjihelper.controllers;

import com.wilcotech.kanjihelper.kanjihelper.domain.models.Settings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

/**
 * Created by Wilson
 * on Sun, 20/12/2020.
 */
public class SettingsController implements Initializable {

   @FXML
   private TextField waitTimeTxt;
   @FXML
   private CheckBox kanjiCbn;
   @FXML
   private CheckBox hiraganaCbn;
   @FXML
   private CheckBox romajiCbn;
   @FXML
   private CheckBox englishCbn;
   @FXML
   private RadioButton infiniteRbn;
   @FXML
   private RadioButton finiteRbn;
   @FXML
   private TextField repeatCountTxt;
   @FXML
   private CheckBox randomCbn;
   @FXML
   private CheckBox animateCbn;

   private boolean infinite;
   ToggleGroup repeatToggleGroup;

   @Override
   public void initialize(URL url, ResourceBundle resourceBundle) {

      //setup the repeat radio buttons
       repeatToggleGroup = new ToggleGroup();
      infiniteRbn.setUserData("infinite");
      finiteRbn.setUserData("finite");
      infiniteRbn.setToggleGroup(repeatToggleGroup);
      finiteRbn.setToggleGroup(repeatToggleGroup);

      repeatToggleGroup.selectedToggleProperty().addListener((toggle,oldToggle,newToggle) ->{
         RadioButton rb = (RadioButton)repeatToggleGroup.getSelectedToggle();
         if (rb != null) {
             infinite = "infinite".equalsIgnoreCase((String)rb.getUserData());
            repeatCountTxt.setDisable(infinite);
         }
      });

      waitTimeTxt.textProperty().addListener((value,oldValue,newValue)->{
         if(!Pattern.matches("^[1-9][0-9]*$",newValue)){
            waitTimeTxt.setText("");
         }
      });
      repeatCountTxt.textProperty().addListener((value,oldValue,newValue)->{
         if(!Pattern.matches("^[1-9][0-9]*$",newValue)){
            repeatCountTxt.setText("");
         }
      });
      animateCbn.selectedProperty().addListener((observableValue, aBoolean, t1) -> {
            infiniteRbn.setDisable(!t1);
            finiteRbn.setDisable(!t1);
            waitTimeTxt.setDisable(!t1);
            randomCbn.setDisable(!t1);
            //Only enable this when is true that animation and finite are enabled else disable
            repeatCountTxt.setDisable(!(t1 && finiteRbn.isSelected()));
      });
   }

   /**
    * Get wait time in seconds
    * @return
    */
   public int getWaitTimeTxt() {
      int waitTime = 0;
      try{
         waitTime = Integer.parseInt(waitTimeTxt.getText());
      }catch (Exception ex){}
      return waitTime;
   }

   public boolean isKanjiChecked() {
      return kanjiCbn.isSelected();
   }

   public boolean isRandomCheck(){
      return randomCbn.isSelected();
   }

   public boolean isAnimateChecked(){
      return animateCbn.isSelected();
   }

   public boolean isHiraganaChecked() {
      return hiraganaCbn.isSelected();
   }

   public boolean isRomajiChecked() {
      return romajiCbn.isSelected();
   }

   public boolean isEnglishChecked() {
      return englishCbn.isSelected();
   }

   public boolean isInfinite() {
      return !infinite;
   }

   public int getRepeatCount() {
      int repeatCount  = 0;
      try{
         repeatCount = Integer.parseInt(repeatCountTxt.getText());
      }catch (Exception ex){
         repeatCount =0;
      }
      return repeatCount;
   }

   public void displayDefaults(Settings settings){
      if(settings.isFinite()){
         repeatToggleGroup.selectToggle(finiteRbn);
      }else{
         repeatToggleGroup.selectToggle(infiniteRbn);
      }
      repeatCountTxt.setText(settings.getRepeatCount()+"");
      kanjiCbn.setSelected(settings.isKanji());
      englishCbn.setSelected(settings.isEnglish());
      romajiCbn.setSelected(settings.isRomaji());
      hiraganaCbn.setSelected(settings.isHiragana());
      waitTimeTxt.setText(settings.getWaitTime()+"");
      randomCbn.setSelected(settings.isRandom());
      animateCbn.setSelected(settings.isAnimate());
   }
}
