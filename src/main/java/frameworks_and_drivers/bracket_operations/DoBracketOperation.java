package frameworks_and_drivers.bracket_operations;

import frameworks_and_drivers.ExtendedView;
import interface_adapters.NextScreenData;
import interface_adapters.advance_team.AdvanceTeamController;
import interface_adapters.advance_team.AdvanceTeamFailed;
//import screens.bracketView;
import interface_adapters.change_points.ChangePointsController;
import interface_adapters.change_points.ChangePointsFailed;
import interface_adapters.declare_winner.DeclareWinnerController;
import interface_adapters.declare_winner.DeclareWinnerFailed;
import interface_adapters.view_interfaces.bracket_operation_interface.ChangePointsBOView;
import interface_adapters.view_interfaces.main_view_interfaces.ChangePointsExtendedView;
import use_cases.change_points.ChangePointsOD;
import use_cases.declare_winner.DeclareWinnerOD;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DoBracketOperation extends JFrame implements ActionListener, ChangePointsBOView {
    private JLabel title;
    private JLabel gameNumLabel;
    private JLabel teamsLabel;
    private JLabel advanceTeamLabel;
    private JLabel declareWInnerLabel;
    private JButton declareButton;
    private JLabel chngPtsLabel;
    private JButton advanceButton;
    private JTextField changePtsTF;
    private JButton changePointsButton;
    private JLabel changePtsInstruction;
    private JPanel bracketOpWindow;
    private JTextField teamPointsBox;

    private AdvanceTeamController advanceTeamController;
    private DeclareWinnerController declareWinnerController;
    private ChangePointsController changePointsController;
    public int gameID;
    private ExtendedView extendedView;

    private NextScreenData nextScreenData;

    public DoBracketOperation(AdvanceTeamController advanceTeamController,
                              DeclareWinnerController declareWinnerController,
                              ChangePointsController changePointsController, ExtendedView viewChange, NextScreenData nextScreenData) {
        super("Bracket Operations");
        this.advanceTeamController = advanceTeamController;
        this.declareWinnerController = declareWinnerController;
        this.changePointsController = changePointsController;
        this.extendedView = viewChange;
        this.nextScreenData = nextScreenData;

        gameNumLabel.setText("Game Number: 0");
        teamsLabel.setText("Teams: ");
        advanceTeamLabel.setText("Advance Winning Team");
        chngPtsLabel.setText("Change Points for Team");
        declareButton.setText("Declare Winner");
        declareWInnerLabel.setText("Declare the Winner");

        advanceButton.addActionListener(this);
        changePointsButton.addActionListener(this);
        declareButton.addActionListener(this);
        changePtsTF.addActionListener(this);


        this.setContentPane(bracketOpWindow);
        this.setPreferredSize(new Dimension(650, 300));
        this.pack();
        this.setVisible(true);

    }


    public void setGameForOperation(int gameID) {
        this.gameID = gameID;
    }

    public void setGameNumLabel(String gameID) {
        this.gameNumLabel.setText("Game Number: " + gameID);
    }

    public void setTeamsLabel(String label) {
        this.teamsLabel.setText(label);
    }
    public void changeTeamsLabel(String team1 , String team2, int team1Score, int team2Score) {
        this.teamsLabel.setText("[" + team1 + "] " + team1Score + " - " + team2Score + " [" + team2 + "]");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == advanceButton) {
                advanceTeamController.create(gameID);
            } else if (e.getSource() == declareButton) {
                declareWinnerController.create(gameID);
            } else if (e.getSource() == changePointsButton){
                int points = Integer.parseInt(changePtsTF.getText().trim());
                String teamName = teamPointsBox.getText().trim();
                if (teamName.equals("")) {
                    JOptionPane.showMessageDialog(null, "Please select a team.");
                }
                changePointsController.setView(this);
                changePointsController.create(gameID, points, teamName);
            }

        } catch (NumberFormatException ex){
            JOptionPane.showMessageDialog(null, "Please enter a valid integer.");
        } catch (AdvanceTeamFailed | DeclareWinnerFailed | ChangePointsFailed exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage());
        }
    }

}
