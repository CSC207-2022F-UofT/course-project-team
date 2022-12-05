package interface_adapters.end_tournament;

import interface_adapters.data_interface_adapters.end_tournament_data.EndTournFileWriter;
import use_cases.end_tournament.*;
import use_cases.general_classes.InformationRecord;

/**
 * A class for the Controller of the end tournament use case.
 */
public class EndTournController {
    final EndTournIB userInput;

    public EndTournController(String username, InformationRecord informationRecord, int bracketId) {
        EndTournOB endTournOB = new EndTournPresenter();
        EndTournGateway gateway = new EndTournFileWriter("brackets.txt");
        userInput = new EndTournUC(endTournOB, username, informationRecord, bracketId, gateway);
    }

    public void endTourn() {
        userInput.endTourn();
    }

}
