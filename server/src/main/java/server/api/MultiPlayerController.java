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
//CHECKSTYLE:OFF
import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;

import static server.util.QuestionConversion.convertActivity;

@RestController
public class MultiPlayerController {
    private ArrayList<MultiGame> games;
    private MultiGame currentLobbyGame;
    private final Random random;
    private final ActivityRepository repo;
    private int id;
    private ArrayList<Integer> allPlayersResponded;

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
        this.allPlayersResponded = new ArrayList<>();
        allPlayersResponded.add(0);
    }

    /**Called when the player connects or disconnect from the lobby.
     * @param player the player send by the client
     * @return the game object that acts as the lobby
     */
    @MessageMapping("/multi")
    @SendTo("/topic/multi")
    public MultiGame connect(Player player){
        System.out.println("-----------------");
        System.out.println(player);
        ArrayList<Player> tempPlayers = currentLobbyGame.getPlayers();
        if(tempPlayers.contains(player)){
            tempPlayers.remove(player);
            System.out.println("Disconnected\n");
        } else {
            tempPlayers.add(player);
            System.out.println("Connected\n");
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
        allPlayersResponded.add(0);
        System.out.println(currentLobbyGame.hashCode());
        games.add(started);
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
    public MultiGame gameplayQuestionSender(@DestinationVariable String gameId, MultiGame gameFromPlayer) {
        MultiGame game = null;
        for (MultiGame g : games){
            if (g.getId() == Integer.valueOf(gameId)){
                game = g;
                break;
            }
        }
        if (allPlayersResponded.contains(game.getId())){
            allPlayersResponded.set(game.getId(), allPlayersResponded.get(game.getId()) + 1);
        }
        if (game.getPlayers().size() <= allPlayersResponded.get(game.getId())){
            game.setCurrentQuestion(getQuestion());
            System.out.println(game.getCurrentQuestion());
            allPlayersResponded.set(game.getId(), 0);
            return game;
        }
        return null;
    }

    @SendTo("/topic/multi/gameplay/{gameId}")
    public MultiGame sendQuestion(@DestinationVariable String gameId,MultiGame game) {
        System.out.println(gameId);
        game.setCurrentQuestion(getQuestion());
        return game;
    }

    @MessageMapping("/multi/leaveInGame/{gameId}")
    public void disconnect(@DestinationVariable String gameId, Player player) {
        MultiGame game= null;
        for (MultiGame g : games) {
            if (g.getId() == Integer.valueOf(gameId)) {
                game = g;
                break;
            }
        }
        if(game != null){
            ArrayList<Player> players = game.getPlayers();
            players.remove(player);
            game.setPlayers(players);
        }
    }




}
