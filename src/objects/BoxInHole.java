package objects;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Point2D;

public class BoxInHole extends GameObject{

    public BoxInHole(Point2D position) {
        super(position, "box-in-hole", 20);
    }

    @Override
    public void onFallInHole(Hole hole, Room room) {}
}
