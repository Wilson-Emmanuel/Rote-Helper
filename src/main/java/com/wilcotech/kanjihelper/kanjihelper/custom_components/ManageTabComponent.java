package com.wilcotech.kanjihelper.kanjihelper.custom_components;

import com.wilcotech.kanjihelper.kanjihelper.controllers.ManageController;
import com.wilcotech.kanjihelper.kanjihelper.domain.models.Entry;
import com.wilcotech.kanjihelper.kanjihelper.domain.models.Group;
import com.wilcotech.kanjihelper.kanjihelper.domain.services.EntryService;
import com.wilcotech.kanjihelper.kanjihelper.domain.services.GroupService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Wilson
 * on Tue, 29/12/2020.
 */
public class ManageTabComponent extends AnchorPane {

    public ComboBox<Group> manageGroupsCmb;
     public TableView<Entry> manageTable;
     public Button editBtn;
     public Button deleteBtn;
     public MenuButton menuBtn;

     private ManageController manageController;
     private EntryService entryService;
     private GroupService groupService;

    ObservableList<Group> groups = FXCollections.observableArrayList();

    public ManageTabComponent(EntryService entryService, GroupService groupService){
        this.entryService = entryService;
        this.groupService = groupService;
        try{

            FXMLLoader fxmlLoader = new FXMLLoader(ResourceUtils.getURL("classpath:manage.fxml"));

            manageController = new ManageController();
            fxmlLoader.setController(manageController);
            Node parent = fxmlLoader.load();
            this.getChildren().add(parent);
            manageGroupsCmb = manageController.manageGroupsCmb;
            manageTable = manageController.manageTable;
            editBtn = manageController.editBtn;
            deleteBtn = manageController.deleteBtn;
            menuBtn = manageController.menuBtn;

            groups.addAll(groupService.getGroups());
            manageGroupsCmb.setItems(groups);

            manageGroupsCmb.setOnAction(actionEvent -> {
                Group selected = manageGroupsCmb.getSelectionModel().getSelectedItem();
                manageTable.getItems().addAll(entryService.getEntries(selected));
            });


        }catch (IOException ex){

        }
    }


}
