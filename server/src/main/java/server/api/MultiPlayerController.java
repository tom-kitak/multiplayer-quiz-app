package server.api;

import commons.Activity;
import commons.MultiGame;
import commons.Player;

import commons.Question;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

import org.springframework.web.bind.annotation.RestController;
import server.database.ActivityRepository;

import java.util.*;

import static server.util.QuestionConversion.convertActivity;

@RestController
public class MultiPlayerController {
    private ArrayList<MultiGame> games;
    private MultiGame currentLobbyGame;
    private final Random random;
    private final ActivityRepository repo;
    private int id;
    private HashMap<MultiGame, Integer> allPlayersResponded;

    /**
     * Creates a new MultiplayerController object.
     * @param random a new Random object
     * @param repo the activity repository
     */
    public MultiPlayerController(Random random, ActivityRepository repo) {
        this.random = random;
        this.id = 0;
        this.repo = repo;
        this.games = new ArrayList<>();
        this.currentLobbyGame = new MultiGame(null);
        currentLobbyGame.setId(id);
        this.allPlayersResponded = new HashMap<>();
        allPlayersResponded.put(currentLobbyGame, 0);
    }

    /**Called when the player connects or disconnect from the lobby.
     * @param player the player send by the client
     * @return the game object that acts as the lobby
     */
    @MessageMapping("/multi")
    @SendTo("/topic/multi")
    public MultiGame connect( Player player){
        System.out.println("-----------------");
        System.out.println(player);
        ArrayList<Player> tempPlayers = currentLobbyGame.getPlayers();
        if(tempPlayers.contains(player)){
            tempPlayers.remove(player);
        } else {
            tempPlayers.add(player);
        }
        currentLobbyGame.setPlayers(tempPlayers);

        return currentLobbyGame;
    }

    /**Starts the game and creates a new lobby.
     * @return the game that started
     */
    @MessageMapping("/start")
    @SendTo("/topic/started")
    public MultiGame startGame() {
        Question question = getQuestion();
        MultiGame started = currentLobbyGame;
        started.setCurrentQuestion(question);
        this.id++;
        currentLobbyGame = new MultiGame(null);
        currentLobbyGame.setId(id);
        allPlayersResponded.put(currentLobbyGame, 0);
        return started;
    }

    /**NEEDS REFACTORING: only TEMP.
     * @return
     */
    public Question getQuestion() {
        // Can't create a question if there aren't enough activities
        if (repo.count() < 4)
            return null;
        Activity[] activities = new Activity[4];
        // This is a workaround for the id generation that isn't consistent
        // This works now but will be slow in the future, so we need to research better id assignment.
        List<Activity> currentRepo = repo.findAll();
        // Collects the 4 activities needed for a question
        for (int i = 0; i < 4; i++) {

            // Picks a random activity
            var idx = random.nextInt(currentRepo.size());
            Activity a = currentRepo.get(idx);
            // Makes a list of current activities and checks for duplicates
            // Old implementation changed because of a java API 1.6 error.
            List<Activity> list = new ArrayList<>();
            Collections.addAll(list, activities);
            // Adds the activity or reruns the iteration.
            if (!list.contains(a)) {
                activities[i] = a;
            } else {
                i--;
            }
        }
        // Returns the result.
        Question question = convertActivity(activities);
        return question;
    }

    @MessageMapping("/multi/gameplay/{gameId}")
    @SendTo("/topic/multi/gameplay/{gameId}")
    public MultiGame gameplayQuestionSender(@DestinationVariable String gameId) {
        MultiGame game = null;
        for (MultiGame g : games){
            if (g.getId() == Integer.valueOf(gameId)){
                game = g;
                break;
            }
        }

    }




}
