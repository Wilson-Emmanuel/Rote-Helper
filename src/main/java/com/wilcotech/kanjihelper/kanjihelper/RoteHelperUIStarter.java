package com.wilcotech.kanjihelper.kanjihelper;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Created by Wilson
 * on Sun, 20/12/2020.
 */
public class RoteHelperUIStarter extends Application {

    private ConfigurableApplicationContext configurableApplicationContext;
    @Override
    public void start(Stage stage) throws Exception {
        configurableApplicationContext.publishEvent(new StageReadyEvent(stage));
    }

    @Override
    public void init() throws Exception {
        configurableApplicationContext = new SpringApplicationBuilder(RoteHelperApplication.class).run();
    }

    @Override
    public void stop() throws Exception {
        configurableApplicationContext.close();
        Platform.exit();
    }

    static class StageReadyEvent extends ApplicationEvent{

        public StageReadyEvent(Stage stage){super(stage);}

        public Stage getStage(){
            return ((Stage) getSource());
        }
    }
}
