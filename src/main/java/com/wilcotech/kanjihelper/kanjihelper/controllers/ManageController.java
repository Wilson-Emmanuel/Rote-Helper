package com.wilcotech.kanjihelper.kanjihelper.controllers;

import com.wilcotech.kanjihelper.kanjihelper.domain.models.Entry;
import com.wilcotech.kanjihelper.kanjihelper.domain.models.Group;
import com.wilcotech.kanjihelper.kanjihelper.utilities.ComboxBoxFactory;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Wilson
 * on Tue, 29/12/2020.
 */
public class ManageController implements Initializable {

    @FXML public ComboBox<Group> manageGroupsCmb;
    @FXML public TableView<Entry> manageTable;
    @FXML private TableColumn<Entry,String> snCol;
    @FXML private TableColumn<Entry,String> englishCol;
    @FXML private TableColumn<Entry,String> kanjiCol;
    @FXML private TableColumn<Entry,String> hiraganaCol;
    @FXML private TableColumn<Entry,String> romajiCol;

    @FXML public Button editBtn;
    @FXML public Button deleteBtn;
    @FXML public MenuButton menuBtn;





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        FontAwesomeIconView editIcon =new FontAwesomeIconView(FontAwesomeIcon.EDIT);
        editIcon.setStyleClass("whitebg");
        editBtn.setGraphic(editIcon);
        editBtn.setGraphicTextGap(1);
        FontAwesomeIconView deleteIcon =new FontAwesomeIconView(FontAwesomeIcon.TRASH_ALT);
        deleteIcon.setStyleClass("whitebg");
        deleteBtn.setGraphic(deleteIcon);
        editBtn.setGraphicTextGap(1);
        menuBtn.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.ELLIPSIS_V));
        ComboxBoxFactory.setComboxFactory(manageGroupsCmb);
        editBtn.setTooltip(new Tooltip("Edit Entry"));
        deleteBtn.setTooltip(new Tooltip("Delete Entry"));

        snCol.setCellValueFactory(new PropertyValueFactory<Entry,String>("sn"));
        kanjiCol.setCellValueFactory(new PropertyValueFactory<Entry,String>("kanji"));
        romajiCol.setCellValueFactory(new PropertyValueFactory<Entry,String>("romaji"));
        englishCol.setCellValueFactory(new PropertyValueFactory<Entry,String>("english"));
        hiraganaCol.setCellValueFactory(new PropertyValueFactory<Entry,String>("hiragana"));
        manageTable.selectionModelProperty().addListener(observable -> {
            int selected = manageTable.getSelectionModel().getSelectedIndex();
            deleteBtn.setDisable(selected <0);
            editBtn.setDisable(selected <0);
        });

        manageGroupsCmb.selectionModelProperty().addListener(observable -> {
            int selected = manageGroupsCmb.getSelectionModel().getSelectedIndex();
            menuBtn.setDisable(selected < 0);
        });

    }


}
