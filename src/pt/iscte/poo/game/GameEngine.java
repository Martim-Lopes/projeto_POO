package pt.iscte.poo.game;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;

import objects.Baba;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.utils.Direction;

public class GameEngine implements Observer {

    private static GameEngine INSTANCE = new GameEngine();
    private static final String ROOMS_DIRECTORY = "rooms";

    private Room currentRoom;
    private int lastTickProcessed = 0;
    private int level = 0;
    private int totalMoves = 0;
    private boolean gameWon = false;

    private GameEngine() {
        loadLevel(this.level);
    }

    public static GameEngine getInstance() {
        return INSTANCE;
    }

    public void loadLevel(int level) {
        try {
            File file = new File(ROOMS_DIRECTORY + "/room" + level + ".txt");
            currentRoom = Room.readRoom(file);
            totalMoves = 0;
            updateGUI();
            updateStatusMessage();
        } catch (FileNotFoundException e) {
            ImageGUI.getInstance().showMessage("Erro", "Nivel nao encontrado: room" + level + ".txt");
        }
    }

    @Override
    public void update(Observed source) {

        if (ImageGUI.getInstance().wasKeyPressed()) {
            int k = ImageGUI.getInstance().keyPressed();

            if (k == KeyEvent.VK_R) {
                restart("A reiniciar", "O jogo está a reiniciar");
                //está função pode ser util para dar informações ao joogador
                //ImageGUI.getInstance().showMessage("Teste", "Isto é um teste");
            }

            if (k == KeyEvent.VK_LEFT || k == KeyEvent.VK_RIGHT || k == KeyEvent.VK_UP || k == KeyEvent.VK_DOWN) {
                Direction d = Direction.directionFor(k);
                if (getPlayer().move(d, currentRoom)) {
                    totalMoves++;
                    if (currentRoom.hasContradiction()) {
                        //ver se as regras das palavras são contraditórias ou se estão repetidas
                        restart("Morreste", "Não é permitido ter duas condições em simultâneo");
                    } else if (!gameWon) {
                        updateStatusMessage();
                        handleVictory();
                    }
                }
            }
        }
        int t = ImageGUI.getInstance().getTicks();
        while (lastTickProcessed < t) {
            processTick();
            currentRoom.onTick();
        }
        // ver se é preciso atualizar
        if (currentRoom != null && currentRoom.needsGuiUpdate()) {
            updateGUI();
        }
        if (currentRoom != null && !getPlayer().isAlive()) {
            restart("Morreste", "Tem mais cuidado para a próxima!");
        }
        ImageGUI.getInstance().update();
    }

    private void processTick() {
        lastTickProcessed++;
    }

    public void updateGUI() {
        if (currentRoom != null) {
            ImageGUI.getInstance().clearImages();
            ImageGUI.getInstance().addImages(currentRoom.getObjects());
            currentRoom.clearGuiUpdate();
        }
    }

    private void updateStatusMessage() {
        ImageGUI.getInstance().setStatusMessage("Level " + level + " | Total moves: " + totalMoves + " | R: reiniciar");
    }

    public Baba getPlayer() {
        return currentRoom.getPlayer();
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void restart(String messageTitle, String message) {
        if (gameWon) {
            level = 0;
            gameWon = false;
        }
        loadLevel(this.level);
        ImageGUI.getInstance().showMessage(messageTitle, message);

    }

    private void handleVictory() {
        if (!currentRoom.isWin()) {
            return;
        }
        File nextRoom = new File("rooms/room" + (level + 1) + ".txt");
        if (nextRoom.exists()) {
            level++;
            loadLevel(level);
        } else {
            try {
                File winFile = new File(ROOMS_DIRECTORY + "/win.txt");
                if (winFile.exists()) {
                    currentRoom = Room.readRoom(winFile);
                    updateGUI();
                }
            } catch (FileNotFoundException e) {
                ImageGUI.getInstance().showMessage("Erro", "Mapa Win nao encontrado!");
            }
            gameWon = true;
            ImageGUI.getInstance().setStatusMessage("Parabens chegaste ao final do jogo | Prime R para reiniciar");
        }
    }
}
