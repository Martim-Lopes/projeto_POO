package pt.iscte.poo.game;

import objects.Baba;
import objects.GameObject;
import objects.Rock;
import pt.iscte.poo.utils.Point2D;

public enum RoomObjects {
    ROCK("rock"){
        @Override
        public GameObject create(Point2D position) {
            return new Rock(position);
        }
    },
    BOMB("bomb"){
        @Override
        public GameObject create(Point2D position) {
            return new Bomb(position);
        }
    },
    BOX("box"){
        @Override
        public GameObject create(Point2D position) {
            return new Box(position);
        }
    },
    FLAG("flag"){
        @Override
        public GameObject create(Point2D position) {
            return new Flag(position);
        }
    },
    WORDBOX("wordBox"){
        @Override
        public GameObject create(Point2D position) {
            return new WordBox(position);
        }
    },
    WORDIS("wordIs"){
        @Override
        public GameObject create(Point2D position) {
            return new WorIs(position);
        }
    },
    WORDWIN("wordWin"){
        @Override
        public GameObject create(Point2D position) {
            return new WordWin(position);
        }
    },
    WORDMOVE("wordMove"){
        @Override
        public GameObject create(Point2D position) {
            return new WordMove(position);
        }
    },
    WORDSTOP("wordStop"){
        @Override
        public GameObject create(Point2D position) {
            return new WordStop(position);
        }
    };

    private final String s;

    RoomObjects(String s) {
        this.s = s;

    }

    public abstract GameObject create(Point2D position);

    public static RoomObjects fromString(String s) {
        for(RoomObjects obj : values()) {
            if(obj.s.equals(s)) {
                return obj;
            }
        }
        return null;
    }
}
