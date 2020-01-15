package fr.arolla.tennis;

import fr.arolla.tennis.player.Player;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class MatchTest {

    @Test
    void none_player_should_win_the_match() {
        // Given
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");

        Match match = new Match(player1, player2);

        // When
        match.markPoint(player1);
        match.markPoint(player2);

        // Then
        assertThat(match.getStatus()).isEqualTo("in progress");
    }

    @Test
    void player1_should_win_the_match() {
        // Given
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");

        Match match = new Match(player1, player2);

        // When
        IntStream.range(0, 4 * 6 * 3)
                .mapToObj(i -> player1)
                .forEach(match::markPoint);

        // Then
        assertThat(match.getStatus()).isEqualTo("Player 1 wins");
    }

}
