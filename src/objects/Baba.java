package objects;

import pt.iscte.poo.game.Room;
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
        // avancamos com o baba antes de empurrar, isso faz a bandeira ser empurravel indiretamente e conseguirmos entrar nela para ganhar
        // o baba entra na bandeira antes de empurrar e passa logo de nivel
        if (room.canMove(next)) {
            setPosition(next);
            room.triggerInteractions(next);
            return true;
        }
        List<Pushable> chain = new ArrayList<>();
        Point2D cursor = next;
        int consecutiveWords = 0;
        while (room.canPush(cursor)) {
            Pushable push = room.getPushableObject(cursor);
            if (push instanceof WordObject) {
                consecutiveWords++;
                if (consecutiveWords > 1) {
                    break;
                }
            }
            else{
                consecutiveWords = 0;
            }
            chain.add(push);
            cursor = cursor.plus(d.asVector());
        }
        if (!chain.isEmpty() && room.canMove(cursor)) {
            // utilizar for com i em vez de : porque assim podemos usar o i == 0 no onPushed
            for (int i = 0; i < chain.size(); i++) {
                Pushable obj = chain.get(i);
                obj.setPosition(obj.getPosition().plus(d.asVector()));
                // i == 0 significa que e a primeira posicao. Se for a primeira posicao significa que foi empurrado diretamente pelo baba
                obj.onPushed(i == 0);
            }
            room.handleHoleInteractions(chain);
            room.updateRules();
            setPosition(next);
            room.triggerInteractions(next);
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
