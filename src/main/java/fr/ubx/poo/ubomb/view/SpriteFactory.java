package fr.ubx.poo.ubomb.view;

import fr.ubx.poo.ubomb.model.GameObject;
import fr.ubx.poo.ubomb.model.decor.*;
import fr.ubx.poo.ubomb.model.bombs.*;
import fr.ubx.poo.ubomb.model.character.*;
import fr.ubx.poo.ubomb.model.doors.*;
import fr.ubx.poo.ubomb.model.bonus.*;
import fr.ubx.poo.ubomb.model.others.*;
import javafx.scene.layout.Pane;

import static fr.ubx.poo.ubomb.view.ImageResource.*;

public final class SpriteFactory {
    public static Sprite create(Pane layer, GameObject gameObject) {
        // Decor
        if (gameObject instanceof Stone)
            return new Sprite(layer, STONE.getImage(), gameObject);
        if (gameObject instanceof Tree)
            return new Sprite(layer, TREE.getImage(), gameObject);

        // Bonus
        if (gameObject instanceof Key)
            return new Sprite(layer, KEY.getImage(), gameObject);
        if (gameObject instanceof Heart)
            return new Sprite(layer, HEART.getImage(), gameObject);
        if (gameObject instanceof BombRangeInc)
            return new Sprite(layer, BONUS_BOMB_RANGE_INC.getImage(), gameObject);
        if (gameObject instanceof BombRangeDec)
            return new Sprite(layer, BONUS_BOMB_RANGE_DEC.getImage(), gameObject);
        if (gameObject instanceof BombBagCapacityInc)
            return new Sprite(layer, BONUS_BOMB_NB_INC.getImage(), gameObject);
        if (gameObject instanceof BombBagCapacityDec)
            return new Sprite(layer, BONUS_BOMB_NB_DEC.getImage(), gameObject);

        // Characters
        if (gameObject instanceof Monster) // implement spriteMonster
            return new SpriteMonster(layer, (Monster) gameObject);
        if (gameObject instanceof Princess)
            return new Sprite(layer, PRINCESS.getImage(), gameObject);
        if (gameObject instanceof Player)
            return new SpritePlayer(layer, gameObject.game.getPlayer());

        // Doors
        if(gameObject instanceof DoorPrevOpened)
            return new Sprite(layer, DOOR_OPENED.getImage(), gameObject);
        if (gameObject instanceof DoorNextOpened)
            return new Sprite(layer, DOOR_OPENED.getImage(), gameObject);
        if (gameObject instanceof DoorNextClosed)
            return new Sprite(layer, DOOR_CLOSED.getImage(), gameObject);

        // Bombs
        if (gameObject instanceof Bomb)
            return new SpriteBomb(layer, (Bomb) gameObject);
        if (gameObject instanceof Explosion)
            return new Sprite(layer, EXPLOSION.getImage(), gameObject);

        // Others
        if (gameObject instanceof Box)
            return new Sprite(layer, BOX.getImage(), gameObject);

        // Must not happen
        throw new RuntimeException("Unsupported sprite for decor " + gameObject);
    }
}