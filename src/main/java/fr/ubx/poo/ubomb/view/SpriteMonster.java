package fr.ubx.poo.ubomb.view;

import fr.ubx.poo.ubomb.game.Direction;
import fr.ubx.poo.ubomb.model.character.Monster;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class SpriteMonster extends Sprite {
    private final ColorAdjust effect = new ColorAdjust();

    public SpriteMonster(Pane layer, Monster monster) {
        super(layer, null, monster);
        updateImage();
    }

    @Override
    public void updateImage() {
        Monster monster = (Monster) getGameObject();
        Image image = getImage(monster.getDirection());
        effect.setBrightness(getBrightness(monster.getInvisibilityTime()));

        setImage(image, effect);
    }

    public Image getImage(Direction direction) {
        return ImageResource.getMonster(direction);
    }
}