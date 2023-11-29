package com.example.android13;


import com.example.android13.piece.Piece;
import com.example.android13.strategy.Position;

/**
 * Action class represents the action a player did, which includes two positions(source and destination) and the type this position causes. We use it to phase the Input commands.
 */
public class Action {
    /**
     * a public int variable, which represents the destination row. Get it from the phased input in Player class.
     */
    public int targetRow;
    /**
     * a public int variable, which represents the destination column. Get it from the phased input in Player class.
     */
    public int targetColumn;
    /**
     * a public int variable, which represents the source row. Get it from the phased input in Player class.
     */
    public int srcRow;
    /**
     * a public int variable, which represents the source column. Get it from the phased input in Player class.
     */
    public int srcColumn;

    /**
     * reference to Position class. srcPos is a Position type variable, which represents the source position and gets from srcRow and srcColumn
     */
    public Position srcPos;
    /**
     * reference to Position class. srcPos is a Position type variable, which represents the destination position and gets from targetRow and targetColumn
     */
    public Position dstPos;

    /**
     * Create an enum with 5 types in it and call it Type
     */
    public enum Type{MOVE, DRAW_REQ, RESIGN, DRAW_RES, ILLEGAL};
    /**
     * reference to Piece class, upgrade is a Piece type variable to represent promotion. We use it to record and implement pawn promotion logic
     */
    public Piece upgrade;
    /**
     * reference to enum Type. type is an enum variable, which represent the current action player did. Use it to distinguish the different type player did
     */
    public Type type;

    @Override
    public String toString() {
        return "Action{" +
                "targetRow=" + targetRow +
                ", targetColumn=" + targetColumn +
                ", srcRow=" + srcRow +
                ", srcColumn=" + srcColumn +
                ", srcPos=" + srcPos +
                ", dstPos=" + dstPos +
                ", upgrade=" + upgrade +
                ", type=" + type +
                '}';
    }
}

