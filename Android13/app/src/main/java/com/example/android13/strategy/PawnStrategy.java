package com.example.android13.strategy;

import com.example.android13.Action;
import com.example.android13.Board;
import com.example.android13.Player;
import com.example.android13.piece.Hero;
import com.example.android13.piece.Piece;

import java.util.ArrayList;

import static com.example.android13.strategy.MoveResult.*;
import static com.example.android13.strategy.MoveResult.CHECK;

import android.util.Log;

/**
 * special strategy for Pawn to move
 */
public class PawnStrategy extends PieceStrategy implements Cloneable{

    /**
     * get possible moves for the piece. I use an arraylist to store all the possible place on the board that can be moved by current piece and then match each element in arraylist with phased input position(src and dest). Match step happens in nextStep() and it will use availableMove() to check further
     * @param piece which would to move
     * @return possible moves in Arraylist type
     */
    public ArrayList<Position> GetPossibleMoves(Piece piece){
        ArrayList<Position> positions = new ArrayList<>();
        Position cur = piece.getPosition();
        Piece[][] pieces = piece.getBoard().GetPieces();

        if (piece.getColor() == Player.Color.WHITE){
            //White Pawn move up
            if (cur.row < 7 && pieces[cur.row + 1][cur.col] == null){
                //up
                positions.add(new Position(cur.row + 1,cur.col));
            }

            if (cur.row < 7 && cur.col < 7){
                if (pieces[cur.row + 1][cur.col + 1] != null) {
                    //right up kill
                    if (pieces[cur.row + 1][cur.col + 1].getColor() != piece.getColor()) {
                        positions.add(new Position(cur.row + 1, cur.col + 1));
                    }
                }
            }

            if (cur.col > 0 && cur.row < 7 && pieces[cur.row + 1][cur.col - 1] != null){
                //left up kill
                if (pieces[cur.row + 1][cur.col - 1].getColor() != piece.getColor()){
                    positions.add(new Position(cur.row + 1,cur.col - 1));
                }
            }

            if (cur.row == 1 && pieces[cur.row + 2][cur.col] == null && pieces[cur.row + 1][cur.col] == null){
                //first two-steps move
                positions.add(new Position(cur.row + 2, cur.col));
            }
        } else {
            //Black Pawn move down
            if (cur.row > 0 && pieces[cur.row - 1][cur.col] == null){
                //down
                positions.add(new Position(cur.row - 1,cur.col));
            }
            if (cur.row > 0 && cur.col < 7 && pieces[cur.row - 1][cur.col + 1] != null){
                //right down kill
                if (pieces[cur.row - 1][cur.col + 1].getColor() != piece.getColor()){
                    positions.add(new Position(cur.row - 1,cur.col + 1));
                }
            }
            if (cur.row > 0 && cur.col > 0 && pieces[cur.row - 1][cur.col - 1] != null){
                //left down kill
                if (pieces[cur.row - 1][cur.col - 1].getColor() != piece.getColor()){
                    positions.add(new Position(cur.row - 1,cur.col - 1));
                }
            }
            if (cur.row == 6 && pieces[cur.row - 2][cur.col] == null && pieces[cur.row - 1][cur.col] == null){
                //first two-steps move
                positions.add(new Position(cur.row - 2, cur.col));
            }
        }
        return positions;
    }


    /**
     * move one step. Special NextStep() for Pawn promotion, when promotion exist pawn will do the new Hero NextStep()
     * @param piece which would move
     * @param action my move
     * @return the result of this step
     */
    public MoveResult NextStep(Piece piece, Action action){
        Position position = action.dstPos;
        boolean avail = false;

        ArrayList<Position> positions = GetAvailMoves(piece);
        for (Position pos : positions) {
            if (position.col == pos.col && position.row == pos.row) {
                avail = true;
                break;
            }
        }



        if (!avail) {
            if (!enPassant(piece,position)) {
                return ILLEGAL;
            }

        }

        Board board = piece.getBoard();
        Piece[][] pieces = board.GetPieces();



        Piece enemyKing = board.getWhiteKing();
        if (piece.getColor() == Player.Color.WHITE) {
            enemyKing = board.getBlackKing();
        }


        // kill enemy piece or just move to an empty position
        piece.prevPosition = piece.getPosition();
        pieces[position.row][position.col] = piece;
        pieces[piece.getPosition().row][piece.getPosition().col] = null;
        piece.setPosition(position);

        if (action.upgrade != null){
            piece.hero = action.upgrade.hero;
            piece.pieceStrategy = action.upgrade.pieceStrategy;
        }


        boolean check = checkEnemyKing(piece);
        if (!check) {
            return LEGAL;
        }


        // whether checkmate
        // no place can move
        if (enemyKing.pieceStrategy.GetAvailMoves(enemyKing).isEmpty()) {
            // the check piece will not be killed and the check path will not be blocked by any other enemy pieces
            boolean checkPieceBeKilled = checkKilled(piece);
            boolean checkPieceBeBlocked = checkBlocked(piece, enemyKing.getPosition());
            if (!checkPieceBeKilled && !checkPieceBeBlocked) {
                return CHECKMATE;
            }
        }
        return CHECK;
    }

    /**
     * created for pawn's En Passant, for checking the stepNum and the position on the board to add a new position that this pawn can move. To implement En Passant
     * @param piece moving piece
     * @param position destination position for doing En passant pawn
     * @return whether this pawn can do an En Passant move
     */
    public boolean enPassant(Piece piece, Position position){
        Position cur = piece.getPosition();
        Piece[][] pieces = piece.getBoard().GetPieces();

        if (!(position.row >= 0 && position.row < BOARD_ROW && position.col >= 0 && position.col < BOARD_COLUMN)) {
            return false;
        }

        if (pieces[position.row][position.col] != null){
            return false;
        }
        if (piece.getColor() == Player.Color.WHITE){
            //En passant white
            if (cur.row == 4 && position.row == 5 && Math.abs(position.col - cur.col) == 1){
                Piece pawn = pieces[cur.row][position.col];
                if (pawn != null && pawn.getColor() != piece.getColor()){
                    if (pawn.hero == Hero.PAWN && pawn.getStepNum() == 1){
                        if (piece.getBoard().preBlackPiece == pawn) {   //if enemy last move is pawn double-steps
                            pieces[cur.row][position.col] = null;
                            return true;
                        }
                    }
                }
            }
        } else {
            if (cur.row == 3 && position.row == 2 && Math.abs(position.col - cur.col) == 1){
                Piece pawn = pieces[cur.row][position.col];
                if (pawn != null && pawn.getColor() != piece.getColor()){
                    if (pawn.hero == Hero.PAWN && pawn.getStepNum() == 1){
                        if (piece.getBoard().preWhitePiece == pawn) {   //if enemy last move is pawn double-steps
                            pieces[cur.row][position.col] = null;
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public PawnStrategy clone() {
        return (PawnStrategy) super.clone();
    }


}
