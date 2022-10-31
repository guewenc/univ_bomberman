package fr.ubx.poo.ubomb.view;

import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.model.GameObject;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Sprite {
    public static final int size = 40;
    private final Pane layer;
    private final GameObject gameObject;
    private ImageView imageView;
    private Image image;
    private Effect effect;

    public Sprite(Pane layer, Image image, GameObject gameObject) {
        this.layer = layer;
        this.image = image;
        this.gameObject = gameObject;
    }

    public GameObject getGameObject() {
        return gameObject;
    }

    public final void setImage(Image image, Effect effect) {
        if (this.image == null || this.image != image || this.effect != effect) {
            this.image = image;
            this.effect = effect;
        }
    }

    public final void setImage(Image image) {
        setImage(image, null);
    }

    public void updateImage() { }

    public Position getPosition() {
        return getGameObject().getPosition();
    }

    public final void render() {
        if (gameObject.isModified()) {
            if (imageView != null) {
                remove();
            }
            updateImage();
            imageView = new ImageView(this.image);
            imageView.setEffect(effect);
            imageView.setX(getPosition().getX() * size);
            imageView.setY(getPosition().getY() * size);
            layer.getChildren().add(imageView);
            gameObject.setModified(false);
        }
    }

    /*
     * Function used to manage the blinking of the player (when he is invincible) according to a time (here the variable x) :
     *
     *          sin(3π (x + 1/2))     1
     *  f(x) = ------------------ +  ---
     *                 5/2           5/2
     */

    public final double toSeconds(long nano) {
        return nano / 10e8;
    }

    public final double getBrightness(long invisibilityTIme) {
        double invisibilityTime = toSeconds(Math.max(invisibilityTIme, 0));
        return ((Math.sin(3 * Math.PI * (invisibilityTime + 0.5))) / (2.5)) + 0.4;
    }

    public final void remove() {
        layer.getChildren().remove(imageView);
        imageView = null;
    }
}