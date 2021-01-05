package com.wilcotech.kanjihelper.kanjihelper.utilities;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 * Created by Wilson
 * on Sun, 20/12/2020.
 */
public class NotificationUtil {
    public static void inputError(String error){
        Notifications.create()
                .title("Bad Input")
                .text(error)
                .darkStyle()
                .hideAfter(Duration.seconds(3))
                .position(Pos.BOTTOM_RIGHT)
                .showError();
    }
    public static void warning(String warning){
        Notifications.create()
                .title("Warning")
                .text(warning)
                .darkStyle()
                .hideAfter(Duration.seconds(3))
                .position(Pos.BOTTOM_RIGHT)
                .showWarning();
    }
    public static void success(String message){
        Image  image = new Image("/images/good.png");

        Notifications.create()
                .title("Success")
                .text(message)
                .darkStyle()
                .graphic(new ImageView(image))
                .hideAfter(Duration.seconds(3))
                .position(Pos.BOTTOM_RIGHT)
                .showInformation();
    }
}
