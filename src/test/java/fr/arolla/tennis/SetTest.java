package fr.arolla.tennis;

import fr.arolla.tennis.player.Player;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class SetTest {

    @Test
    void none_player_should_win_the_set() {
        // Given
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");

        Set set = new Set(player1, player2);

        // When
        set.markPoint(player1);
        set.markPoint(player2);

        // Then
        assertThat(set.getWinner()).isEmpty();
    }

    @Test
    void player1_should_wins_set_when_wins_6_games() {
        // Given
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");

        Set set = new Set(player1, player2);

        // When
        winStandardGames(set, player1, 6);

        // Then
        assertThat(set.getWinner())
                .map(Player::getName)
                .isEqualTo(Optional.of(player1.getName()));
    }

    @Test
    void player1_should_wins_set_when_wins_7_games_and_opponent_5() {
        // Given
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");

        Set set = new Set(player1, player2);

        // When
        winStandardGames(set, player1, 5);
        winStandardGames(set, player2, 5);
        winStandardGames(set, player1, 2);

        // Then
        assertThat(set.getWinner())
                .map(Player::getName)
                .isEqualTo(Optional.of(player1.getName()));
    }

    @Test
    void player1_should_wins_set_with_tie_break() {
        // Given
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");

        Set set = new Set(player1, player2);

        // When
        winStandardGames(set, player1, 5);
        winStandardGames(set, player2, 6);
        winStandardGames(set, player1, 1);
        winTieBreak(set, player1);

        // Then
        assertThat(set.getWinner())
                .map(Player::getName)
                .isEqualTo(Optional.of(player1.getName()));
    }

    private void winStandardGames(Set set, Player player, int numberOfGamesToWin) {
        winGame(set, player, 4 * numberOfGamesToWin);
    }

    private void winTieBreak(Set set, Player player) {
        winGame(set, player, 7);
    }

    private void winGame(Set set, Player player, int numberOfPoints) {
        IntStream.range(0, numberOfPoints)
                .mapToObj(i -> player)
                .forEach(set::markPoint);
    }

}
