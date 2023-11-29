package com.example.android13.strategy;

import com.example.android13.piece.Piece;
import java.util.ArrayList;

/**
 * special strategy for Bishop to move
 */
public class BishopStrategy extends PieceStrategy implements Cloneable{
    /**
     * get possible moves for the piece. I use an arraylist to store all the possible place on the board that can be moved by current piece and then match each element in arraylist with phased input position(src and dest). Match step happens in nextStep() and it will use availableMove() to check further
     * @param piece which would to move
     * @return possible moves in Arraylist type
     */
    @Override
    public ArrayList<Position> GetPossibleMoves(Piece piece) {
        ArrayList<Position> positions = new ArrayList<>();

        Position cur = piece.getPosition();
        Piece[][] pieces = piece.getBoard().GetPieces();

        // left up
        for (int r = cur.row+1, c = cur.col-1; r < BOARD_ROW && c >= 0; r++, c--) {
            if (pieces[r][c] == null) {
                positions.add(new Position(r, c));
            } else {
                if (pieces[r][c].getColor() != piece.getColor()) {
                    positions.add(new Position(r, c));
                }
                break;
            }
        }

        // left down
        for (int r = cur.row-1, c = cur.col-1; r >= 0 && c >= 0; r--, c--) {
            if (pieces[r][c] == null) {
                positions.add(new Position(r, c));
            } else {
                if (pieces[r][c].getColor() != piece.getColor()) {
                    positions.add(new Position(r, c));
                }
                break;
            }
        }

        // right up
        for (int r = cur.row+1, c = cur.col+1; r < BOARD_ROW && c < BOARD_COLUMN; r++, c++) {
            if (pieces[r][c] == null) {
                positions.add(new Position(r, c));
            } else {
                if (pieces[r][c].getColor() != piece.getColor()) {
                    positions.add(new Position(r, c));
                }
                break;
            }
        }

        // right down
        for (int r = cur.row-1, c = cur.col+1; r >= 0 && c < BOARD_COLUMN; r--, c++) {
            if (pieces[r][c] == null) {
                positions.add(new Position(r, c));
            } else {
                if (pieces[r][c].getColor() != piece.getColor()) {
                    positions.add(new Position(r, c));
                }
                break;
            }
        }
        return positions;
    }

    @Override
    public BishopStrategy clone() {
        return (BishopStrategy) super.clone();
    }

}
