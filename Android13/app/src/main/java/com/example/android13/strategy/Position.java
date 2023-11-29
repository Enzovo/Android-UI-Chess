package com.example.android13.strategy;

/**
 * position of board, from 0 - 7 (1 - 8)
 */
public class Position implements Cloneable {
    public int row;
    public int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setNewPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }


    @Override
    public String toString() {
        return "Position{" +
                "row=" + row +
                ", col=" + col +
                '}';
    }

    @Override
    public Position clone() {
        return new Position(row, col);
    }
}
