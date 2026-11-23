package objects;

public enum WordType {
    BOX, IS, WIN, MOVE, STOP;

    public boolean isProperty() {
        return this == WIN || this == MOVE || this == STOP;
    }
}