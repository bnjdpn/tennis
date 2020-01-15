package fr.arolla.tennis.game;

import fr.arolla.tennis.player.Player;
import fr.arolla.tennis.player.Winner;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public abstract class BaseGame<T extends BasePlayerScore> {

    private final T playerScore1;
    private final T playerScore2;

    private Optional<Winner> winner;

    public BaseGame(T playerScore1, T playerScore2) {
        this.playerScore1 = playerScore1;
        this.playerScore2 = playerScore2;
        this.winner = Optional.empty();
    }

    protected abstract Optional<Winner> managePoint(Player player);

    public T getPlayerScore1() {
        return playerScore1;
    }

    public T getPlayerScore2() {
        return playerScore2;
    }

    public Optional<Winner> markPoint(Player player) {

        if (winner.isPresent()) {
            throw new UnsupportedOperationException(winner + " is already winner of the game");
        }

        winner = managePoint(player);

        return winner;
    }

    public Optional<Winner> getWinner() {
        return winner;
    }

    public T getPlayerScore(Player player) {
        return findPlayerScore(standardPlayerScore -> standardPlayerScore.getPlayer().equals(player));
    }

    public T getOpponentScore(Player player) {
        return findPlayerScore(standardPlayerScore -> !standardPlayerScore.getPlayer().equals(player));
    }

    private T findPlayerScore(Predicate<T> predicate) {
        return Stream.of(playerScore1, playerScore2)
                .filter(predicate)
                .findFirst()
                .orElseThrow();
    }
}
