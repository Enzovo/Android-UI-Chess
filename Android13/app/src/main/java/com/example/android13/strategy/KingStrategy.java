package com.example.android13.strategy;

import com.example.android13.piece.Piece;
import java.util.ArrayList;

/**
 * special strategy for King to move
 */
public class KingStrategy extends PieceStrategy implements Cloneable{
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


        if (cur.col == 0){
            // leftmost
            if (cur.row == 0){
                // leftmost and bottom
                //up
                if (pieces[cur.row + 1][cur.col] == null){
                    positions.add(new Position(cur.row + 1, cur.col));
                } else {
                    if (pieces[cur.row + 1][cur.col].getColor() != piece.getColor()) {
                        positions.add(new Position(cur.row + 1, cur.col));
                    }
                }
                //right up
                if (pieces[cur.row + 1][cur.col + 1] == null){
                    positions.add(new Position(cur.row + 1, cur.col + 1));
                } else {
                    if (pieces[cur.row + 1][cur.col + 1].getColor() != piece.getColor()) {
                        positions.add(new Position(cur.row + 1, cur.col + 1));
                    }
                }
                //right
                if (pieces[cur.row][cur.col + 1] == null){
                    positions.add(new Position(cur.row, cur.col + 1));
                } else {
                    if (pieces[cur.row][cur.col + 1].getColor() != piece.getColor()) {
                        positions.add(new Position(cur.row, cur.col + 1));
                    }
                }
            } else if (cur.row == 7) {
                // leftmost and top
                //right
                if (pieces[cur.row][cur.col + 1] == null){
                    positions.add(new Position(cur.row, cur.col + 1));
                } else {
                    if (pieces[cur.row][cur.col + 1].getColor() != piece.getColor()) {
                        positions.add(new Position(cur.row, cur.col + 1));
                    }
                }
                //right down
                if (pieces[cur.row - 1][cur.col + 1] == null){
                    positions.add(new Position(cur.row - 1, cur.col + 1));
                } else {
                    if (pieces[cur.row - 1][cur.col + 1].getColor() != piece.getColor()) {
                        positions.add(new Position(cur.row - 1, cur.col + 1));
                    }
                }
                //down
                if (pieces[cur.row - 1][cur.col] == null){
                    positions.add(new Position(cur.row - 1, cur.col));
                } else {
                    if (pieces[cur.row - 1][cur.col].getColor() != piece.getColor()) {
                        positions.add(new Position(cur.row - 1, cur.col));
                    }
                }
            } else {
                //else
                //right
                if (pieces[cur.row][cur.col + 1] == null){
                    positions.add(new Position(cur.row, cur.col + 1));
                } else {
                    if (pieces[cur.row][cur.col + 1].getColor() != piece.getColor()) {
                        positions.add(new Position(cur.row, cur.col + 1));
                    }
                }
                //up
                if (pieces[cur.row + 1][cur.col] == null){
                    positions.add(new Position(cur.row + 1, cur.col));
                } else {
                    if (pieces[cur.row + 1][cur.col].getColor() != piece.getColor()) {
                        positions.add(new Position(cur.row + 1, cur.col));
                    }
                }
                //down
                if (pieces[cur.row - 1][cur.col] == null){
                    positions.add(new Position(cur.row - 1, cur.col));
                } else {
                    if (pieces[cur.row - 1][cur.col].getColor() != piece.getColor()) {
                        positions.add(new Position(cur.row - 1, cur.col));
                    }
                }
                //right up
                if (pieces[cur.row + 1][cur.col + 1] == null){
                    positions.add(new Position(cur.row + 1, cur.col + 1));
                } else {
                    if (pieces[cur.row + 1][cur.col + 1].getColor() != piece.getColor()) {
                        positions.add(new Position(cur.row + 1, cur.col + 1));
                    }
                }
                //right down
                if (pieces[cur.row - 1][cur.col + 1] == null){
                    positions.add(new Position(cur.row - 1, cur.col + 1));
                } else {
                    if (pieces[cur.row - 1][cur.col + 1].getColor() != piece.getColor()) {
                        positions.add(new Position(cur.row - 1, cur.col + 1));
                    }
                }
            }
        } else if (cur.col == 7) {
            // rightmost
            if (cur.row == 0){
                // rightmost and bottom
                //up
                if (pieces[cur.row + 1][cur.col] == null){
                    positions.add(new Position(cur.row + 1, cur.col));
                } else {
                    if (pieces[cur.row + 1][cur.col].getColor() != piece.getColor()) {
                        positions.add(new Position(cur.row + 1, cur.col));
                    }
                }
                //left
                if (pieces[cur.row][cur.col - 1] == null){
                    positions.add(new Position(cur.row, cur.col - 1));
                } else {
                    if (pieces[cur.row][cur.col - 1].getColor() != piece.getColor()) {
                        positions.add(new Position(cur.row, cur.col - 1));
                    }
                }
                //left up
                if (pieces[cur.row + 1][cur.col - 1] == null){
                    positions.add(new Position(cur.row + 1, cur.col - 1));
                } else {
                    if (pieces[cur.row + 1][cur.col - 1].getColor() != piece.getColor()) {
                        positions.add(new Position(cur.row + 1, cur.col - 1));
                    }
                }
            } else if (cur.row == 7) {
                // rightmost and top
                //left
                if (pieces[cur.row][cur.col - 1] == null){
                    positions.add(new Position(cur.row, cur.col - 1));
                } else {
                    if (pieces[cur.row][cur.col - 1].getColor() != piece.getColor()) {
                        positions.add(new Position(cur.row, cur.col - 1));
                    }
                }
                //down
                if (pieces[cur.row - 1][cur.col] == null){
                    positions.add(new Position(cur.row - 1, cur.col));
                } else {
                    if (pieces[cur.row - 1][cur.col].getColor() != piece.getColor()) {
                        positions.add(new Position(cur.row - 1, cur.col));
                    }
                }
                //left down
                if (pieces[cur.row - 1][cur.col - 1] == null){
                    positions.add(new Position(cur.row - 1, cur.col - 1));
                } else {
                    if (pieces[cur.row - 1][cur.col - 1].getColor() != piece.getColor()) {
                        positions.add(new Position(cur.row - 1, cur.col - 1));
                    }
                }
            } else {
                //else
                //left
                if (pieces[cur.row][cur.col - 1] == null){
                    positions.add(new Position(cur.row, cur.col - 1));
                } else {
                    if (pieces[cur.row][cur.col - 1].getColor() != piece.getColor()) {
                        positions.add(new Position(cur.row, cur.col - 1));
                    }
                }
                //up
                if (pieces[cur.row + 1][cur.col] == null){
                    positions.add(new Position(cur.row + 1, cur.col));
                } else {
                    if (pieces[cur.row + 1][cur.col].getColor() != piece.getColor()) {
                        positions.add(new Position(cur.row + 1, cur.col));
                    }
                }
                //down
                if (pieces[cur.row - 1][cur.col] == null){
                    positions.add(new Position(cur.row - 1, cur.col));
                } else {
                    if (pieces[cur.row - 1][cur.col].getColor() != piece.getColor()) {
                        positions.add(new Position(cur.row - 1, cur.col));
                    }
                }
                //left up
                if (pieces[cur.row + 1][cur.col - 1] == null){
                    positions.add(new Position(cur.row + 1, cur.col - 1));
                } else {
                    if (pieces[cur.row + 1][cur.col - 1].getColor() != piece.getColor()) {
                        positions.add(new Position(cur.row + 1, cur.col - 1));
                    }
                }
                //left down
                if (pieces[cur.row - 1][cur.col - 1] == null){
                    positions.add(new Position(cur.row - 1, cur.col - 1));
                } else {
                    if (pieces[cur.row - 1][cur.col - 1].getColor() != piece.getColor()) {
                        positions.add(new Position(cur.row - 1, cur.col - 1));
                    }
                }
            }
        } else if (cur.row == 0){
            //bottom
            //left
            if (pieces[cur.row][cur.col - 1] == null){
                positions.add(new Position(cur.row, cur.col - 1));
            } else {
                if (pieces[cur.row][cur.col - 1].getColor() != piece.getColor()) {
                    positions.add(new Position(cur.row, cur.col - 1));
                }
            }
            //right
            if (pieces[cur.row][cur.col + 1] == null){
                positions.add(new Position(cur.row, cur.col + 1));
            } else {
                if (pieces[cur.row][cur.col + 1].getColor() != piece.getColor()) {
                    positions.add(new Position(cur.row, cur.col + 1));
                }
            }
            //up
            if (pieces[cur.row + 1][cur.col] == null){
                positions.add(new Position(cur.row + 1, cur.col));
            } else {
                if (pieces[cur.row + 1][cur.col].getColor() != piece.getColor()) {
                    positions.add(new Position(cur.row + 1, cur.col));
                }
            }
            //left up
            if (pieces[cur.row + 1][cur.col - 1] == null){
                positions.add(new Position(cur.row + 1, cur.col - 1));
            } else {
                if (pieces[cur.row + 1][cur.col - 1].getColor() != piece.getColor()) {
                    positions.add(new Position(cur.row + 1, cur.col - 1));
                }
            }
            //right up
            if (pieces[cur.row + 1][cur.col + 1] == null){
                positions.add(new Position(cur.row + 1, cur.col + 1));
            } else {
                if (pieces[cur.row + 1][cur.col + 1].getColor() != piece.getColor()) {
                    positions.add(new Position(cur.row + 1, cur.col + 1));
                }
            }
        } else if (cur.row == 7){
            //top
            //left
            if (pieces[cur.row][cur.col - 1] == null){
                positions.add(new Position(cur.row, cur.col - 1));
            } else {
                if (pieces[cur.row][cur.col - 1].getColor() != piece.getColor()) {
                    positions.add(new Position(cur.row, cur.col - 1));
                }
            }
            //right
            if (pieces[cur.row][cur.col + 1] == null){
                positions.add(new Position(cur.row, cur.col + 1));
            } else {
                if (pieces[cur.row][cur.col + 1].getColor() != piece.getColor()) {
                    positions.add(new Position(cur.row, cur.col + 1));
                }
            }
            //down
            if (pieces[cur.row - 1][cur.col] == null){
                positions.add(new Position(cur.row - 1, cur.col));
            } else {
                if (pieces[cur.row - 1][cur.col].getColor() != piece.getColor()) {
                    positions.add(new Position(cur.row - 1, cur.col));
                }
            }
            //left down
            if (pieces[cur.row - 1][cur.col - 1] == null){
                positions.add(new Position(cur.row - 1, cur.col - 1));
            } else {
                if (pieces[cur.row - 1][cur.col - 1].getColor() != piece.getColor()) {
                    positions.add(new Position(cur.row - 1, cur.col - 1));
                }
            }
            //right down
            if (pieces[cur.row - 1][cur.col + 1] == null){
                positions.add(new Position(cur.row - 1, cur.col + 1));
            } else {
                if (pieces[cur.row - 1][cur.col + 1].getColor() != piece.getColor()) {
                    positions.add(new Position(cur.row - 1, cur.col + 1));
                }
            }
        } else {
            //left
            if (pieces[cur.row][cur.col - 1] == null){
                positions.add(new Position(cur.row, cur.col - 1));
            } else {
                if (pieces[cur.row][cur.col - 1].getColor() != piece.getColor()) {
                    positions.add(new Position(cur.row, cur.col - 1));
                }
            }
            //right
            if (pieces[cur.row][cur.col + 1] == null){
                positions.add(new Position(cur.row, cur.col + 1));
            } else {
                if (pieces[cur.row][cur.col + 1].getColor() != piece.getColor()) {
                    positions.add(new Position(cur.row, cur.col + 1));
                }
            }
            //up
            if (pieces[cur.row + 1][cur.col] == null){
                positions.add(new Position(cur.row + 1, cur.col));
            } else {
                if (pieces[cur.row + 1][cur.col].getColor() != piece.getColor()) {
                    positions.add(new Position(cur.row + 1, cur.col));
                }
            }
            //down
            if (pieces[cur.row - 1][cur.col] == null){
                positions.add(new Position(cur.row - 1, cur.col));
            } else {
                if (pieces[cur.row - 1][cur.col].getColor() != piece.getColor()) {
                    positions.add(new Position(cur.row - 1, cur.col));
                }
            }
            //left up
            if (pieces[cur.row + 1][cur.col - 1] == null){
                positions.add(new Position(cur.row + 1, cur.col - 1));
            } else {
                if (pieces[cur.row + 1][cur.col - 1].getColor() != piece.getColor()) {
                    positions.add(new Position(cur.row + 1, cur.col - 1));
                }
            }
            //left down
            if (pieces[cur.row - 1][cur.col - 1] == null){
                positions.add(new Position(cur.row - 1, cur.col - 1));
            } else {
                if (pieces[cur.row - 1][cur.col - 1].getColor() != piece.getColor()) {
                    positions.add(new Position(cur.row - 1, cur.col - 1));
                }
            }
            //right up
            if (pieces[cur.row + 1][cur.col + 1] == null){
                positions.add(new Position(cur.row + 1, cur.col + 1));
            } else {
                if (pieces[cur.row + 1][cur.col + 1].getColor() != piece.getColor()) {
                    positions.add(new Position(cur.row + 1, cur.col + 1));
                }
            }
            //right down
            if (pieces[cur.row - 1][cur.col + 1] == null){
                positions.add(new Position(cur.row - 1, cur.col + 1));
            } else {
                if (pieces[cur.row - 1][cur.col + 1].getColor() != piece.getColor()) {
                    positions.add(new Position(cur.row - 1, cur.col + 1));
                }
            }
        }

//        //left
//        positions.add(new Position(cur.row, cur.col - 1));
//        //right
//        positions.add(new Position(cur.row, cur.col + 1));
//        //up
//        positions.add(new Position(cur.row + 1, cur.col));
//        //down
//        positions.add(new Position(cur.row - 1, cur.col));
//        //left up
//        positions.add(new Position(cur.row + 1, cur.col - 1));
//        //left down
//        positions.add(new Position(cur.row - 1, cur.col - 1));
//        //right up
//        positions.add(new Position(cur.row + 1, cur.col + 1));
//        //right down
//        positions.add(new Position(cur.row - 1, cur.col + 1));


        return positions;
    }


    @Override
    public KingStrategy clone() {
        return (KingStrategy) super.clone();
    }

}
