package com.example.android13.strategy;

import com.example.android13.piece.Piece;

import java.util.ArrayList;
/**
 * special strategy for Knight to move
 */
public class KnightStrategy extends PieceStrategy implements Cloneable{
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
//        Position newPos;
//        boolean knightLegal;
//        if (8 > cur.row + 1 &&)

        //left 2 up 1
        if (cur.row + 1 < BOARD_ROW && cur.col - 2 >= 0) {
            if (pieces[cur.row + 1][cur.col - 2] == null) {
                positions.add(new Position(cur.row + 1, cur.col - 2));
            } else {
                if (pieces[cur.row + 1][cur.col - 2].getColor() != piece.getColor()) {
                    positions.add(new Position(cur.row + 1, cur.col - 2));
                }
            }
        }
        //left 2 down 1
        if (cur.row - 1 >= 0 && cur.col - 2 >= 0) {
            if (pieces[cur.row - 1][cur.col - 2] == null) {
                positions.add(new Position(cur.row - 1, cur.col - 2));
            } else {
                if (pieces[cur.row - 1][cur.col - 2].getColor() != piece.getColor()) {
                    positions.add(new Position(cur.row - 1, cur.col - 2));
                }
            }
        }
        //left 1 up 2
        if (cur.row + 2 < BOARD_ROW && cur.col - 1 >= 0) {
            if (pieces[cur.row + 2][cur.col - 1] == null) {
                positions.add(new Position(cur.row + 2, cur.col - 1));
            } else {
                if (pieces[cur.row + 2][cur.col - 1].getColor() != piece.getColor()) {
                    positions.add(new Position(cur.row + 2, cur.col - 1));
                }
            }
        }
        //left 1 down 2
        if (cur.row - 2 >= 0 && cur.col - 1 >= 0) {
            if (pieces[cur.row - 2][cur.col - 1] == null) {
                positions.add(new Position(cur.row - 2, cur.col - 1));
            } else {
                if (pieces[cur.row - 2][cur.col - 1].getColor() != piece.getColor()) {
                    positions.add(new Position(cur.row - 2, cur.col - 1));
                }
            }
        }
        //right 2 up 1
        if (cur.row + 1 < BOARD_ROW && cur.col + 2 < BOARD_COLUMN) {
            if (pieces[cur.row + 1][cur.col + 2] == null) {
                positions.add(new Position(cur.row + 1, cur.col + 2));
            } else {
                if (pieces[cur.row + 1][cur.col + 2].getColor() != piece.getColor()) {
                    positions.add(new Position(cur.row + 1, cur.col + 2));
                }
            }
        }
        //right 2 down 1
        if (cur.row - 1 >= 0 && cur.col + 2 < BOARD_COLUMN) {
            if (pieces[cur.row - 1][cur.col + 2] == null) {
                positions.add(new Position(cur.row - 1, cur.col + 2));
            } else {
                if (pieces[cur.row - 1][cur.col + 2].getColor() != piece.getColor()) {
                    positions.add(new Position(cur.row - 1, cur.col + 2));
                }
            }
        }
        // right 1 up 2
        if (cur.row + 2 < BOARD_ROW && cur.col + 1 < BOARD_COLUMN) {
            if (pieces[cur.row + 2][cur.col + 1] == null) {
                positions.add(new Position(cur.row + 2, cur.col + 1));
            } else {
                if (pieces[cur.row + 2][cur.col + 1].getColor() != piece.getColor()) {
                    positions.add(new Position(cur.row + 2, cur.col + 1));
                }
            }
        }
        //right 1 down 2
        if (cur.row - 2 >= 0 && cur.col + 1 < BOARD_COLUMN) {
            if (pieces[cur.row - 2][cur.col + 1] == null) {
                positions.add(new Position(cur.row - 2, cur.col + 1));
            } else {
                if (pieces[cur.row - 2][cur.col + 1].getColor() != piece.getColor()) {
                    positions.add(new Position(cur.row - 2, cur.col + 1));
                }
            }
        }
        return positions;
    }

    @Override
    public KnightStrategy clone() {
        return (KnightStrategy) super.clone();
    }

}
