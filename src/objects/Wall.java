package objects;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class Wall extends  GameObject {

    public Wall(Point2D position) {
        super(position, "wall", 10);
    }

    @Override
    public boolean isPassable() {
        return false;
    }

    @Override
    public void onFallInHole(Hole hole, Room room) {

    }
}
