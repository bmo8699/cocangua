package model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

// The circle that a Piece will travel through
// Can be accessed via index at map.spaceMap
// Get coordinates by space.getLayoutX() and space.getLayoutY()
public class Space extends Circle implements Position {
    boolean isOccupied;
    Piece piece;

    public Space(Color color) {
        setRadius(20);
        setFill(color);
        this.piece = null;
    }

    @Override
    public boolean getOccupancy() {
        return isOccupied;
    }

    @Override
    public void setOccupancy(boolean isOccupied) {
        this.isOccupied = isOccupied;
    }

    @Override
    public Piece getPiece() {
        return piece;
    }

    @Override
    public void setPiece(Piece piece) {
        this.piece = piece;
    }
}