package objects;

import pt.iscte.poo.game.GameEngine;
import pt.iscte.poo.game.Room;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

import java.util.ArrayList;
import java.util.List;

public class Baba extends GameObject {

    private String direction;
    private boolean alive = true;

    public Baba(Point2D position, String direction) {
        super(position, "baba-" + direction, 25);
        this.direction = direction;
    }

    @Override
    public String getName() {
        return "baba-" + direction;
    }

    public boolean isAlive() {
        return alive;
    }

    public void kill() {
        alive = false;

    }

    public boolean move(Direction d, Room room) {
        if (!alive) {
            return false;
        }
        direction = letterFor(d);
        Point2D next = getPosition().plus(d.asVector());
        if (room.canMove(next)) {
            setPosition(next);
            if (room.isLethal(next)) {
                room.killPlayer();
            }
            return true;
        }
        List<Pushable> chain = new ArrayList<>();
        Point2D cursor = next;
        while (room.canPush(cursor)) {
            chain.add(room.getPushableObject(cursor));
            cursor = cursor.plus(d.asVector());
        }
        if (!chain.isEmpty() && room.canMove(cursor)) {
            for (Pushable obj : chain) {
                obj.setPosition(obj.getPosition().plus(d.asVector()));
                room.tryPutBox(chain);
            }
            setPosition(next);
            if (room.isLethal(next)) {
                room.killPlayer();
            }
            return true;
        }
        return false;
    }

    private static String letterFor(Direction d) {
        switch (d) {
            case UP:
                return "u";
            case DOWN:
                return "d";
            case LEFT:
                return "l";
            default:
                return "r";
        }
    }
}
