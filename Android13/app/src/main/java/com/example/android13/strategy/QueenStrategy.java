package com.example.android13.strategy;

import com.example.android13.piece.Piece;
import java.util.ArrayList;

/**
 * special strategy for Queen to move
 */
public class QueenStrategy extends PieceStrategy implements Cloneable{
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

        // left
        for (int i = cur.col-1; i >= 0; i--) {
            if (pieces[cur.row][i] == null) {
                positions.add(new Position(cur.row, i));
            } else {
                if (pieces[cur.row][i].getColor() != piece.getColor()) {
                    positions.add(new Position(cur.row, i));
                }
                break;
            }
        }

        // right
        for (int i = cur.col+1; i < BOARD_COLUMN; i++) {
            if (pieces[cur.row][i] == null) {
                positions.add(new Position(cur.row, i));
            } else {
                if (pieces[cur.row][i].getColor() != piece.getColor()) {
                    positions.add(new Position(cur.row, i));
                }
                break;
            }
        }

        // down
        for (int i = cur.row-1; i >= 0; i--) {
            if (pieces[i][cur.col] == null) {
                positions.add(new Position(i, cur.col));
            } else {
                if (pieces[i][cur.col].getColor() != piece.getColor()) {
                    positions.add(new Position(i, cur.col));
                }
                break;
            }
        }

        // up
        for (int i = cur.row+1; i < BOARD_ROW; i++) {
            if (pieces[i][cur.col] == null) {
                positions.add(new Position(i, cur.col));
            } else {
                if (pieces[i][cur.col].getColor() != piece.getColor()) {
                    positions.add(new Position(i, cur.col));
                }
                break;
            }
        }

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
    public QueenStrategy clone() {
        return (QueenStrategy) super.clone();
    }

}
