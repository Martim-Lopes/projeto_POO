package objects;

import pt.iscte.poo.utils.Point2D;

public class WordWin extends GameObject {

    public WordWin(Point2D position) {
        super(position, "word-win", 10);
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


