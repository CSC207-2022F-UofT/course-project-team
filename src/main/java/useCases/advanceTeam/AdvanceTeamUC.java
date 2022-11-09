package useCases.advanceTeam;

import entities.*;
import useCases.generalInterfaces.CheckUserPermissionIF;

import java.util.ArrayList;
import java.util.List;

public class AdvanceTeamUC implements CheckUserPermissionIF{

    public Bracket bracket;
    public User user;
    public String username;
    public Game game;
    public int bracketID;
    public int gameID;

    public void advanceTeam(int bracketID, String username, int gameID) {
        this.username = username;
        this.bracketID = bracketID;
        this.gameID = gameID;
    }
    public void findUser(AccountRepo accountRepo) {
        ArrayList<User> users = accountRepo.getUsers();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                this.user = user;
            }
        }
    }

    public void findBracket(BracketRepo bracketRepo) {
        this.bracket = bracketRepo.getBracket(this.bracketID);
    }

    public void findGame(int gameID, Game head) {
        if (head == null) {
            return;
        } else if (head.getGameID() == gameID) {
            this.game = head;
        } else {
            findGame(gameID, head.getPrevGame1());
            findGame(gameID, head.getPrevGame2());
        }
    }

    public boolean checkUserPermission(User user) {
        String userRole = user.getBracketRole(this.bracket.getTournamentID());
        User assignedObserver = this.game.getObserver();
        String username = user.getUsername();
        String assignedObserverUsername = assignedObserver.getUsername();

        if (userRole.equals("Overseer")) {
            return true;
        } else if (userRole.equals("Observer")) {
            return username.equals(assignedObserverUsername);
        } else {
            return false;
        }
    }

    // Make helper functions private.

    public boolean checkGame(Game game) {
        return this.game != null;
    }


    public boolean checkGameWinner(Game game) {
        return game.getGameStatus();
    }

    public int getTreeHeight(Game head) {
        if (head == null) {
            return 0;
        } else {
            int leftHeight = getTreeHeight(head.getPrevGame1());
            int rightHeight = getTreeHeight(head.getPrevGame2());

            if (leftHeight > rightHeight) {
                return leftHeight + 1;
            } else {
                return rightHeight + 1;
            }
        }
    }

    // Get games from a certain level.
    public static List<Game> returnLevelGames(Game head, int roundNum){
        List<Game> games = new ArrayList<>();
        if (head == null) {
            return games;
        } else if (head.getPrevGame1() == null && head.getPrevGame2() == null) {
            if (head.getGameRound() == roundNum) {
                games.add(head);
            }
            return games;
        } else {
            if (head.getGameRound() == roundNum) {
                games.add(head);
            }
            games.addAll(returnLevelGames(head.getPrevGame1(), roundNum));
            games.addAll(returnLevelGames(head.getPrevGame2(), roundNum));
            return games;
        }
    }

    public void insertTeam(Team team, Game game){
        // We are inserting the team to the round immediately after the round the game is in. That is, the current
        // round minus 1 - rounds are counted backwards in the tree.
        ArrayList<Game> games = (ArrayList<Game>) returnLevelGames(game, this.game.getGameRound() - 1);
        for (Game g : games){
            // Find the node in tree s.t. its previous node(1/2) == game. Insert team into that node.
            if (g.getPrevGame1().getGameID() == game.getGameID() || g.getPrevGame2().getGameID() == game.getGameID()){
                g.setTeam(team, 0);
            }
        }
    }

//    public static void main(String[] args) {
//        DefaultGame defaultGame = new DefaultGame();
//        ArrayList<Game> games = (ArrayList<Game>) returnLevelGames(defaultGame, 0);
//        for (Game g : games){
//            System.out.println(defaultGame.getGameID());
//            System.out.println(g == defaultGame);
//        }
//    }


//    public void insertTeam(Team team, Game game) {
//        // Find the node in tree s.t. its previous node(1/2) == game.
//        int currGameId = game.getGameID();
//        Game head = this.bracket.getFinalGame();
//        if (game.getPrevGame1().getGameID() == currGameId || game.getPrevGame2().getGameID() == currGameId) {
//            game.
//        }
////        if (game.getPrevGame1().getGameID() == gameId) {
////            game.getPrevGame1().setTeam(team);
////        } else if (game.getPrevGame2().getGameID() == gameId) {
////            game.getPrevGame2().setTeam(team);
////        } else {
////            insertTeam(team, game.getPrevGame1());
////            insertTeam(team, game.getPrevGame2());
////        }
//    }

    public boolean advanceWinner(){
        // You cannot advance a team from the final.
        if (this.game.getGameRound() + 1 >= getTreeHeight(this.bracket.getFinalGame())) {
            return false;
        }
        // Standard checks
        if (!checkUserPermission(this.user) || !checkGame(this.game) || !checkGameWinner(this.game)) {
            return false;
        }
        Team winner = this.game.getWinner();
        insertTeam(winner, this.game);
        return true;
    }

}
