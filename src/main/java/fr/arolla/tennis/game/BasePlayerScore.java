package fr.arolla.tennis.game;

import fr.arolla.tennis.player.Player;

public abstract class BasePlayerScore {

    private final Player player;

    public BasePlayerScore(Player player) {
        this.player = player;
    }

    public abstract void increaseScore();

    public Player getPlayer() {
        return player;
    }

}
