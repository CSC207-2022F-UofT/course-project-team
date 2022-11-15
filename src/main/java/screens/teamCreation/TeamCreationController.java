package screens.teamCreation;

import useCases.teamCreation.teamCreationIB;
import useCases.teamCreation.teamCreationID;
import useCases.teamCreation.teamCreationOD;


public class TeamCreationController {
    final teamCreationIB userInput;

    public TeamCreationController(teamCreationIB userInput) {
        this.userInput = userInput;
    }

    public teamCreationOD createNewTeam(String teamName, int teamSize) {
        teamCreationID inputData = new teamCreationID(teamName, teamSize);

        return userInput.createNewTeam(inputData);
    }
}