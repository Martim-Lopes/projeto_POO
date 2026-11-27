package objects;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Point2D;

public interface Pushable {

    boolean canBePushed(Room room);
    void onPushed(boolean direct);
    Point2D getPosition();
    void setPosition(Point2D position);
    void onFallInHole(Hole hole, Room room);
}
