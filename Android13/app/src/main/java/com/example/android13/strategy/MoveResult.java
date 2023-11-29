package com.example.android13.strategy;


/**
 * an enum for the result after moving, which can distinguish different move and print special prompt. It could be created in other class simply
 */
public enum MoveResult {
    /**
     * represents this move is a legal move
     */
    LEGAL,
    /**
     * represents this move is an illegal move
     */
    ILLEGAL,
    /**
     * represents this move will cause a check
     */
    CHECK,
    /**
     * represents this move will cause a checkmate
     */
    CHECKMATE,
    /**
     * represents this move will cause a draw
     */
    DRAW,
    /**
     * represents this move will cause a resignation
     */
    RESIGN
}
