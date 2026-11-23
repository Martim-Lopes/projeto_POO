package objects;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

import java.io.Serializable;

public class Rock extends GameObject implements Pushable{


    public Rock(Point2D position) {
        super(position, "rock", 10);
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
    public void onPushed(boolean direct) {}

}
