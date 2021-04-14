package com.wilcotech.kanjihelper.kanjihelper.controllers;

import com.wilcotech.kanjihelper.kanjihelper.custom_components.DialogComponent;
import com.wilcotech.kanjihelper.kanjihelper.custom_components.SettingsComponent;
import com.wilcotech.kanjihelper.kanjihelper.domain.models.Entry;
import com.wilcotech.kanjihelper.kanjihelper.domain.models.Group;
import com.wilcotech.kanjihelper.kanjihelper.domain.services.EntryService;
import com.wilcotech.kanjihelper.kanjihelper.domain.services.GroupService;
import com.wilcotech.kanjihelper.kanjihelper.utilities.ComboxBoxFactory;
import com.wilcotech.kanjihelper.kanjihelper.utilities.NotificationUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


/**
 * Created by Wilson
 * on Sun, 20/12/2020.
 */
@Controller
public class MainController implements Initializable {

    @FXML
    private Tab playTab;
    @FXML
    private Tab createTab;
    @FXML
    private Tab manageTab;

    //MANAGE TAB (included)
    //included fxml with id=manage on the main.fxml file
    @FXML
    private ManageController manageController;
    //@FXML private Window manage

    //PLAY TAB
    @FXML
    private ComboBox<Group> playGroupsCmb;
    @FXML
    private TitledPane settingsPane;
    @FXML
    private Accordion settingsAcd;

    //CREATE TAB (included)
    @FXML
    private EntryController entryController;
    @FXML
    private GroupController groupController;

     @FXML public Button playBtn;

    private SettingsComponent settingsComponent;
    @Autowired
    private EntryService entryService;
    @Autowired
    private GroupService groupService;

 private ObservableList<Group> groups = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ////////////////////////////////////////////////////////
        //get all groups from the db
        groups.addAll(groupService.getGroups());

        ////////////////////////////////////////////////////////
        //configure the settings accordion to expends on startup
        settingsAcd.setExpandedPane(settingsPane);

        ////////////////////////////////////////////////////////
        //create and configure the custom settings components
        settingsComponent = new SettingsComponent();
        settingsPane.setContent(settingsComponent);

        ////////////////////////////////////////////////////////
        //setup play tab's combo boxes
        ComboxBoxFactory.setComboxFactory(playGroupsCmb);
        playGroupsCmb.setItems(groups);
        playGroupsCmb.setVisibleRowCount(6);

        ////////////////////////////////////////////////////////
        //configure the included Entry creation component
        entryController.setEntryService(entryService);
        entryController.setGroups(groups);

        ////////////////////////////////////////////////////////
        //configure the included Group creation component
        groupController.setGroupService(groupService);
        groupController.setGroups(groups);

        ////////////////////////////////////////////////////
        //configure the included manageTab through its controller
        manageController.setGroups(groups);
        //ManageController was instantiated by FXML not SpringBoot,
        //SpringBoot auto instantiation wouldn't work on ManageController
        //hence these objects need to be passed explicitly
        manageController.setGroupService(groupService);
        manageController.setEntryService(entryService);

        /////////////////////////////////////////////////////////
        //configure the play button
        playBtn.setOnAction(actionEvent -> {
            Group currentGroup = playGroupsCmb.getSelectionModel().getSelectedItem();
            if(currentGroup == null){
                NotificationUtil.warning("Please select Group.");
                 return;
            }
            List<Entry> currentEntries = entryService.getEntries(currentGroup);
            if(currentEntries.isEmpty()){
                NotificationUtil.warning("The selected group has no entry.");
                return;
            }
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            DialogComponent dialogComponent = new DialogComponent(stage,currentEntries,settingsComponent.getInputSettings());
            dialogComponent.play();
        });

        ////////////////////////////////////////////////////////
        //configure the tabs and tab images
        ImageView playTabImageView = new ImageView(new Image("/images/play.png",true));
        ImageView createTabImageView = new ImageView(new Image("/images/add.png",true));
        ImageView manageTabImageView = new ImageView(new Image("/images/icon.png",true));
        playTabImageView.setFitHeight(16);
        playTabImageView.setFitWidth(16);
        createTabImageView.setFitHeight(16);
        createTabImageView.setFitWidth(16);
        manageTabImageView.setFitHeight(16);
        manageTabImageView.setFitWidth(16);
        playTab.setGraphic(playTabImageView);
        createTab.setGraphic(createTabImageView);
        manageTab.setGraphic(manageTabImageView);
    }
}
