package interface_adapters.advance_team;
import interface_adapters.data_interface_adapters.advance_team_data.AdvanceTeamFileWriter;
import use_cases.advance_team.*;
import use_cases.general_classes.InformationRecord;

/**
 * This class is the controller for the AdvanceTeam use case.
 * It is responsible for the communication between the user and the use case.
 * It is also responsible for the communication between the use case and the data interface adapters.
 */
public class AdvanceTeamController {

    AdvanceTeamIB userInput;

    /**
     * This constructor creates a new AdvanceTeamController object.
     * @param informationRecord the information record that contains the information of the system
     * @param bracketID the ID of the bracket that the user wants to advance the team from
     * @param username the username of the user that wants to advance the team
     */
    public AdvanceTeamController(InformationRecord informationRecord, int bracketID, String username) {
        AdvanceTeamOB outputBoundary = new AdvanceTeamPresenter();
        AdvanceTeamGateway gateway = new AdvanceTeamFileWriter("brackets.txt");
        this.userInput = new AdvanceTeamUC(outputBoundary, gateway, informationRecord, bracketID, username);
    }

    /** This method is responsible for the communication between the user and the use case. */
    public AdvanceTeamOD create(int gameID){

        AdvanceTeamID inputData = new AdvanceTeamID(gameID);

        return this.userInput.advanceWinner(inputData);
    }

}
