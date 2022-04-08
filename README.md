# Starting template

The Quizzzz application presents entertaining exercises which aim to redirect the attention of the user towards the electrical consumption of everyday appliances. Setting up the environment consists of cloning the repository found at the address: https://gitlab.ewi.tudelft.nl/cse1105/2021-2022/team-repositories/oopp-group-78/repository-template/-/tree/README_file into an IDE. The steps of running the project can be found below in the "How to run it" section.

## Description of project
This application consists of a game with 2 possible game modes. First, you can play single player. In this game mode you will compete against the global leaderboard. You will score points by answering questions correctly and as fast as possible. The other game mode is multiplayer. In the multiplayer mode you will compete against different players to get the highest score out of the players in your lobby. Be aware that the game includes 3 question templates: Comparison Questions, Open Questions and Wattage Questions. Each of those have been carefully chosen not only to recognize the electrical consumption of an appliance, but also to raise the attention of the user towards the differences between power usages of multiple said devices. The goal of the game is to have fun and become more aware of our energy usage.

The game consists of a 20 rounds, each taking up to 15 seconds to answer. Anybody can choose from playing singleplayer or multiplayer. An addition has to be made regarding the rules of the multiplayer game: everybody has the option of choosing a joker card in any round, with a maximum of 3 per game. The 3 jokers have special effects which could help you obtain the victory: eliminate a wrong answer, shortening the answer time of the other players and double points. Each effect is applied in during only the respective round.

In addition, every user has the possibility of editing the activities already existing in the database. They can choose from adding, deleting and updating any activity by changing them in the admin tools screen, connected with a button in the home screen. The activities are needed for generating questions. You can obtain the activities in the shared repository and in the application will be clear instructions on how to import them.

## Group members

| Profile Picture                                                  | Name                     | Email                              |
|------------------------------------------------------------------|--------------------------|------------------------------------|
| <img src="docs/profile_pictures/pf_Bryan.jpg" width="80">        | Bryan Wassenaar          | B.J.A.Wassenaar@student.tudelft.nl |
| <img src ="docs/profile_pictures/pf_Eugen.jpg" width = "80"> | Bulboaca Alexandru-Eugen | A.Bulboaca@student.tudelft.nl      |
| <img src ="docs/profile_pictures/pf_Lukasz.jpg" width = "80">    | Łukasz Rek		          | Rek@student.tudelft.nl             |
| <img src ="docs/profile_pictures/pf_Tom.jpg" width = "80">       | Tom Kitak		      | tkitak@student.tudelft.nl             |
| <img src ="docs/profile_pictures/pf_Laurens.jpg" width = "80"> | Laurens Michielsen | L.L.Michielsen@student.tudelft.nl | 


## How to run it
There are two possible options to run the project. If you would like to use the first option, you should first start the server out of Intellij where you run the Main class in the server subdirectory. After the server is up and running, you have to start the client side. You achieve this by running the Main class in the client subdirectory. There is one downside to this approach: if your machine is wrongly configured you will get a lot of errors. The second option is to run the project using Gradle. You first start the server by starting the gradle.build file in the server subdirectory and the build.gradle file in the client directory after the server is up and running.
Moreover, you have the option of selecting the server you want to be play on, by inserting it's IP address in the first screen of the running application. 
The next step consists of selecting which type of game you want to try: singleplayer or multiplayer. The singleplayer version starts the game immediately after choosing this option. In contrast, after selecting multiplayer you will be placed in a waiting room with everybody who is on the same server ip address as you are. From there the process is self-explanatory.
## How to contribute to it
If you want to contribute to the project, you create a branch from the developer branch. You make the changes you want to make. After you are done you push the newly created branch with your changes to gitlab. You create a merge request with explanation to merge with the developer branch. Your code will be reviewed by at least 2 members of the team and needs to pass the pipeline. If they think changes are necessary they will comment on your merge request with a request for changes. If they deem your code adheres to our coding standards, then they will approve your request. 
## Copyright / License (opt.)
