package pt.iscte.poo.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import objects.*;
import pt.iscte.poo.utils.Point2D;

public class Room {

    private List<GameObject> objects;
    private String roomName;
    private Baba player;
    private int cols;
    private int rows;

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
        r.cols = Integer.parseInt(s[0]);
        r.rows = Integer.parseInt(s[1]);

        for (int i = 0; i < r.cols; i++) {
            for (int j = 0; j < r.rows; j++) {
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
        while (sc.hasNextLine()) {
            String[] line = sc.nextLine().split(",");
            String type = line[0].trim();
            Point2D pos = new Point2D(Integer.parseInt(line[1].trim()), Integer.parseInt(line[2].trim()));
            // Caso a parte para o baba. Existe a opção de usarmos o instanceof, mas acho que e demasiado avançado
            if (type.equals("baba")) {
                Baba baba = new Baba(pos, line[3].trim());
                r.player = baba;
                r.addObject(baba);
            } else {
                RoomObjects objs = RoomObjects.fromString(type);
                if (objs != null) {
                    r.addObject(objs.create(pos));
                }
            }
        }
        sc.close();
        return r;
    }

    public Baba getPlayer() {
        return player;
    }

    public boolean canMove(Point2D position) {
        if (position.getX() < 0 || position.getX() >= cols || position.getY() >= rows || position.getY() < 0) {
            return false;
        }
        for (GameObject obj : objects) {
            if (obj.getPosition().equals(position) && !obj.isPassable()) {
                return false;
            }
        }
        return true;
    }

    public boolean canPush(Point2D position) {
        for (GameObject obj : objects) {
            if (obj.getPosition().equals(position) && obj.isPushable()) {
                return true;
            }
        }
        return false;
    }

    public GameObject getPushableObject(Point2D position) {
        for (GameObject obj : objects) {
            if (obj.getPosition().equals(position) && obj.isPushable()) {
                return obj;
            }
        }
        return null;
    }

    public GameObject getLethalObjectAt(Point2D position) {
        for (GameObject obj : objects) {
            if (obj.getPosition().equals(position) && obj.isLethal()) {
                return obj;
            }
        }
        return null;
    }

    public boolean isLethal(Point2D position) {
        for (GameObject obj : objects) {
            if (obj.getPosition().equals(position) && obj.isLethal()) {
                return true;
            }
        }
        return false;
    }

    public void killPlayer() {
        if (player != null) {
            player.kill();
            objects.remove(player);
        }
    }

    public boolean isWin() {
        if (player == null || !player.isAlive()) {
            return false;
        }
        Point2D playerPos = player.getPosition();
        for (GameObject obj : objects) {
            if (obj.getPosition().equals(playerPos) && obj.isWin()) {
                return true;
            }
        }
        return false;
    }

    public void tryPutBox(GameObject pushedObj) {
        if (!pushedObj.getName().equals("box")) {
            return;
        }
        Point2D pos = pushedObj.getPosition();
        GameObject hole = getLethalObjectAt(pos);
        if (hole != null) {
            objects.remove(hole);
            objects.remove(pushedObj);
            objects.add(new BoxInHole(pos));
            GameEngine.getInstance().updateGUI();
        }
    }


}