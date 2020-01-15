package fr.arolla.tennis;

import fr.arolla.tennis.game.BaseGame;
import fr.arolla.tennis.game.BasePlayerScore;
import fr.arolla.tennis.game.standard.StandardGame;
import fr.arolla.tennis.game.tiebreak.TieBreakGame;
import fr.arolla.tennis.player.Player;
import fr.arolla.tennis.player.Winner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public final class Set {
    private static final int NUMBER_OF_GAMES_REQUIRED_TO_WIN = 6;

    private final Player player1;
    private final Player player2;

    private final TwoPointsAheadPredicate twoPointsAheadPredicate;

    private final List<BaseGame<? extends BasePlayerScore>> finishedGames;
    private BaseGame<? extends BasePlayerScore> currentGame;

    private Optional<Winner> winner;

    public Set(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.winner = Optional.empty();
        this.finishedGames = new ArrayList<>();
        this.twoPointsAheadPredicate = new TwoPointsAheadPredicate();
    }

    public Optional<Winner> markPoint(Player player) {

        if (winner.isPresent()) {
            throw new UnsupportedOperationException(winner + " is already winner of the set");
        }

        currentGame = Optional.<BaseGame<? extends BasePlayerScore>>ofNullable(currentGame)
                .orElseGet(this::newGame);

        currentGame.markPoint(player)
                .ifPresent(gameWinner -> {
                    finishedGames.add(currentGame);
                    if (haveWinTheSet(player)) {
                        winner = Optional.of(new Winner(player));
                    }
                    currentGame = null;
                });

        return winner;
    }

    private BaseGame<? extends BasePlayerScore> newGame() {
        if (isTieBreak()) {
            return new TieBreakGame(player1, player2);
        } else {
            return new StandardGame(player1, player2);
        }
    }

    private boolean isTieBreak() {
        long numberOfGamesWinnedByPlayer1 = countGamesWonByPlayer(player1);
        long numberOfGamesWinnedByPlayer2 = countGamesWonByPlayer(player2);

        return numberOfGamesWinnedByPlayer1 == NUMBER_OF_GAMES_REQUIRED_TO_WIN &&
                numberOfGamesWinnedByPlayer2 == NUMBER_OF_GAMES_REQUIRED_TO_WIN;
    }

    private long countGamesWonByPlayer(Player player) {
        return finishedGames.stream()
                .filter(game -> game.getWinner()
                        .map(Player::getName)
                        .filter(winnerName -> winnerName.equals(player.getName()))
                        .isPresent())
                .count();
    }

    private boolean haveWinTheSet(Player player) {

        if (currentGame instanceof TieBreakGame) {
            return currentGame.getWinner()
                    .map(Player::getName)
                    .filter(winnerName -> winnerName.equals(player.getName()))
                    .isPresent();
        }

        Player opponent = Stream.of(player1, player2)
                .filter(aPlayer -> !aPlayer.equals(player))
                .findFirst()
                .orElseThrow();

        long numberOfGamesWonByPlayer = countGamesWonByPlayer(player);
        long numberOfGamesWonByOpponent = countGamesWonByPlayer(opponent);

        return numberOfGamesWonByPlayer >= NUMBER_OF_GAMES_REQUIRED_TO_WIN
                && twoPointsAheadPredicate.test(numberOfGamesWonByPlayer, numberOfGamesWonByOpponent);
    }

    public Optional<Winner> getWinner() {
        return winner;
    }
}
