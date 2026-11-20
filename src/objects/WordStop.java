package objects;

import pt.iscte.poo.utils.Point2D;

import javax.swing.text.Position;

public class WordStop extends GameObject {

    public WordStop(Point2D position) {
        super(position, "word-stop", 10);
    }

    @Override
    public boolean isPassable() {
        return false;
    }

    @Override
    public boolean isPushable() {
        return true;
    }
}
