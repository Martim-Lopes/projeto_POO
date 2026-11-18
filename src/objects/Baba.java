package objects;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

import java.util.Random;

public class Baba implements ImageTile {

    private Point2D position;
    private String direction;

    public Baba(Point2D position, String direction) {
        this.position = position;
        this.direction = direction;
    }

    @Override
    public String getName() {
        return "baba-" + direction;
    }

    @Override
    public Point2D getPosition() {
        return position;
    }

    @Override
    public int getLayer() {
        return 25;
    }

    public void move(Direction d, Room room) {
        direction = letterFor(d);
        Point2D next = position.plus(d.asVector());
        position = next;
    }

    private static String letterFor(Direction d) {
        switch (d) {
            case UP: return "u";
            case DOWN: return "d";
            case LEFT: return "l";
            default: return "r";
        }
    }
}
