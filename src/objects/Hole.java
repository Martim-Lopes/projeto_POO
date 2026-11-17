package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class Hole implements ImageTile {

    private Point2D position;
    private boolean withBox = false;

    public Hole(Point2D position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "hole";
    }

    @Override
    public Point2D getPosition() {
        return position;
    }

    @Override
    public int getLayer() {
        return 10;
    }

    public void putBox() {
        withBox = true;
    }

    public boolean hasBox(){
        return withBox;
    }


}
