package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import seng201.team0.GameManager;
import seng201.team0.models.GameStats;
import seng201.team0.models.GameTimer;
import seng201.team0.models.Race;
import seng201.team0.models.RaceParticipant;

import java.util.Random;

public class LeaderboardController extends ParentController {
    @FXML
    private Label leaderboardHeadingLabel;
    @FXML
    private VBox leaderboardVBox;
    @FXML
    private Label playerResultLabel;

    // Properties
    private GameStats gameDB = GameManager.getGameStats();
    private Race race;
    private String[] dnfMessages = {
            "DNF: You were so fast, you looped time and missed the finish line entirely.",
            "DNF: Tragically, your dreams finished the race. Your car did not.",
            "DNF: A true pioneer - boldly stopping where no racecar has stopped before.",
            "DNF: The good news? You avoided traffic at the finish line.",
            "DNF: You brought speed, passion, and amateur skills. Good call on the first two.",
            "DNF: Unfortunately, your GPS malfunctioned and drove you into a pit of emotional despair." };


    // Logic
    public void initialize(Race race) {
        this.race = race;
        this.race.sortParticipantsByFinishTime();
        displayResults();
    }

    private void displayResults() {
        RaceParticipant player = null;
        int playerPosition = 0;
        leaderboardHeadingLabel.setText(String.format("Race Results (#%d of %d)", gameDB.getRacesDone(), gameDB.getRaceCount()));
        for (int i = 0; i < race.getParticipants().size(); i++) {
            RaceParticipant participant = race.getParticipants().get(i);
            HBox row = createRowForParticipant(participant, i + 1, i < 3);
            leaderboardVBox.getChildren().add(row);
            if (participant.getIsPlayer()) {
                player = participant;
                playerPosition = i + 1;
                double prizeMoney = race.prizeMoneyForPosition(playerPosition);
                gameDB.setPrizeMoneyWon(gameDB.getPrizeMoneyWon() + prizeMoney);
            }
        }

        String playerCaption = "";
        if (player != null) {
            if (player.getFinishTimeSeconds() <= 0.0) {
                Random rand = new Random();
                playerCaption = dnfMessages[rand.nextInt(dnfMessages.length)];
            } else {
                playerCaption = String.format("You completed the race in %s and got %s place.",
                        GameTimer.totalSecondsToStringHourMinSec(player.getFinishTimeSeconds()),
                        intToPlacingString(playerPosition));
            }
        }
        playerResultLabel.setText(playerCaption);
    }

    public HBox createRowForParticipant(RaceParticipant participant, int position, boolean emphasise) {
        double prizeMoney = 0.0;
        String finishCaption = "DNF";
        if (participant.getFinishTimeSeconds() > 0.0 ) {
            prizeMoney = race.prizeMoneyForPosition(position);
            finishCaption = GameTimer.totalSecondsToStringHourMinSec(participant.getFinishTimeSeconds());
        }
        HBox parent = new HBox();
        parent.setSpacing(5);
        if (emphasise) {
            parent.setId("prizeWinners");
        }

        Label positionLabel = new Label();
        positionLabel.setText(intToPlacingString(position));
        positionLabel.setPrefSize(100, 0);
        parent.getChildren().add(positionLabel);

        Image carIcon = participant.getCar().getIcon();
        ImageView imageView = new ImageView(carIcon);
        imageView.setFitWidth(emphasise ? 60 : 30);
        imageView.setFitHeight(emphasise ? 30 : 15);
        HBox.setMargin(imageView, new Insets(3, 3, 0, 0));
        parent.getChildren().add(imageView);

        Label driverLabel = new Label();
        driverLabel.setText(String.format("#%d %s", participant.getEntryNumber(), participant.getDriverName()));
        driverLabel.setPrefSize(emphasise ? 320 : 350, 0); // Account for variable size of carIcon so columns line up
        parent.getChildren().add(driverLabel);

        Label finishTimeLabel = new Label();
        finishTimeLabel.setText(finishCaption);
        finishTimeLabel.setPrefSize(100, 0);
        parent.getChildren().add(finishTimeLabel);

        Label prizeMoneyLabel = new Label();
        prizeMoneyLabel.setText(String.format("$%,.2f", prizeMoney));
        prizeMoneyLabel.setPrefSize(140, 0);
        prizeMoneyLabel.setAlignment(Pos.CENTER_RIGHT);
        parent.getChildren().add(prizeMoneyLabel);

        return parent;
    }

    private String intToPlacingString(int number) {
        if ((number % 100) == 11 || (number % 100) == 12 || (number % 100) == 13) return String.format("%dth", number); // Exceptions for 11, 12, 13, 111 etc
        if ((number % 10) == 1) return String.format("%dst", number);
        if ((number % 10) == 2) return String.format("%dnd", number);
        if ((number % 10) == 3) return String.format("%drd", number);
        return String.format("%dth", number);
    }

    public void continueClicked(MouseEvent event) {
        try {
            if (gameDB.getRacesDone() >= gameDB.getRaceCount() || !gameDB.canContinuePlaying())  {
                switchToEndScreenScene(event);
            } else {
                switchToSelectRaceScene(event);
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
}