package fr.ubx.poo.ubomb.game;

import fr.ubx.poo.ubomb.model.GameObject;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;

public class Grid {
    private final int width;
    private final int height;

    private final Map<Position, GameObject> elements;

    public Grid(int width, int height) {
        this.width = width;
        this.height = height;
        this.elements = new Hashtable<>();
    }

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }

    public GameObject get(Position position) {
        return elements.get(position);
    }

    public void set(Position position, GameObject object) {
        if (object != null)
            elements.put(position, object);
    }

    public void remove(Position position, GameObject object) {
        elements.remove(position, object);
    }
    public Collection<GameObject> values() {
        return elements.values();
    }
}