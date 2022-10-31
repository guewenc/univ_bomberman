package fr.ubx.poo.ubomb.view;

import fr.ubx.poo.ubomb.game.Direction;
import fr.ubx.poo.ubomb.model.character.Player;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class SpritePlayer extends Sprite {
    private final ColorAdjust effect = new ColorAdjust();

    public SpritePlayer(Pane layer, Player player) {
        super(layer, null, player);
        updateImage();
    }

    @Override
    public void updateImage() {
        Player player = (Player) getGameObject();
        Image image = getImage(player.getDirection());
        effect.setBrightness(getBrightness(player.getInvisibilityTime()));
        
        setImage(image, effect);
    }

    public Image getImage(Direction direction) {
        return ImageResource.getPlayer(direction);
    }
}