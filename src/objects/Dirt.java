package objects;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class Dirt extends GameObject {

    public Dirt(Point2D position) {
        super(position, "dirt", 5);
    }

    @Override
    public void onFallInHole(Hole hole, Room room) {

    }
}
