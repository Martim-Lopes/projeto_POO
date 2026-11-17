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

    private List<ImageTile> objects;
    private String roomName;
    private Baba player;

    public Room() {
        objects = new ArrayList<>();
    }

    private void setName(String name) {
        roomName = name;
    }

    public String getName() {
        return roomName;
    }

    public void addObject(ImageTile obj) {
        objects.add(obj);
    }

    public List<ImageTile> getObjects() {
        return objects;
    }

    public static Room readRoom(File f) throws FileNotFoundException {
        Room r = new Room();
        r.setName(f.getName());
        Scanner sc = new Scanner(f);

        int cols = Integer.parseInt(sc.next(","));
        int rows =  Integer.parseInt(sc.next(" "));

        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                ImageTile dirt = new Dirt(new Point2D(i, j));
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
                if (c == 'X') {
                    ImageTile wall = new Wall(new Point2D(i, j));
                    r.addObject(wall);
                }
                if (c == 'x') {
                    ImageTile crackedWall =  new CrackedWall(new Point2D(i,j));
                    r.addObject(crackedWall);
                }
                if (c == 'H') {
                    ImageTile hole = new Hole(new Point2D(i,j));
                    r.addObject(hole);
                }
                if(c == 'g') {
                    ImageTile grass = new Grass(new Point2D(i, j));
                    r.addObject(grass);
                }
            }
            j++;
        }
        while(sc.hasNextLine()) {
            String[] line = sc.nextLine().split(",");
            if(line[0].equals("baba")) {
                Baba baba = new Baba(new Point2D(Integer.parseInt(line[1]), Integer.parseInt(line[2])), line[3]);
                r.player = baba;
            }
        }

        //r.player = baba;
        return r;
    }

    public Baba getPlayer() {
        return player;
    }
}