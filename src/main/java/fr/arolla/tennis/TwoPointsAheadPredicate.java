package fr.arolla.tennis;

import java.util.function.BiPredicate;

public final class TwoPointsAheadPredicate implements BiPredicate<Number, Number> {

    @Override
    public boolean test(Number scorePlayer1, Number scorePlayer2) {
        int score1 = scorePlayer1.intValue();
        int score2 = scorePlayer2.intValue();
        return Math.abs((score1 - score2) * (score2 - score1)) >= 2;
    }
}
