package objects;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Point2D;

public class Box extends GameObject implements Pushable, Interactive {

    public Box(Point2D position) {
        super(position, "box", 10);
    }

    @Override
    public boolean isPassable() {
        return false;
    }

    @Override
    public boolean canBePushed(Room room) {
        return true;
    }

    @Override
    public void onPushed(boolean direct) {

    }

    @Override
    public void onPlayerEnter(Room room) {}
}
