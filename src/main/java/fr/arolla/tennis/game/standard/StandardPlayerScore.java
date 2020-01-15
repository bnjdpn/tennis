package fr.arolla.tennis.game.standard;

import fr.arolla.tennis.game.BasePlayerScore;
import fr.arolla.tennis.player.Player;

import java.util.List;

import static fr.arolla.tennis.game.standard.StandardPlayerScore.StandardScore.ZERO;

public final class StandardPlayerScore extends BasePlayerScore {

    private StandardScore score;
    private boolean advantage;

    public StandardPlayerScore(Player player) {
        super(player);
        this.score = ZERO;
    }

    @Override
    public void increaseScore() {
        this.score = score.increaseScore();
    }

    public void gainAdvantage() {
        advantage = true;
    }

    public void looseAdvantage() {
        advantage = false;
    }

    public StandardScore getScore() {
        return score;
    }

    public boolean hasAdvantage() {
        return advantage;
    }

    public enum StandardScore {
        ZERO, FIFTEEN, THIRTY, FORTY;

        private static final List<StandardScore> SCORE_VALUES_SEQUENCE = List.of(ZERO, FIFTEEN, THIRTY, FORTY);

        public StandardScore increaseScore() {
            return SCORE_VALUES_SEQUENCE.stream()
                    .skip(SCORE_VALUES_SEQUENCE.indexOf(this) + 1L)
                    .findFirst()
                    .orElseThrow();
        }

    }
}
