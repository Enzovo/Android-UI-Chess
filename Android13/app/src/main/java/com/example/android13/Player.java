package com.example.android13;


import android.util.Log;
import android.widget.Toast;

import com.example.android13.piece.Hero;
import com.example.android13.piece.Piece;
import com.example.android13.strategy.*;

import java.util.Objects;

/**
 * Player class is used for phasing input commands. Every command line will be phased in the Player class and be changed into a special enum type
 */
public class Player {
    /**
     * create an enum Color, which represents different player
     */
    public enum Color {WHITE, BLACK}

    /**
     * reference to enum Color, color is a private and unchangeable variable to represents the player's color
     */
    private final Color color;
    /**
     * reference to class Board, board is a private Board type variable, which can be used to access board's method
     */
    private Board board;
    /**
     * create a pieces object, which is a 2D array and represents the place in the chess board
     */
    public Piece[][] pieces;

    /**
     * Player constructor, use it to initialize the color, board and pieces variable
     *
     * @param color  Color enum's instance, which represents the moving player's color
     * @param board  Board class instance, which represents the chess board
     * @param pieces Piece[][] instance, which represents the position of the piece on the chess board
     */
    public Player(Color color, Board board, Piece[][] pieces) {
        this.color = color;
        this.board = board;
        this.pieces = pieces;
    }

    /**
     * Build the action from the source location to the destination location
     *
     * @param action action instruction
     * @return an action object, which represents the action current player wants to do
     */
    public Action nextAction(String action) {
        Log.d("Board","action:"+action);
        Player enemyPlayer = board.getPreIsWhite();
        if (enemyPlayer == this) {
            enemyPlayer = board.getBlack();
        }
        Action result = new Action();

        if (Objects.equals(action, "draw?")) {
            //unnecessary
            result.type = Action.Type.DRAW_REQ;
        } else if (Objects.equals(action, "draw")) {
            result.type = Action.Type.DRAW_RES;
        } else if (Objects.equals(action, "resign")) {
            result.type = Action.Type.RESIGN;
        } else {
            String[] moveActions = action.split(" ");
            if (moveActions.length == 2) {
                if (moveActions[0].charAt(0) < 'a' || moveActions[0].charAt(0) > 'h'
                        || moveActions[0].charAt(1) < '1' || moveActions[0].charAt(1) > '8') {
                    result.type = Action.Type.ILLEGAL;
                } else {
                    result.srcRow = moveActions[0].charAt(1) - '1';
                    result.srcColumn = moveActions[0].charAt(0) - 'a';
                    result.targetRow = moveActions[1].charAt(1) - '1';
                    result.targetColumn = moveActions[1].charAt(0) - 'a';
                    result.type = Action.Type.MOVE;
                    if (pieces[result.srcRow][result.srcColumn] == null || pieces[result.srcRow][result.srcColumn].hero == null) {
                        result.type = Action.Type.ILLEGAL;
                        return result;
                    }
                    // default upgrade
                    if (board.GetPieces()[result.srcRow][result.srcColumn].hero == Hero.PAWN) {
                        if ((result.targetRow == 7 && color == Color.WHITE) || (result.targetRow == 0 && color == Color.BLACK)) {
                            result.upgrade = new Piece(color);
                        }
                    }
                }
            } else if (moveActions.length == 3) {
                if (moveActions[0].charAt(0) < 'a' || moveActions[0].charAt(0) > 'h'
                        || moveActions[0].charAt(1) < '1' || moveActions[0].charAt(1) > '8') {
                    result.type = Action.Type.ILLEGAL;
                } else {
                    result.srcRow = moveActions[0].charAt(1) - '1';
                    result.srcColumn = moveActions[0].charAt(0) - 'a';
                    result.targetRow = moveActions[1].charAt(1) - '1';
                    result.targetColumn = moveActions[1].charAt(0) - 'a';
                    result.type = Action.Type.MOVE;
                }
                if (Objects.equals(moveActions[2], "draw?")) {
                    result.type = Action.Type.DRAW_REQ;
                } else if ((result.targetRow == 7 && color == Color.WHITE) || (result.targetRow == 0 && color == Color.BLACK)) {

                    result.upgrade = new Piece(color);
                    switch (moveActions[2].charAt(0)) {
                        case 'R':
                            result.upgrade.hero = Hero.ROOK;
                            result.upgrade.pieceStrategy = new RookStrategy();
                            break;
                        case 'B':
                            result.upgrade.hero = Hero.BISHOP;
                            result.upgrade.pieceStrategy = new BishopStrategy();
                            break;
                        case 'N':
                            result.upgrade.hero = Hero.KNIGHT;
                            result.upgrade.pieceStrategy = new KnightStrategy();
                            break;
                        case 'Q':
                            result.upgrade.hero = Hero.QUEEN;
                            result.upgrade.pieceStrategy = new QueenStrategy();
                            break;
                    }
                }
            }
        }
        result.srcPos = new Position(result.srcRow, result.srcColumn);
        result.dstPos = new Position(result.targetRow, result.targetColumn);

        return result;
    }
}
