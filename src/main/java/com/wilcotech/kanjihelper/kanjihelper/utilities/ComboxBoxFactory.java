package com.wilcotech.kanjihelper.kanjihelper.utilities;

import com.wilcotech.kanjihelper.kanjihelper.domain.models.Group;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 * Created by Wilson
 * on Tue, 29/12/2020.
 */
public class ComboxBoxFactory {

    public static void setComboxFactory(ComboBox<Group> comboBox){
        comboBox.setCellFactory(new Callback<ListView<Group>, ListCell<Group>>() {
            @Override public ListCell<Group> call(ListView<Group> p) {
                return new ListCell<Group>() {
                    @Override protected void updateItem(Group group, boolean empty) {
                        super.updateItem(group, empty);

                        if (group == null || empty) {
                            setText("");
                        } else {
                            setText(group.getGroupName());
                        }
                    }
                };
            }
        });
    }
}
