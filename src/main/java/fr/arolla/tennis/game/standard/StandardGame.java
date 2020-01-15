package fr.arolla.tennis.game.standard;

import fr.arolla.tennis.game.BaseGame;
import fr.arolla.tennis.player.Player;
import fr.arolla.tennis.player.Winner;

import java.util.Optional;

import static fr.arolla.tennis.game.standard.StandardPlayerScore.StandardScore.FORTY;


public final class StandardGame extends BaseGame<StandardPlayerScore> {

    public StandardGame(Player player1, Player player2) {
        super(
                new StandardPlayerScore(player1),
                new StandardPlayerScore(player2)
        );
    }

    @Override
    public Optional<Winner> managePoint(Player player) {

        StandardPlayerScore playerScore = getPlayerScore(player);
        StandardPlayerScore opponentScore = getOpponentScore(player);

        if (isInDeuceState(playerScore, opponentScore)) {
            return manageDeucePoint(playerScore, opponentScore);
        } else {
            return manageStandardPoint(playerScore);
        }
    }

    private Optional<Winner> manageStandardPoint(StandardPlayerScore playerScore) {
        if (FORTY.equals(playerScore.getScore())) {
            return Optional.of(new Winner(playerScore.getPlayer()));
        } else {
            playerScore.increaseScore();
            return Optional.empty();
        }
    }

    private Optional<Winner> manageDeucePoint(StandardPlayerScore playerScore, StandardPlayerScore opponentScore) {
        if (playerScore.hasAdvantage()) {
            return Optional.of(new Winner(playerScore.getPlayer()));
        } else if (opponentScore.hasAdvantage()) {
            opponentScore.looseAdvantage();
        } else {
            playerScore.gainAdvantage();
        }
        return Optional.empty();
    }

    private boolean isInDeuceState(StandardPlayerScore playerScore, StandardPlayerScore opponentScore) {
        return FORTY.equals(playerScore.getScore()) && FORTY.equals(opponentScore.getScore());
    }
}
