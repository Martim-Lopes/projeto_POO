package objects;

import pt.iscte.poo.utils.Point2D;

public class Box extends GameObject {
    public Box(Point2D position) {
        super(position, "box", 10);
    }

    @Override
    public boolean isPassable() {
        return false;
    }
}
