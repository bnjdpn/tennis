package fr.arolla.tennis.game.tiebreak;

import fr.arolla.tennis.player.Player;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class TieBreakGameTest {

    @Test
    void none_player_should_wins_the_game_when_none_score_below_7() {
        // Given
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");

        TieBreakGame tieBreakGame = new TieBreakGame(player1, player2);

        // When
        IntStream.range(0, 5)
                .forEach(i -> {
                    tieBreakGame.markPoint(player1);
                    tieBreakGame.markPoint(player2);
                });

        // Then
        assertThat(tieBreakGame.getWinner()).isEmpty();
    }

    @Test
    void player1_should_wins_when_reach_7_points_and_2_more_than_opponent() {
        // Given
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");

        TieBreakGame tieBreakGame = new TieBreakGame(player1, player2);

        // When
        IntStream.range(0, 5)
                .mapToObj(i -> player2)
                .forEach(tieBreakGame::markPoint);
        IntStream.range(0, 7)
                .mapToObj(i -> player1)
                .forEach(tieBreakGame::markPoint);

        // Then
        assertThat(tieBreakGame.getWinner())
                .map(Player::getName)
                .isEqualTo(Optional.of(player1.getName()));
        assertThat(tieBreakGame.getPlayerScore2().getScore()).isEqualTo(5);
        assertThat(tieBreakGame.getPlayerScore1().getScore()).isEqualTo(7);
    }

    @Test
    void player1_should_not_wins_when_mark_7_points_and_opponent_6() {
        // Given
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");

        TieBreakGame tieBreakGame = new TieBreakGame(player1, player2);

        // When
        IntStream.range(0, 6)
                .mapToObj(i -> player2)
                .forEach(tieBreakGame::markPoint);
        IntStream.range(0, 7)
                .mapToObj(i -> player1)
                .forEach(tieBreakGame::markPoint);

        // Then
        assertThat(tieBreakGame.getWinner()).isEmpty();

        // When
        tieBreakGame.markPoint(player1);

        // Then
        assertThat(tieBreakGame.getWinner())
                .map(Player::getName)
                .isEqualTo(Optional.of(player1.getName()));
    }


}
