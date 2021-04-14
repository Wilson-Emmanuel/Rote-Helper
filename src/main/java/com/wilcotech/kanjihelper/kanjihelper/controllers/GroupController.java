package com.wilcotech.kanjihelper.kanjihelper.controllers;

import com.wilcotech.kanjihelper.kanjihelper.domain.models.Group;
import com.wilcotech.kanjihelper.kanjihelper.domain.services.GroupService;
import com.wilcotech.kanjihelper.kanjihelper.utilities.NotificationUtil;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class GroupController implements Initializable {
    @FXML
    public TextField newGroupTxt;
    @FXML public Button newGroupBtn;

    private GroupService groupService;
    private ObservableList<Group> groups;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //create new group
        newGroupBtn.setOnAction(actionEvent -> {
            createGroup();
        });
    }

    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    public void setGroups(ObservableList<Group> groups) {
        this.groups = groups;
    }

    /**
     * Insert a valid group into the DB
     */
    private void createGroup(){
        String groupName = newGroupTxt.getText().trim();
        if(groupName.isBlank()){
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
}
