package fr.ubx.poo.ubomb.view;

import fr.ubx.poo.ubomb.model.bombs.*;
import javafx.scene.layout.Pane;

import static fr.ubx.poo.ubomb.view.ImageResource.getBombImage;

public class SpriteBomb extends Sprite {
    private final Bomb bomb;
    private int bombState;

    public SpriteBomb(Pane layer, Bomb bomb) {
        super(layer, getBombImage(bomb.getBombState()), bomb);
        this.bomb = bomb;
        bombState = bomb.getBombState();
    }

    @Override
    public void updateImage() {
        if(bombState != bomb.getBombState())
            bombState = bomb.getBombState();
        setImage(ImageResource.getBombImage(bombState));
    }
}