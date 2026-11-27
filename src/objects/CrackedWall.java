package objects;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class CrackedWall extends GameObject {

    public CrackedWall(Point2D position) {
        super(position, "cracked-wall", 10);
    }
}
