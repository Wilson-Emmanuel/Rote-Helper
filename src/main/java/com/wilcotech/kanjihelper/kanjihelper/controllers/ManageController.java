package com.wilcotech.kanjihelper.kanjihelper.controllers;

import com.wilcotech.kanjihelper.kanjihelper.domain.models.Entry;
import com.wilcotech.kanjihelper.kanjihelper.domain.models.Group;
import com.wilcotech.kanjihelper.kanjihelper.domain.services.EntryService;
import com.wilcotech.kanjihelper.kanjihelper.domain.services.GroupService;
import com.wilcotech.kanjihelper.kanjihelper.utilities.ComboxBoxFactory;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Wilson
 * on Tue, 29/12/2020.
 */
//@Controller class to be managed by FXML since it's included
public class ManageController implements Initializable {

    @FXML public AnchorPane manageAnchor;
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

   private EntryService entryService;
    private GroupService groupService;
    private ObservableList<Group> groups;

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
        manageTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        snCol.setMaxWidth( 1f * Integer.MAX_VALUE * 10);//10%
        englishCol.setMaxWidth( 1f * Integer.MAX_VALUE * 30 );//30%
        kanjiCol.setMaxWidth( 1f * Integer.MAX_VALUE * 20 );//20%
        romajiCol.setMaxWidth( 1f * Integer.MAX_VALUE * 20 );//20%
        hiraganaCol.setMaxWidth( 1f * Integer.MAX_VALUE * 20 );//20%
        snCol.setStyle( "-fx-alignment: CENTER;");


        manageGroupsCmb.selectionModelProperty().addListener(observable -> {
            int selected = manageGroupsCmb.getSelectionModel().getSelectedIndex();
            menuBtn.setDisable(selected < 0);
        });

        manageGroupsCmb.setOnAction(actionEvent -> {
            Group selected = manageGroupsCmb.getSelectionModel().getSelectedItem();
            manageTable.getItems().addAll(entryService.getEntries(selected));
        });

    }
    public void setGroups(ObservableList<Group> groups){
        this.groups = groups;
        manageGroupsCmb.setItems(this.groups);
    }
    public void setEntryService(EntryService entryService){
        this.entryService = entryService;
    }
    public void setGroupService(GroupService groupService){
        this.groupService = groupService;
    }



}
