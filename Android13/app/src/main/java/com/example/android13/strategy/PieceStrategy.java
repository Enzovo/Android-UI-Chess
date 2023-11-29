package com.example.android13.strategy;


import com.example.android13.Action;
import com.example.android13.Board;
import com.example.android13.Player;
import com.example.android13.piece.Hero;
import com.example.android13.piece.Piece;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static com.example.android13.strategy.MoveResult.*;
import static com.example.android13.strategy.MoveResult.CHECK;

import android.util.Log;
import android.widget.Toast;

/**
 * PieceStrategy class is an abstract class, which includes the strategy for each piece
 */
public abstract class PieceStrategy implements Cloneable {
    /**
     * global variable for row
     */
    int BOARD_ROW = 8;
    /**
     * global variable for column
     */
    int BOARD_COLUMN = 8;

    /**
     * move one step
     *
     * @param piece  which would move
     * @param action my move
     * @return the result of this step
     */
    public MoveResult NextStep(Piece piece, Action action) {
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
            return ILLEGAL;
        }

        Board board = piece.getBoard();
        Piece[][] pieces = board.GetPieces();


        Piece enemyKing = board.getWhiteKing();
        if (piece.getColor() == Player.Color.WHITE) {
            enemyKing = board.getBlackKing();
        }

        //Castling move
        if (piece.hero == Hero.KING && piece.getStepNum() == 0 && Math.abs(position.col - piece.getPosition().col) == 2) {
            pieces[position.row][position.col] = piece;
            if (piece.getPosition().col > position.col) {
                //left
                pieces[piece.getPosition().row][piece.getPosition().col - 1] = pieces[piece.getPosition().row][0];
                pieces[piece.getPosition().row][piece.getPosition().col - 1].setPosition(new Position(piece.getPosition().row, piece.getPosition().col - 1));
                pieces[piece.getPosition().row][0] = null;
            } else {
                //right
                pieces[piece.getPosition().row][piece.getPosition().col + 1] = pieces[piece.getPosition().row][7];
                pieces[piece.getPosition().row][piece.getPosition().col + 1].setPosition(new Position(piece.getPosition().row, piece.getPosition().col + 1));
                pieces[piece.getPosition().row][7] = null;
            }
            pieces[piece.getPosition().row][piece.getPosition().col] = null;
            piece.setPosition(position);
        } else {
            // kill enemy piece or just move to an empty position
            piece.prevPosition = piece.getPosition();
            pieces[position.row][position.col] = piece;
            pieces[piece.getPosition().row][piece.getPosition().col] = null;
            piece.setPosition(position);
        }


        if (action.upgrade != null) {
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
     * used for checking the path from checking piece to the enemyKing will be blocked by any enemy piece or not
     *
     * @param piece     checking piece
     * @param enemyKing enemyKing position
     * @return true - path will be blocked.  false - path won't be blocked
     */
    boolean checkBlocked(Piece piece, Position enemyKing) {
        if (piece.getPosition().col == enemyKing.col) {
            if (piece.getPosition().row > enemyKing.row) {
                for (int i = piece.getPosition().row - 1; i > enemyKing.row; i--) {
                    Piece tmpPiece = new Piece(piece.getColor(), null, piece.getBoard(), null, new Position(i, piece.getPosition().col));
                    if (checkKilled(tmpPiece)) {
                        return true;
                    }
                }
            } else {
                for (int i = piece.getPosition().row + 1; i < enemyKing.row; i++) {
                    Piece tmpPiece = new Piece(piece.getColor(), null, piece.getBoard(), null, new Position(i, piece.getPosition().col));
                    if (checkKilled(tmpPiece)) {
                        return true;
                    }
                }
            }
        } else if (piece.getPosition().row == enemyKing.row) {
            if (piece.getPosition().col > enemyKing.col) {
                for (int i = piece.getPosition().col - 1; i > enemyKing.col; i--) {
                    Piece tmpPiece = new Piece(piece.getColor(), null, piece.getBoard(), null, new Position(piece.getPosition().row, i));
                    if (checkKilled(tmpPiece)) {
                        return true;
                    }
                }
            } else {
                for (int i = piece.getPosition().col + 1; i < enemyKing.col; i++) {
                    Piece tmpPiece = new Piece(piece.getColor(), null, piece.getBoard(), null, new Position(piece.getPosition().row, i));
                    if (checkKilled(tmpPiece)) {
                        return true;
                    }
                }
            }
        } else if (piece.getPosition().col > enemyKing.col) {
            if (piece.getPosition().row > enemyKing.row) {
                for (int r = enemyKing.row + 1, c = enemyKing.col + 1; r < piece.getPosition().row && c < piece.getPosition().col; r++, c++) {
                    Piece tmpPiece = new Piece(piece.getColor(), null, piece.getBoard(), null, new Position(r, c));
                    if (checkKilled(tmpPiece)) {
                        return true;
                    }
                }
            } else {
                for (int r = enemyKing.row - 1, c = enemyKing.col + 1; r > piece.getPosition().row && c < piece.getPosition().col; r--, c++) {
                    Piece tmpPiece = new Piece(piece.getColor(), null, piece.getBoard(), null, new Position(r, c));
                    if (checkKilled(tmpPiece)) {
                        return true;
                    }
                }
            }
            //      p   >  k
        } else {
            if (piece.getPosition().row > enemyKing.row) {
                for (int r = enemyKing.row + 1, c = enemyKing.col - 1; r < piece.getPosition().row && c > piece.getPosition().col; r++, c--) {
                    Piece tmpPiece = new Piece(piece.getColor(), null, piece.getBoard(), null, new Position(r, c));
                    if (checkKilled(tmpPiece)) {
                        return true;
                    }
                }
            } else {
                for (int r = enemyKing.row - 1, c = enemyKing.col - 1; r > piece.getPosition().row && c > piece.getPosition().col; r--, c--) {
                    Piece tmpPiece = new Piece(piece.getColor(), null, piece.getBoard(), null, new Position(r, c));
                    if (checkKilled(tmpPiece)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * used for checking the checking piece will be killed by any enemy pieces or not
     *
     * @param piece checking piece
     * @return true - checking piece will be killed.  false - checking piece will not be killed
     */
    boolean checkKilled(Piece piece) {
        Player.Color enemyColor = Player.Color.WHITE;
        if (piece.getColor() == Player.Color.WHITE) {
            enemyColor = Player.Color.BLACK;
        }
        Board board = piece.getBoard();
        Piece[][] pieces = board.GetPieces();
        for (int i = 0; i < BOARD_ROW; i++) {
            for (int j = 0; j < BOARD_COLUMN; j++) {
                if (pieces[i][j] != null) {
                    if (pieces[i][j].getColor() != enemyColor) {
                        continue;
                    }
                } else {
                    continue;
                }
                ArrayList<Position> positions = pieces[i][j].pieceStrategy.GetAvailMoves(pieces[i][j]);
                for (Position pos : positions) {
                    if (pos.col == piece.getPosition().col && pos.row == piece.getPosition().row) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    /**
     * whether piece can check to the position, I use for-each to check whether the enemy King is in my available moves from new position
     *
     * @param piece    which can check
     * @param position the position of enemy KING
     * @return true - this piece will cause check. false - nothing happens
     */
    public boolean Check(Piece piece, Position position) {
        ArrayList<Position> positions = piece.pieceStrategy.GetAvailMoves(piece);
        boolean check = false;
        for (Position pos : positions) {
            if (pos.col == position.col && pos.row == position.row) {
                check = true;
                break;
            }
        }
        return check;
    }

    /**
     * * whether piece can check to the position, I use for-each to check whether the enemy King is in my available moves from new position
     *
     * @param piece moving piece
     * @return true - this piece will cause check. false - nothing happens
     */
    public boolean checkEnemyKing(Piece piece) {
        Piece enemyKing = piece.board.getWhiteKing();
        if (piece.getColor() == Player.Color.WHITE) {
            enemyKing = piece.board.getBlackKing();
        }

        Piece[][] pieces = piece.getBoard().GetPieces();

        for (int r = 0; r < BOARD_ROW; r++) {
            for (int c = 0; c < BOARD_COLUMN; c++) {
                if (pieces[r][c] != null && pieces[r][c].getColor() != enemyKing.getColor()) {
                    if (Check(pieces[r][c], enemyKing.getPosition())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * whether enemy piece can check my KING
     *
     * @param piece    enemy piece
     * @param position the position of my KING
     * @return whether my king will be checked
     */
    public boolean possibleCheck(Piece piece, Position position) {
        ArrayList<Position> positions = piece.pieceStrategy.GetPossibleMoves(piece);
        boolean check = false;
        for (Position pos : positions) {
            if (pos.col == position.col && pos.row == position.row) {
                check = true;
                break;
            }
        }
        return check;
    }


    /**
     * whether my move will cause any enemy piece to check my King, if so it should be an illegal move(court death)
     *
     * @param piece    my moving piece
     * @param position dst position
     * @return My KING whether be checked
     */
    boolean BeChecked(Piece piece, Position position) {
        Position cur = piece.getPosition();
        Piece[][] pieces = piece.getBoard().GetPieces();

        Piece saveEnemyPiece = pieces[position.row][position.col];

        pieces[position.row][position.col] = piece;
        pieces[cur.row][cur.col] = null;


        Position myKing = piece.getBoard().getBlackKing().getPosition();
        if (piece.getColor() == Player.Color.WHITE) {
            myKing = piece.getBoard().getWhiteKing().getPosition();
        }
        //if piece == MYKING, reassign KING position. if not MYKING will be src position(not updated)
        if (piece.hero == Hero.KING) {
            myKing = position;
        }


        for (int r = 0; r < BOARD_ROW; r++) {
            for (int c = 0; c < BOARD_COLUMN; c++) {
                if (pieces[r][c] != null && pieces[r][c].getColor() != piece.getColor()) {
                    if (possibleCheck(pieces[r][c], myKing)) {
                        pieces[cur.row][cur.col] = piece;
                        pieces[position.row][position.col] = saveEnemyPiece;
                        return true;
                    }
                }
            }
        }
        pieces[cur.row][cur.col] = piece;
        pieces[position.row][position.col] = saveEnemyPiece;

        return false;
    }


    /**
     * get available moves for the piece. After got possibleMove(), check these moves would be a legal move then store it as the final available moves
     *
     * @param piece which would to move
     * @return available moves in Arraylist
     */

    public ArrayList<Position> GetAvailMoves(Piece piece) {

        ArrayList<Position> positions = GetPossibleMoves(piece);

        ArrayList<Position> availPositions = new ArrayList<>();
        for (Position pos : positions) {
            if (!BeChecked(piece, pos)) {
                availPositions.add(pos);
            }
        }

        //Castling
        if (piece.hero == Hero.KING) {
            Piece[][] pieces = piece.getBoard().GetPieces();
            if (piece.getColor() == Player.Color.WHITE) {
                if (pieces[0][0] != null && pieces[0][0].hero == Hero.ROOK && pieces[0][0].getStepNum() == 0 && piece.getStepNum() == 0) {
                    //left white Rook
                    if (pieces[0][1] == null && pieces[0][2] == null && pieces[0][3] == null) {
                        if (!castlingPathBeCheck(piece, new Position(0, 1)) &&
                                !castlingPathBeCheck(piece, new Position(0, 2)) &&
                                !castlingPathBeCheck(piece, new Position(0, 3))) {
                            availPositions.add(new Position(0, 2));
                        }
                    }
                }

                if (pieces[0][7] != null && pieces[0][7].hero == Hero.ROOK && pieces[0][7].getStepNum() == 0 && piece.getStepNum() == 0) {
                    //right white Rook
                    if (pieces[0][6] == null && pieces[0][5] == null) {
                        if (!castlingPathBeCheck(piece, new Position(0, 6)) &&
                                !castlingPathBeCheck(piece, new Position(0, 5))) {
                            availPositions.add(new Position(0, 6));
                        }
                    }
                }
            } else {
                if (pieces[7][0] != null && pieces[7][0].hero == Hero.ROOK && pieces[7][0].getStepNum() == 0 && piece.getStepNum() == 0) {
                    //left black Rook
                    if (pieces[7][1] == null && pieces[7][2] == null && pieces[7][3] == null) {
                        if (!castlingPathBeCheck(piece, new Position(7, 1)) &&
                                !castlingPathBeCheck(piece, new Position(7, 2)) &&
                                !castlingPathBeCheck(piece, new Position(7, 3))) {
                            availPositions.add(new Position(7, 2));
                        }
                    }
                }

                if (pieces[7][7] != null && pieces[7][7].hero == Hero.ROOK && pieces[7][7].getStepNum() == 0 && piece.getStepNum() == 0) {
                    //right black Rook
                    if (pieces[7][6] == null && pieces[7][5] == null) {
                        if (!castlingPathBeCheck(piece, new Position(7, 6)) &&
                                !castlingPathBeCheck(piece, new Position(7, 5))) {
                            availPositions.add(new Position(7, 6));
                        }
                    }
                }
            }
        }


        return availPositions;
    }

    /**
     * Castling condition, check the path whether be checked when castling
     *
     * @param king     my king
     * @param position some position on the path
     * @return whether this position would be checked by any enemy piece
     */
    public boolean castlingPathBeCheck(Piece king, Position position) {
        Piece[][] pieces = king.getBoard().GetPieces();

        for (int r = 0; r < BOARD_ROW; r++) {
            for (int c = 0; c < BOARD_COLUMN; c++) {
                if (pieces[r][c] != null && pieces[r][c].getColor() != king.getColor()) {
                    if (possibleCheck(pieces[r][c], position)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    /**
     * get possible moves for the piece. I use an arraylist to store all the possible place on the board that can be moved by current piece and then match each element in arraylist with phased input position(src and dest). Match step happens in nextStep() and it will use availableMove() to check further
     *
     * @param piece which would to move
     * @return possible moves in Arraylist type
     */
    public abstract ArrayList<Position> GetPossibleMoves(Piece piece);


    @Override
    public PieceStrategy clone() {
        try {
            return (PieceStrategy) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // Can't happen
        }
    }

}
