package com.example.android13.piece;


import com.example.android13.Board;
import com.example.android13.Player;
import com.example.android13.strategy.PieceStrategy;
import com.example.android13.strategy.Position;

import java.util.Objects;

/**
 * Piece class is made for representing the detail about special piece
 */
public class Piece implements Cloneable{
    /**
     * reference to Position class, which represents the position on the board
     */
    Position position;
    /**
     * reference to Board class, which is created for access Board class
     */
    public Board board;
    /**
     * reference to Color enum, which represents piece's color
     */
    public Player.Color color;
    /**
     * reference to PieceStrategy class, which represents this piece's strategy
     */
    public PieceStrategy pieceStrategy;
    /**
     * a Position object, which used for presenting a previous move on the board. created for En passant implement
     */
    public Position prevPosition = new Position(-1, -1);
    /**
     * a variable to represent how many moves this piece had. created for En passant and Castling
     */
    private int stepNum = 0;

    /**
     * StepNum getter, which is created for getting stepNum in other class
     *
     * @return stepNum
     */
    public int getStepNum() {
        return stepNum;
    }

    /**
     * stepNum setter, which is created for setting stepNum in other class(after one move)
     *
     * @param stepNum new stepNum
     */
    public void setStepNum(int stepNum) {
        this.stepNum = stepNum;
    }

    /**
     * reference to Hero enum, which is created to set each piece's name
     */
    public Hero hero;


    /**
     * Tags whether the current hero has been upgraded
     */
    public boolean isUpgraded;

    /**
     * getName() is called at Display() in Board class, it will give a printed name to each piece base on their Hero type
     *
     * @return name for each piece as a String
     */
    public String getName() {
        String name = "w";
        if (color == Player.Color.BLACK) {
            name = "b";
        }
//        switch (hero) {
//            case BISHOP -> name = name+"B";
//            case KING -> name = name+"K";
//            case PAWN -> name = name+"P";
//            case ROOK -> name = name+"R";
//            case QUEEN -> name = name+"Q";
//            case KNIGHT -> name = name + "N";
//        }
        switch (hero) {
            case BISHOP:
                name = name + "B";
                break;
            case KING:
                name = name + "K";
                break;
            case PAWN:
                name = name + "P";
                break;
            case ROOK:
                name = name + "R";
                break;
            case QUEEN:
                name = name + "Q";
                break;
            case KNIGHT:
                name = name + "N";
                break;
        }
        return name;
    }

    /**
     * color getter
     *
     * @return color
     */
    public Player.Color getColor() {
        return color;
    }

    /**
     * position getter
     *
     * @return position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * position setter, which can update the new position of this piece on the board
     *
     * @param position new position
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Piece one-arg constructor, which is created for only initializing color.
     *
     * @param color new color
     */
    public Piece(Player.Color color) {
        this.color = color;
    }

    /**
     * Piece five-arg constructor, which is created for initializing each detail of this piece
     *
     * @param color         piece's color
     * @param pieceStrategy piece's strategy
     * @param board         the chess board that this piece on
     * @param hero          piece's character
     * @param position      piece's current position on the board
     */
    public Piece(Player.Color color, PieceStrategy pieceStrategy, Board board, Hero hero, Position position) {
        this.color = color;
        this.pieceStrategy = pieceStrategy;
        this.board = board;
        this.hero = hero;
        this.position = position;
    }

    public Piece(Piece other) {
        this.color = other.color;
        this.pieceStrategy = other.pieceStrategy.clone();
        this.board = other.board;
        this.hero = other.hero;
        if (other.position != null) {
            this.position = other.position.clone();
        }
        if (other.prevPosition != null) {
            this.prevPosition = other.prevPosition.clone();
        }
        this.stepNum = other.stepNum;
        this.isUpgraded = other.isUpgraded;
    }

    @Override
    public Piece clone() {
        return new Piece(this);
    }

    /**
     * board getter
     *
     * @return board
     */
    public Board getBoard() {
        return board;
    }

    @Override
    public String toString() {
        return "Piece{" +
                "position=" + position +
                ", board=" + board +
                ", color=" + color +
                ", pieceStrategy=" + pieceStrategy +
                ", prevPosition=" + prevPosition +
                ", stepNum=" + stepNum +
                ", hero=" + hero +
                '}';
    }


    @Override
    public int hashCode() {
        return Objects.hash(position, board, color, pieceStrategy, prevPosition, stepNum, hero, isUpgraded);
    }
}

