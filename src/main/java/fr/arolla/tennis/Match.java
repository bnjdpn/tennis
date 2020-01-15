package fr.arolla.tennis;

import fr.arolla.tennis.player.Player;
import fr.arolla.tennis.player.Winner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Match {
    private static final int NUMBER_OF_SETS_REQUIRED_TO_WIN = 3;

    private final Player player1;
    private final Player player2;

    private final List<Set> finishedSets;
    private Set currentSet;

    private Optional<Winner> winner;

    public Match(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.winner = Optional.empty();
        this.finishedSets = new ArrayList<>();
    }

    public String getStatus() {
        return winner.map(Player::getName)
                .map(playerName -> playerName + " wins")
                .orElse("in progress");
    }

    public void markPoint(Player player) {

        if (winner.isPresent()) {
            throw new UnsupportedOperationException(winner + "is already winner of the match");
        }

        currentSet = Optional.ofNullable(currentSet)
                .orElseGet(() -> new Set(player1, player2));

        currentSet.markPoint(player)
                .ifPresent(setWinner -> {
                    finishedSets.add(currentSet);
                    currentSet = null;
                    if (haveWinTheMatch(player)) {
                        winner = Optional.of(new Winner(player));
                    }
                });
    }

    private boolean haveWinTheMatch(Player player) {
        return finishedSets.stream()
                .filter(set -> set.getWinner()
                        .map(Player::getName)
                        .filter(setWinnerName -> setWinnerName.equals(player.getName()))
                        .isPresent())
                .count() >= NUMBER_OF_SETS_REQUIRED_TO_WIN;
    }
}
