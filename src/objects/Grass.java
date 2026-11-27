package objects;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class Grass extends GameObject {

    public Grass(Point2D position) {
        super(position, "grass", 10);
    }
}
