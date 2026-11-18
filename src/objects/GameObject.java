package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public abstract class GameObject implements ImageTile {

    private Point2D position;
    private final String imageName;
    private final int layer;

    public GameObject(Point2D position, String imageName, int layer) {
        this.position = position;
        this.imageName = imageName;
        this.layer = layer;
    }

    @Override
    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return imageName;
    }

    @Override
    public int getLayer() {
        return layer;
    }

    public boolean isPassable() {
        return true;
    }



}