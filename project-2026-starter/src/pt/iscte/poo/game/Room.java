package pt.iscte.poo.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import objects.Baba;
import objects.Dirt;
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

        int cols = sc.nextInt();
        int rows = sc.nextInt();

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
                }
            }
            j++;
        }


        for (int i = 0; i <= 10; i++) {
            ImageTile dirt = new Dirt(new Point2D(i, i));
            r.addObject(dirt);
            ImageTile dirt2 = new Dirt(new Point2D(10 - i, i));
            r.addObject(dirt2);
        }


        Baba baba = new Baba(new Point2D(5, 5));
        r.addObject(baba);

        r.player = baba;
        return r;
    }

    public Baba getPlayer() {
        return player;
    }
}