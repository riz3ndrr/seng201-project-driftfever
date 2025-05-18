package seng201.team0.models;


import java.util.ArrayList;
import java.util.List;

public class RaceCommentary {
    // Properties
    List<RaceComment> comments = new ArrayList<>();


    // Logic
    public void add(RaceComment comment) {
        comments.add(comment);
    }

    public List<RaceComment> getCommentsForParticipant(RaceParticipant participant) {
        List<RaceComment> result = new ArrayList<>();
        for (RaceComment comment : comments) {
            if (comment.participant == participant) {
                result.add(comment);
            }
        }
        return result;
    }
}
