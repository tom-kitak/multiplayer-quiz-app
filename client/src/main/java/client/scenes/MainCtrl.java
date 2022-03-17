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
package client.scenes;

import commons.Activity;
import commons.SingleGame;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

public class MainCtrl {

    private Stage primaryStage;

    private HomeScreenCtrl homeScreenCtrl;
    private Scene homeScreen;

    private QuizScreenCtrl quizScreenCtrl;
    private Scene quizScreen;

    private EndScreenCtrl endScreenCtrl;
    private Scene endScreen;

    private HowToPlayCtrl howToPlayCtrl;
    private Scene howToPlayScreen;

    private AdministrativeInterfaceCtrl administrativeInterfaceCtrl;
    private Scene administrativeInterfaceScene;

    private AddActivityCtrl addActivityCtrl;
    private Scene addActivityScreen;

    private EditActivityCtrl editActivityCtrl;
    private Scene editActivityScreen;

    private ImportActivityCtrl importActivityCtrl;
    private Scene importActivityScene;

    /**
     * This method should be adjusted if you want to add new screens.
     * @param primaryStage
     * @param homeScreenPair
     * @param quizScreenPair
     * @param endScreenPair
     */
    public void initialize(Stage primaryStage,
                           Pair<HomeScreenCtrl, Parent> homeScreenPair,
                           Pair<QuizScreenCtrl, Parent> quizScreenPair,
                           Pair<EndScreenCtrl, Parent> endScreenPair,
                           Pair<HowToPlayCtrl, Parent> howToPlayPair,
                           Pair<AdministrativeInterfaceCtrl, Parent> administrativeInterfacePair,
                           Pair<AddActivityCtrl, Parent> addActivityPair,
                           Pair<EditActivityCtrl, Parent> editActivityPair,
                           Pair<ImportActivityCtrl, Parent> importActivityPair) {

        this.primaryStage = primaryStage;

        this.homeScreenCtrl = homeScreenPair.getKey();
        this.homeScreen = new Scene(homeScreenPair.getValue());

        this.quizScreenCtrl = quizScreenPair.getKey();
        this.quizScreen = new Scene(quizScreenPair.getValue());

        this.endScreenCtrl = endScreenPair.getKey();
        this.endScreen = new Scene(endScreenPair.getValue());

        this.howToPlayCtrl = howToPlayPair.getKey();
        this.howToPlayScreen = new Scene(howToPlayPair.getValue());

        this.administrativeInterfaceCtrl = administrativeInterfacePair.getKey();
        this.administrativeInterfaceScene = new Scene(administrativeInterfacePair.getValue());

        this.addActivityCtrl = addActivityPair.getKey();
        this.addActivityScreen = new Scene(addActivityPair.getValue());

        this.editActivityCtrl = editActivityPair.getKey();
        this.editActivityScreen = new Scene(editActivityPair.getValue());

        this.importActivityCtrl = importActivityPair.getKey();
        this.importActivityScene = new Scene(importActivityPair.getValue());

        showHomeScreen();
        primaryStage.show();
    }

    public void showHomeScreen() {
        primaryStage.setTitle("Home Screen");
        primaryStage.setScene(homeScreen);
    }

    public void showQuizScreen(SingleGame game) {
        primaryStage.setTitle("Quiz Screen");
        quizScreenCtrl.startGame(game);
        primaryStage.setScene(quizScreen);
    }

    public void showEndScreen() {
        primaryStage.setTitle("End Screen");
        primaryStage.setScene(endScreen);

    }

    public void showHowToPlay() {
        primaryStage.setTitle("How To Play");
        primaryStage.setScene(howToPlayScreen);
    }

    public void showAdministratorInterface(){
        primaryStage.setTitle("Administrator Tools");
        primaryStage.setScene(administrativeInterfaceScene);
        administrativeInterfaceCtrl.refresh();
    }

    public void showAddActivity() {
        primaryStage.setTitle("Add Activity");
        primaryStage.setScene(addActivityScreen);
        addActivityScreen.setOnKeyPressed(e -> addActivityCtrl.keyPressed(e));
    }

    public void showEditActivity(Activity activity) {
        primaryStage.setTitle("Edit Activity");
        primaryStage.setScene(editActivityScreen);
        editActivityScreen.setOnKeyPressed(e -> editActivityCtrl.keyPressed(e));
        editActivityCtrl.setActivity(activity);
    }

    public void showImportActivities() {
        primaryStage.setTitle("Import Activity");
        primaryStage.setScene(importActivityScene);
        importActivityScene.setOnKeyPressed(e -> importActivityCtrl.keyPressed(e));
    }
}