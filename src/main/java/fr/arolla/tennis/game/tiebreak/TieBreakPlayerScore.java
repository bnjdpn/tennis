package fr.arolla.tennis.game.tiebreak;

import fr.arolla.tennis.game.BasePlayerScore;
import fr.arolla.tennis.player.Player;

public final class TieBreakPlayerScore extends BasePlayerScore {

    private int score;

    public TieBreakPlayerScore(Player player) {
        super(player);
    }

    @Override
    public void increaseScore() {
        score++;
    }

    public int getScore() {
        return score;
    }
}
