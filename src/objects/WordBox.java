package objects;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Point2D;

public class WordBox extends WordObject {


    public WordBox(Point2D position) {
        super(position, "word-box", WordType.BOX);
    }
}
