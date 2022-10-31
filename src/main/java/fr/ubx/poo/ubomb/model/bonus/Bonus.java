package fr.ubx.poo.ubomb.model.bonus;

import fr.ubx.poo.ubomb.game.EntityCode;
import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.model.decor.Decor;

import java.util.List;

import static fr.ubx.poo.ubomb.game.EntityCode.*;

public abstract class Bonus extends Decor {
    public Bonus(Position position) {super(position, List.of(Player, Monster)); }

    @Override
    public boolean isEntity(EntityCode entity) {
        return false;
    }

    @Override
    public boolean canExplode() {
        return true;
    }
}