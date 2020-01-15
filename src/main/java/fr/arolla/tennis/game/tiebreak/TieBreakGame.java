package fr.arolla.tennis.game.tiebreak;

import fr.arolla.tennis.TwoPointsAheadPredicate;
import fr.arolla.tennis.game.BaseGame;
import fr.arolla.tennis.player.Player;
import fr.arolla.tennis.player.Winner;

import java.util.Optional;

public final class TieBreakGame extends BaseGame<TieBreakPlayerScore> {
    private static final int NUMBER_OF_POINTS_REQUIRED_TO_WIN = 7;

    private final TwoPointsAheadPredicate twoPointsAheadPredicate;

    public TieBreakGame(Player player1, Player player2) {
        super(new TieBreakPlayerScore(player1), new TieBreakPlayerScore(player2));
        this.twoPointsAheadPredicate = new TwoPointsAheadPredicate();
    }

    @Override
    protected Optional<Winner> managePoint(Player player) {

        TieBreakPlayerScore playerScore = getPlayerScore(player);
        TieBreakPlayerScore opponentScore = getOpponentScore(player);

        playerScore.increaseScore();

        if (haveAtLeastSevenPoints(playerScore) && isTwoMorePointsThanOpponent(playerScore, opponentScore)) {
            return Optional.of(new Winner(player));
        }

        return Optional.empty();
    }

    private boolean isTwoMorePointsThanOpponent(TieBreakPlayerScore playerScore, TieBreakPlayerScore opponentScore) {
        return twoPointsAheadPredicate.test(playerScore.getScore(), opponentScore.getScore());
    }

    private boolean haveAtLeastSevenPoints(TieBreakPlayerScore playerScore) {
        return playerScore.getScore() >= NUMBER_OF_POINTS_REQUIRED_TO_WIN;
    }
}
