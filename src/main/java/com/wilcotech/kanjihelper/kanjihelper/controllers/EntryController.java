package com.wilcotech.kanjihelper.kanjihelper.controllers;

import com.wilcotech.kanjihelper.kanjihelper.domain.models.Entry;
import com.wilcotech.kanjihelper.kanjihelper.domain.models.Group;
import com.wilcotech.kanjihelper.kanjihelper.domain.services.EntryService;
import com.wilcotech.kanjihelper.kanjihelper.domain.services.GroupService;
import com.wilcotech.kanjihelper.kanjihelper.utilities.ComboxBoxFactory;
import com.wilcotech.kanjihelper.kanjihelper.utilities.NotificationUtil;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class EntryController implements Initializable {
    @FXML
    private ComboBox<Group> createGroupsCmb;

    @FXML
    private TextField newKanjiTxt;
    @FXML
    private TextField newHiraganaTxt;
    @FXML
    private TextField newRomajiTxt;
    @FXML
    private TextField newEnglishTxt;
    @FXML
    private Button newEntryBtn;
    @FXML
    private Button clearEntryBtn;

    private EntryService entryService;
    private ObservableList<Group> groups;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ComboxBoxFactory.setComboxFactory(createGroupsCmb);
        createGroupsCmb.setVisibleRowCount(6);

        //create new entry
        newEntryBtn.setOnAction(actionEvent -> {
            createEntry();
        });
        clearEntryBtn.setOnAction(actionEvent -> {
            clearEntryFields();
        });

    }

    public void setEntryService(EntryService entryService) {
        this.entryService = entryService;
    }

    public void setGroups(ObservableList<Group> groups) {
        this.groups = groups;
        this.createGroupsCmb.setItems(this.groups);
    }

    /**
     * Insert new valid entry into the database
     */
    private void createEntry() {

        //get the selected group that the entry is being created for
        Group currentGroup = createGroupsCmb.getSelectionModel().getSelectedItem();
        if(currentGroup == null){
            NotificationUtil.inputError("Invalid Group Selection.");
            return;
        }

        //get and validate input texts
        String kanji = newKanjiTxt.getText().trim();
        String hiragana = newHiraganaTxt.getText().trim();
        String romaji = newRomajiTxt.getText().trim();
        String english = newEnglishTxt.getText().trim();
        if((english.isBlank() || english.isEmpty()) || (kanji.isBlank() && hiragana.isBlank() && romaji.isBlank())){
            NotificationUtil.inputError("Invalid or incomplete input. Note that English cannot be empty.");
            return;
        }

        //insert entry into the db
        Entry entry = Entry.builder()
                .kanji(kanji)
                .hiragana(hiragana)
                .romaji(romaji)
                .english(english)
                .group(currentGroup.getGroupName())
                .build();
        entryService.save(entry,currentGroup);
        NotificationUtil.success("Entry successfully added.");
    }

    /**
     * Clear entry fields
     */
    private void clearEntryFields(){
        //empty
        newKanjiTxt.setText("");
        newEnglishTxt.setText("");
        newRomajiTxt.setText("");
        newHiraganaTxt.setText("");
    }
}
