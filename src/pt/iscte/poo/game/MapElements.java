package pt.iscte.poo.game;

import objects.*;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public enum MapElements {
    WALL('X') {
        @Override
        public GameObject create(Point2D position) {
            return new Wall(position);
        }
    },
    CRACKED_WALL('x') {
        @Override
        public GameObject create(Point2D position) {
            return new CrackedWall(position);
        }
    },
    HOLE('H') {
        @Override
        public GameObject create(Point2D position) {
            return new Hole(position);
        }
    },
    GRASS('g') {
        @Override
        public GameObject create(Point2D position) {
            return new Grass(position);
        }
    },
    ACID('A') {
        @Override
        public GameObject create(Point2D position) {
            return new Acid(position);
        }
    };

    private final char symbol;


    MapElements(char symbol) {
        this.symbol = symbol;
    }

    public abstract GameObject create(Point2D position);

    public static MapElements fromChar(char c) {
        for (MapElements s : values()) {
            if (s.symbol == c) {
                return s;
            }
        }
        return null;
    }
}
