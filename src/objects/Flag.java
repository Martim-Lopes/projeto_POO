package objects;

import pt.iscte.poo.utils.Point2D;

public class Flag extends GameObject{
    public Flag(Point2D position) {
        super(position, "flag", 10);
    }

    @Override
    public boolean isPassable() {
        return false;
    }
}
