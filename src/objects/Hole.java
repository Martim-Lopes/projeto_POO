package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class Hole extends  GameObject {

    private boolean withBox = false;

    public Hole(Point2D position) {
        super(position, "hole", 10);
    }

    public void putBox() {
        withBox = true;
    }

    public boolean hasBox(){
        return withBox;
    }

    @Override
    public boolean isPassable() {
        return false;
    }


}
