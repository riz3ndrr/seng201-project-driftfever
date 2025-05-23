package seng201.team0.models;


import java.util.ArrayList;
import java.util.List;

/**
 * RaceCommentary is the list of events that have happened to race
 * participants throughout a race.
 * Commentary can be filtered down to a can by clicking it.
 */
public class RaceCommentary {
    // Properties
    List<RaceComment> comments = new ArrayList<>();


    // Logic
    public void add(RaceComment comment) {
        comments.add(comment);
    }

    /**
     * Collect a list of race comments of a particular player to be displayed in the commentary box.
     * If no participant is selected, it will display the comments relating to ALL players.
     * @param participant which is the participant the user wishes to know news about.
     * @return a list of race comments regarding that player's updates on the race.
     */
    public List<RaceComment> getCommentsForParticipant(RaceParticipant participant) {
        if (participant == null) {
            return comments;
        } else {
            List<RaceComment> result = new ArrayList<>();
            for (RaceComment comment : comments) {
                if (comment.participant == participant) {
                    result.add(comment);
                }
            }
            return result;
        }
    }
}
