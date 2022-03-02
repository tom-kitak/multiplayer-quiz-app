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
                           Pair<EndScreenCtrl, Parent> endScreenPair) {

        this.primaryStage = primaryStage;

        this.homeScreenCtrl = homeScreenPair.getKey();
        this.homeScreen = new Scene(homeScreenPair.getValue());

        this.quizScreenCtrl = quizScreenPair.getKey();
        this.quizScreen = new Scene(quizScreenPair.getValue());

        this.endScreenCtrl = endScreenPair.getKey();
        this.endScreen = new Scene(endScreenPair.getValue());

        showHomeScreen();
        primaryStage.show();
    }

    public void showHomeScreen() {
        primaryStage.setTitle("Home Screen");
        primaryStage.setScene(homeScreen);
    }

    /**
     * Method shows a new quiz screen
     * @param questionNo indicates which question are we on
     */
    public void showQuizScreen(int questionNo) {
        primaryStage.setTitle("Quiz Screen");
        ///we need to make a class for quiz screen to include all the elements,
        ///the setter methods for this will just make use of the fact that quizScreen
        ///is a scene. QuizScreen extends Scene
        ///this.quizScreen.setQuestionNumber(questionNo);
        primaryStage.setScene(quizScreen);
    }

    public void showEndScreen() {
        primaryStage.setTitle("End Screen");
        primaryStage.setScene(endScreen);
    }

    /**
     * The method calls for the screen to show the next quiz question
     * @param questionNo calls the next slide as long as the question number is not 21
     */
    public void nextSlide(int questionNo) {
        if(questionNo <= 20){
            questionNo++;
            showQuizScreen(questionNo);
        }
        else showEndScreen();
    }
}