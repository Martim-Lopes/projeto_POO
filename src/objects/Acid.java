package objects;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Point2D;

public class Acid extends GameObject implements Pushable{

    private boolean pushed = false;


    public Acid(Point2D position) { super(position, "acid", 20);}


    @Override
    public boolean canBePushed(Room room) {
        return true;
    }

    @Override
    public void onPushed(boolean direct) {
        pushed = direct;
    }

    @Override
    public boolean isPassable() {
        return false;
    }

    public boolean wasDirectPush() {
        return pushed;
    }

    @Override
    public void onFallInHole(Hole hole, Room room) {
    }
}
