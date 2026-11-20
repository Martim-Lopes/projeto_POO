package objects;

import pt.iscte.poo.utils.Point2D;

public class WordMove extends GameObject {

    public WordMove(Point2D position) {
        super(position, "word-move", 10);
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
