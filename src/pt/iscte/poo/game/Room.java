package pt.iscte.poo.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import objects.*;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class Room {

    private List<GameObject> objects;
    private String roomName;
    private Baba player;
    private static int cols;
    private static int rows;

    public Room() {
        objects = new ArrayList<>();
    }

    private void setName(String name) {
        roomName = name;
    }

    public String getName() {
        return roomName;
    }

    public void addObject(GameObject obj) {
        objects.add(obj);
    }

    public List<GameObject> getObjects() {
        return objects;
    }

    public static Room readRoom(File f) throws FileNotFoundException {
        Room r = new Room();
        r.setName(f.getName());
        Scanner sc = new Scanner(f);

        String[] s = sc.nextLine().split(",");
        cols = Integer.parseInt(s[0]);
        rows = Integer.parseInt(s[1]);

        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                GameObject dirt = new Dirt(new Point2D(i, j));
                r.addObject(dirt);
            }
        }
        int j = 0;
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.isBlank()) {
                break;
            }
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                MapElements elements = MapElements.fromChar(c);
                if (elements != null) {
                    r.addObject(elements.create(new Point2D(i, j)));
                }
            }
            j++;
        }
        while(sc.hasNextLine()) {
            String[] line = sc.nextLine().split(",");
            if(line[0].equals("baba")) {
                Baba baba = new Baba(new Point2D(Integer.parseInt(line[1]), Integer.parseInt(line[2])), line[3]);
                r.addObject(baba);
                r.player = baba;
            }
        }
        return r;
    }

    public Baba getPlayer() {
        return player;
    }

    public boolean canMove(Point2D position) {
        if(position.getX() < 0 || position.getX() >= cols || position.getY() >= rows || position.getY() < 0) {return false;}
        for(GameObject obj : objects) {
            if(obj.getPosition().equals(position) && !obj.isPassable()) {return false;}
        }
        return true;
    }
}