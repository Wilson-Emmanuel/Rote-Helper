package com.wilcotech.kanjihelper.kanjihelper.controllers;

import com.wilcotech.kanjihelper.kanjihelper.custom_components.DialogComponent;
import com.wilcotech.kanjihelper.kanjihelper.custom_components.ManageTabComponent;
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
import javafx.scene.layout.AnchorPane;
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

    @FXML public Tab playTab;
    @FXML public Tab createTab;
    @FXML public Tab manageTab;

    ///MANAGE TAB
    @FXML public AnchorPane manageApn;

    //PLAY TAB
@FXML
public ComboBox<Group> playGroupsCmb;
@FXML
public TitledPane settingsPane;
@FXML private Accordion settingsAcd;

//CREATE TAB
 @FXML public TextField newGroupTxt;
 @FXML public Button newGroupBtn;
 @FXML public ComboBox<Group> createGroupsCmb;

 @FXML public TextField newKanjiTxt;
 @FXML public TextField newHiraganaTxt;
 @FXML public TextField newRomajiTxt;
 @FXML public TextField newEnglishTxt;
 @FXML public Button newEntryBtn;
 @FXML public Button playBtn;



private SettingsComponent settingsComponent;
private ManageTabComponent manageTabComponent;
@Autowired private EntryService entryService;
@Autowired private GroupService groupService;

 ObservableList<Group> groups = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadGroups();
        playGroupsCmb.setVisibleRowCount(6);
        createGroupsCmb.setVisibleRowCount(6);

        //set the accordion (settingsAcd) to expends the first and only accordion on startup
        settingsAcd.setExpandedPane(settingsPane);

        //creating and setting the custom settings component
        settingsComponent = new SettingsComponent();
        settingsPane.setContent(settingsComponent);

        //creating and setting the manager tab
        manageTabComponent = new ManageTabComponent(entryService,groupService);
        //AnchorPane.setLeftAnchor(manageTabComponent,0.0);
        //AnchorPane.setRightAnchor(manageTabComponent,0.0);
        manageApn.getChildren().add(manageTabComponent);

        //create new group
        newGroupBtn.setOnAction(actionEvent -> {
            createGroup();
        });

        //create new entry
        newEntryBtn.setOnAction(actionEvent -> {
            createEntry();
        });

        //setup combo boxes
        setupCombos(playGroupsCmb);
        setupCombos(createGroupsCmb);

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


        //setup the tabs
        ImageView playTabView = new ImageView(new Image("/images/play.png",true));
        ImageView createTabView = new ImageView(new Image("/images/add.png",true));
        ImageView manageTabView = new ImageView(new Image("/images/icon.png",true));
        playTabView.setFitHeight(16);
        playTabView.setFitWidth(16);createTabView.setFitHeight(16);
        createTabView.setFitWidth(16);manageTabView.setFitHeight(16);
        manageTabView.setFitWidth(16);


        playTab.setGraphic(playTabView);
        createTab.setGraphic(createTabView);
        manageTab.setGraphic(manageTabView);

    }



    private void setupCombos(ComboBox<Group> comboBox) {
        ComboxBoxFactory.setComboxFactory(comboBox);
    }

    private void loadGroups() {
        //get all groups
        groups.addAll(groupService.getGroups());
        playGroupsCmb.setItems(groups);
        createGroupsCmb.setItems(groups);
    }
    private void createGroup(){
        String groupName = newGroupTxt.getText();
        if(!groupName.isBlank()){
            groupName = groupName.trim();
        }else{
            NotificationUtil.inputError("Please enter valid group name.");
            return;
        }
        //check if group name already exists
        if(groupService.existsByGroupName(groupName)){
            NotificationUtil.inputError("Group already exists.");
            return;
        }
        Group group = Group.builder().groupName(groupName).build();
        group = groupService.save(group);
        groups.add(group);
        newGroupTxt.setText("");
    }

    private void createEntry() {
        Group currentGroup = createGroupsCmb.getSelectionModel().getSelectedItem();
        if(currentGroup == null){
            NotificationUtil.inputError("Invalid Group Selection.");
            return;
        }
        String kanji = newKanjiTxt.getText().trim();
        String hiragana = newHiraganaTxt.getText().trim();
        String romaji = newRomajiTxt.getText().trim();
        String english = newEnglishTxt.getText().trim();
        if((english.isBlank() || english.isEmpty()) || (kanji.isBlank() && hiragana.isBlank() && romaji.isBlank())){
            NotificationUtil.inputError("Invalid or incomplete input. Note that English cannot be empty.");
            return;
        }
        Entry entry = Entry.builder()
                .kanji(kanji)
                .hiragana(hiragana)
                .romaji(romaji)
                .english(english)
                .group(currentGroup.getGroupName())
                .build();
        entryService.save(entry,currentGroup);
        NotificationUtil.success("Entry successfully added.");
        newKanjiTxt.setText("");
        newEnglishTxt.setText("");
        newRomajiTxt.setText("");
        newHiraganaTxt.setText("");
    }
}
