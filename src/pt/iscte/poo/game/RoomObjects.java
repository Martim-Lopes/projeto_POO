package pt.iscte.poo.game;

import objects.*;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public enum RoomObjects {

    BABA("baba"){
        @Override
        public GameObject create(Point2D pos, String direction) {
            if (direction == null || direction.isEmpty()) {
                throw new IllegalArgumentException("Direcao em falta para criar Baba");
            }
            return new Baba(pos, direction);
        }
    },
    ROCK("rock"){
        @Override
        public GameObject create(Point2D position, String direction) {
            return new Rock(position);
        }
    },
    BOMB("bomb"){
        @Override
        public GameObject create(Point2D position, String direction) {
            return new Bomb(position);
        }
    },
    BOX("box"){
        @Override
        public GameObject create(Point2D position, String direction) {
            return new Box(position);
        }
    },
    FLAG("flag"){
        @Override
        public GameObject create(Point2D position, String direction) {
            return new Flag(position);
        }
    },
    WORDBOX("wordBox"){
        @Override
        public GameObject create(Point2D position, String direction) {
            return new WordBox(position);
        }
    },
    WORDIS("wordIs"){
        @Override
        public GameObject create(Point2D position, String direction) {
            return new WordIs(position);
        }
    },
    WORDWIN("wordWin"){
        @Override
        public GameObject create(Point2D position, String direction) {
            return new WordWin(position);
        }
    },
    WORDMOVE("wordMove"){
        @Override
        public GameObject create(Point2D position, String direction) {
            return new WordMove(position);
        }
    },
    WORDSTOP("wordStop"){
        @Override
        public GameObject create(Point2D position, String direction) {
            return new WordStop(position);
        }
    };

    private final String tag;

    RoomObjects(String tag) {
        this.tag = tag;

    }

    public abstract GameObject create(Point2D position, String direction);

    public static RoomObjects fromString(String tag) {
        for (RoomObjects obj : values()) {
            if (obj.tag.equals(tag)) {
                return obj;
            }
        }
        throw new IllegalArgumentException("Objeto desconhecido no ficheiro: " + tag);
    }

    public static GameObject createFromLine(String line) {
        String[] parts = line.split(",");
        if (parts.length < 3) {
            throw new IllegalArgumentException("Linha de objeto invalida: " + line);
        }
        String type = parts[0].trim();
        int x = Integer.parseInt(parts[1].trim());
        int y = Integer.parseInt(parts[2].trim());
        String direction = (parts.length >= 4) ? parts[3].trim() : null;
        RoomObjects objectType = fromString(type);
        return objectType.create(new Point2D(x, y), direction);
    }
}
