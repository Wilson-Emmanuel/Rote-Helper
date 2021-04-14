package com.wilcotech.kanjihelper.kanjihelper.custom_components;

import com.wilcotech.kanjihelper.kanjihelper.controllers.SettingsController;
import com.wilcotech.kanjihelper.kanjihelper.domain.models.Settings;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import org.springframework.util.ResourceUtils;

import java.util.prefs.Preferences;

/**
 * Created by Wilson
 * on Sun, 20/12/2020.
 */

public class SettingsComponent extends AnchorPane {

    private SettingsController settingsController;

    public SettingsComponent(){
        super();
        settingsController = new SettingsController();
        try{
            //Load  UI
            FXMLLoader loader = new FXMLLoader(ResourceUtils.getURL("classpath:settings.fxml"));
            loader.setController(settingsController);
            Node settingsNode = loader.load();
            this.getChildren().add(settingsNode);

            //set previously saved settings or save and get default settings
            settingsController.displayDefaults(getSavedSettings());
        }catch (Exception ex){
            //ex.printStackTrace();
        }
    }

    public Settings getInputSettings(){
        Settings settings = Settings.builder()
                .isFinite(settingsController.isInfinite())
                .repeatCount(settingsController.getRepeatCount())
                .waitTime(settingsController.getWaitTimeTxt())
                .kanji(settingsController.isKanjiChecked())
                .english(settingsController.isEnglishChecked())
                .hiragana(settingsController.isHiraganaChecked())
                .romaji(settingsController.isRomajiChecked())
                .random(settingsController.isRandomCheck())
                .animate(settingsController.isAnimateChecked())
                .build();
        //commit this new settings before returning because user may have changed it
        return commitSettings(settings);
    }
    private Settings getSavedSettings(){
        Preferences pref = Preferences.userNodeForPackage(SettingsComponent.class);
         return Settings.builder()
                .isFinite(pref.getBoolean("isFinite",false))
                .repeatCount(pref.getInt("repeatCount",0))
                .waitTime(pref.getInt("waitTime",5))
                .kanji(pref.getBoolean("kanji",true))
                .romaji(pref.getBoolean("romaji",true))
                .hiragana(pref.getBoolean("hiragana",true))
                .english(pref.getBoolean("english",true))
                .random(pref.getBoolean("random",false))
                .animate(pref.getBoolean("animate",false))
                .build();
    }

    /**
     * Commit and return same
     * @param settings
     * @return
     */
    private Settings commitSettings(Settings settings){
        Preferences pref = Preferences.userNodeForPackage(SettingsComponent.class);
        pref.putBoolean("isFinite",settings.isFinite());
        pref.putBoolean("kanji",settings.isKanji());
        pref.putBoolean("romaji",settings.isRomaji());
        pref.putBoolean("hiragana",settings.isHiragana());
        pref.putBoolean("english",settings.isEnglish());
        pref.putBoolean("random",settings.isRandom());
        pref.putBoolean("animate",settings.isAnimate());
        pref.putInt("waitTime",settings.getWaitTime());
        pref.putInt("repeatCount",settings.getRepeatCount());
        return settings;
    }


}
