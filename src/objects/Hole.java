package objects;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class Hole extends  GameObject implements Interactive {

    public Hole(Point2D position) {
        super(position, "hole", 10);
    }

    @Override
    public boolean isLethal() {
        return true;
    }

    @Override
    public void onPlayerEnter(Room room) {
        room.killPlayer();
    }
}
