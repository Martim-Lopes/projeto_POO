package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class Rock extends GameObject {


    public Rock(Point2D position) {
        super(position, "rock", 10);
    }

    @Override
    public boolean isPassable() {
        return false;
    }

    @Override
    public boolean isPushable() {
        return true;
    }
}
