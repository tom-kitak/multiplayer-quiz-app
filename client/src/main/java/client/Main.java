/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package client;

import static com.google.inject.Guice.createInjector;

import java.io.IOException;
import java.net.URISyntaxException;

import client.scenes.EndScreenCtrl;
import client.scenes.HomeScreenCtrl;
import client.scenes.HowToPlayCtrl;
import client.scenes.QuizScreenCtrl;
import client.scenes.MainCtrl;
import com.google.inject.Injector;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);

    public static void main(String[] args) throws URISyntaxException, IOException {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        var HomeScreenPair = FXML.load(HomeScreenCtrl.class,
                "client", "scenes", "HomeScreen.fxml");
        var QuizScreenPair = FXML.load(QuizScreenCtrl.class,
                "client", "scenes", "QuizScreen.fxml");
        var EndScreenPair = FXML.load(EndScreenCtrl.class,
                "client", "scenes", "EndScreen.fxml");
        var HowToPlayPair = FXML.load(HowToPlayCtrl.class,
                "client", "scenes", "HowToPlayScreen.fxml");

        var mainCtrl = INJECTOR.getInstance(MainCtrl.class);
        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });
        mainCtrl.initialize(primaryStage, HomeScreenPair, QuizScreenPair, EndScreenPair, HowToPlayPair);
    }

    private void closeProgram() {
        boolean answer = ConfirmBoxCtrl.display("Alert", "Are you sure you want to close?");
        if(answer) System.exit(0);
    }
}