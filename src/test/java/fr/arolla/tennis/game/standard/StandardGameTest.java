package fr.arolla.tennis.game.standard;

import fr.arolla.tennis.player.Player;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class StandardGameTest {

    @Test
    void none_player_should_wins_the_game_when_none_score_40() {
        // Given
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");

        StandardGame standardGame = new StandardGame(player1, player2);

        // When
        standardGame.markPoint(player1);
        standardGame.markPoint(player2);

        // Then
        assertThat(standardGame.getWinner()).isEmpty();
    }

    @Test
    void player1_should_wins_the_game_after_player1_has_40_and_scores() {
        // Given
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");

        StandardGame standardGame = new StandardGame(player1, player2);

        // When
        standardGame.markPoint(player1);
        standardGame.markPoint(player1);
        standardGame.markPoint(player1);
        standardGame.markPoint(player1);

        // Then
        assertThat(standardGame.getWinner())
                .map(Player::getName)
                .isEqualTo(Optional.of(player1.getName()));
    }

    @Test
    void player1_should_wins_the_game_when_winning_advantage_in_deuce() {
        // Given
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");

        StandardGame standardGame = new StandardGame(player1, player2);

        // When
        makeGameInDeuceState(player1, player2, standardGame);
        standardGame.markPoint(player1);
        standardGame.markPoint(player1);

        // Then
        assertThat(standardGame.getWinner())
                .map(Player::getName)
                .isEqualTo(Optional.of(player1.getName()));
    }

    @Test
    void player1_should_loose_advantage_when_player2_scores_in_deuce() {
        // Given
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");

        StandardGame standardGame = new StandardGame(player1, player2);

        // When
        makeGameInDeuceState(player1, player2, standardGame);
        standardGame.markPoint(player1);

        // Then
        assertThat(standardGame.getPlayerScore1().hasAdvantage()).isTrue();
        assertThat(standardGame.getPlayerScore2().hasAdvantage()).isFalse();
        assertThat(standardGame.getWinner()).isEmpty();

        // When
        standardGame.markPoint(player2);

        // Then
        assertThat(standardGame.getPlayerScore1().hasAdvantage()).isFalse();
        assertThat(standardGame.getPlayerScore2().hasAdvantage()).isFalse();
        assertThat(standardGame.getWinner()).isEmpty();
    }

    private void makeGameInDeuceState(Player player1, Player player2, StandardGame standardGame) {
        standardGame.markPoint(player1);
        standardGame.markPoint(player2);
        standardGame.markPoint(player1);
        standardGame.markPoint(player2);
        standardGame.markPoint(player1);
        standardGame.markPoint(player2);
    }
}
