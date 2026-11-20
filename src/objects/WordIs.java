package objects;

import pt.iscte.poo.utils.Point2D;

public class WordIs extends GameObject {

    public WordIs(Point2D position) {
        super(position, "word-is", 10);
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
