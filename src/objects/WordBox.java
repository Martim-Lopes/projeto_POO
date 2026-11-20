package objects;

import pt.iscte.poo.utils.Point2D;

public class WordBox extends GameObject {


    public WordBox(Point2D position) {
        super(position, "word-box", 10);
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
