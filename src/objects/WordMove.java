package objects;

import pt.iscte.poo.utils.Point2D;

public class WordMove extends WordObject {

    public WordMove(Point2D position) {
        super(position, "word-move", WordType.MOVE);
    }

}
