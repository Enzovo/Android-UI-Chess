package com.example.android13;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android13.piece.Hero;
import com.example.android13.strategy.*;
import com.example.android13.piece.Piece;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Board extends AppCompatActivity {


    public TextView[][] DisplayBoard = new TextView[8][8];
    public TextView[][] DisplayBoardBackground = new TextView[8][8];

    public Button backButton, nextMoveButton, undoButton, aiButton, resignButton, drawButton;

    public GameRecord gameRecord;
    private Action action = null;
    private MoveResult result = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board);

        Intent intent = getIntent();
        long id = intent.getLongExtra("id", -1);
        this.white = new Player(Player.Color.WHITE, this, pieces);
        this.black = new Player(Player.Color.BLACK, this, pieces);
        iniBoard(id);
        if (id != -1) {
            gameRecord = LitePal.find(GameRecord.class, id);
        } else {
            gameRecord = new GameRecord();
        }
    }

    /**
     * Gets the resource ID of the image by name
     *
     * @param name
     * @return the resource ID
     */
    public int getResId(String name) {
        switch (name) {
            case "wB":
                return R.drawable.wbishop;
            case "wK":
                return R.drawable.wking;
            case "wP":
                return R.drawable.wpawn;
            case "wR":
                return R.drawable.wrook;
            case "wQ":
                return R.drawable.wqueen;
            case "wN":
                return R.drawable.wknight;
            case "bB":
                return R.drawable.bbishop;
            case "bK":
                return R.drawable.bking;
            case "bP":
                return R.drawable.bpawn;
            case "bR":
                return R.drawable.brook;
            case "bQ":
                return R.drawable.bqueen;
            case "bN":
                return R.drawable.bknight;
        }
        return -1;
    }

    /**
     * Redraw each piece on the board
     */
    public void Display() {
        for (int i = ROW - 1; i >= 0; i--) {
            for (int j = 0; j < COLUMN; j++) {
                DisplayBoard[i][j].setBackgroundResource(android.R.color.transparent);
            }
        }
        for (int i = ROW - 1; i >= 0; i--) {
            for (int j = 0; j < COLUMN; j++) {

                if (pieces[i][j] != null) {
                    Piece piece = pieces[i][j];
                    DisplayBoard[piece.getPosition().row][piece.getPosition().col].setBackgroundResource(getResId(piece.getName()));
                }
            }
        }
    }


    private boolean selected = false;
    private boolean undoFinish = false;
    private Position selectPosition = new Position(-1, -1);

    private boolean preIsWhite = false; // Mark whether the previous round is white chess or not.
    private Action enemyAction = null;
    /**
     * reference to Player class and create a private variable white with Player type, which indicates the white player
     */
    private Player white;
    /**
     * reference to Player class and create a private variable black with Player type, which indicates the black player
     */
    private Player black;

    /**
     * whith getter
     *
     * @return Player type variable white
     */
    public Player getPreIsWhite() {
        return white;
    }

    /**
     * black getter
     *
     * @return Player type variable black
     */
    public Player getBlack() {
        return black;
    }

    /**
     * reference to Piece class and create a public variable with Piece type, which indicates the previously moved white piece. To record the previous moved piece, uses it for En passant implement
     */
    public Piece preWhitePiece;
    /**
     * reference to Piece class and create a public variable with Piece type, which indicates the previously moved black piece. To record the previous moved piece, uses it for En passant implement
     */
    public Piece preBlackPiece;

    /**
     * reference to Piece class and create a private variable with Piece type, which indicates the white King piece use it to initial the white King's position on chess board
     */
    private Piece whiteKing;
    /**
     * reference to Piece class and create a private variable with Piece type, which indicates the black King piece use it to initial the black King's position on chess board
     */
    private Piece blackKing;
    /**
     * make a global variable to indicate the Row of the chess board
     */
    public static final int ROW = 8;
    /**
     * make a global variable to indicate the Column of the chess board
     */
    public static final int COLUMN = 8;

    /**
     * pieces is a 2D array that represent the whole chess board
     */
    private Piece[][] pieces = new Piece[ROW][COLUMN];

    private Piece[][] copypieces = new Piece[ROW][COLUMN];


    /**
     * iniBoard() is created for initialing the chess board. Put all the pieces at the correct position in the 2D array pieces. It will be called at the constructor of the Board class, which will be called at the Chess class to run the game
     *
     * @param id
     */
    private void iniBoard(long id) {
        backButton = findViewById(R.id.back_button);
        nextMoveButton = findViewById(R.id.nextMove_button);
        undoButton = findViewById(R.id.undo_button);
        aiButton = findViewById(R.id.ai_button);
        resignButton = findViewById(R.id.resign_button);
        drawButton = findViewById(R.id.draw_button);
        // init white pieces
        pieces[0][0] = new Piece(Player.Color.WHITE, new RookStrategy(), this, Hero.ROOK, new Position(0, 0));
        pieces[0][1] = new Piece(Player.Color.WHITE, new KnightStrategy(), this, Hero.KNIGHT, new Position(0, 1));
        pieces[0][2] = new Piece(Player.Color.WHITE, new BishopStrategy(), this, Hero.BISHOP, new Position(0, 2));
        pieces[0][3] = new Piece(Player.Color.WHITE, new QueenStrategy(), this, Hero.QUEEN, new Position(0, 3));
        pieces[0][4] = new Piece(Player.Color.WHITE, new KingStrategy(), this, Hero.KING, new Position(0, 4));
        pieces[0][5] = new Piece(Player.Color.WHITE, new BishopStrategy(), this, Hero.BISHOP, new Position(0, 5));
        pieces[0][6] = new Piece(Player.Color.WHITE, new KnightStrategy(), this, Hero.KNIGHT, new Position(0, 6));
        pieces[0][7] = new Piece(Player.Color.WHITE, new RookStrategy(), this, Hero.ROOK, new Position(0, 7));

        for (int c = 0; c < COLUMN; c++) {
            pieces[1][c] = new Piece(Player.Color.WHITE, new PawnStrategy(), this, Hero.PAWN, new Position(1, c));
        }

        whiteKing = pieces[0][4];

        // init black pieces
        pieces[7][0] = new Piece(Player.Color.BLACK, new RookStrategy(), this, Hero.ROOK, new Position(7, 0));
        pieces[7][1] = new Piece(Player.Color.BLACK, new KnightStrategy(), this, Hero.KNIGHT, new Position(7, 1));
        pieces[7][2] = new Piece(Player.Color.BLACK, new BishopStrategy(), this, Hero.BISHOP, new Position(7, 2));
        pieces[7][3] = new Piece(Player.Color.BLACK, new QueenStrategy(), this, Hero.QUEEN, new Position(7, 3));
        pieces[7][4] = new Piece(Player.Color.BLACK, new KingStrategy(), this, Hero.KING, new Position(7, 4));
        pieces[7][5] = new Piece(Player.Color.BLACK, new BishopStrategy(), this, Hero.BISHOP, new Position(7, 5));
        pieces[7][6] = new Piece(Player.Color.BLACK, new KnightStrategy(), this, Hero.KNIGHT, new Position(7, 6));
        pieces[7][7] = new Piece(Player.Color.BLACK, new RookStrategy(), this, Hero.ROOK, new Position(7, 7));

        for (int c = 0; c < COLUMN; c++) {
            pieces[6][c] = new Piece(Player.Color.BLACK, new PawnStrategy(), this, Hero.PAWN, new Position(6, c));
        }

//        pieces[6][5] = new Piece(Player.Color.WHITE, new PawnStrategy(), this, Hero.PAWN, new Position(6, 5));

        blackKing = pieces[7][4];

        DisplayBoard[0][0] = (TextView) findViewById(R.id.R00);
        DisplayBoardBackground[0][0] = (TextView) findViewById(R.id.R000);
        DisplayBoard[1][0] = (TextView) findViewById(R.id.R10);
        DisplayBoardBackground[1][0] = (TextView) findViewById(R.id.R010);
        DisplayBoard[2][0] = (TextView) findViewById(R.id.R20);
        DisplayBoardBackground[2][0] = (TextView) findViewById(R.id.R020);
        DisplayBoard[3][0] = (TextView) findViewById(R.id.R30);
        DisplayBoardBackground[3][0] = (TextView) findViewById(R.id.R030);
        DisplayBoard[4][0] = (TextView) findViewById(R.id.R40);
        DisplayBoardBackground[4][0] = (TextView) findViewById(R.id.R040);
        DisplayBoard[5][0] = (TextView) findViewById(R.id.R50);
        DisplayBoardBackground[5][0] = (TextView) findViewById(R.id.R050);
        DisplayBoard[6][0] = (TextView) findViewById(R.id.R60);
        DisplayBoardBackground[6][0] = (TextView) findViewById(R.id.R060);
        DisplayBoard[7][0] = (TextView) findViewById(R.id.R70);
        DisplayBoardBackground[7][0] = (TextView) findViewById(R.id.R070);

        DisplayBoard[0][1] = (TextView) findViewById(R.id.R01);
        DisplayBoardBackground[0][1] = (TextView) findViewById(R.id.R001);
        DisplayBoard[1][1] = (TextView) findViewById(R.id.R11);
        DisplayBoardBackground[1][1] = (TextView) findViewById(R.id.R011);
        DisplayBoard[2][1] = (TextView) findViewById(R.id.R21);
        DisplayBoardBackground[2][1] = (TextView) findViewById(R.id.R021);
        DisplayBoard[3][1] = (TextView) findViewById(R.id.R31);
        DisplayBoardBackground[3][1] = (TextView) findViewById(R.id.R031);
        DisplayBoard[4][1] = (TextView) findViewById(R.id.R41);
        DisplayBoardBackground[4][1] = (TextView) findViewById(R.id.R041);
        DisplayBoard[5][1] = (TextView) findViewById(R.id.R51);
        DisplayBoardBackground[5][1] = (TextView) findViewById(R.id.R051);
        DisplayBoard[6][1] = (TextView) findViewById(R.id.R61);
        DisplayBoardBackground[6][1] = (TextView) findViewById(R.id.R061);
        DisplayBoard[7][1] = (TextView) findViewById(R.id.R71);
        DisplayBoardBackground[7][1] = (TextView) findViewById(R.id.R071);

        DisplayBoard[0][2] = (TextView) findViewById(R.id.R02);
        DisplayBoardBackground[0][2] = (TextView) findViewById(R.id.R002);
        DisplayBoard[1][2] = (TextView) findViewById(R.id.R12);
        DisplayBoardBackground[1][2] = (TextView) findViewById(R.id.R012);
        DisplayBoard[2][2] = (TextView) findViewById(R.id.R22);
        DisplayBoardBackground[2][2] = (TextView) findViewById(R.id.R022);
        DisplayBoard[3][2] = (TextView) findViewById(R.id.R32);
        DisplayBoardBackground[3][2] = (TextView) findViewById(R.id.R032);
        DisplayBoard[4][2] = (TextView) findViewById(R.id.R42);
        DisplayBoardBackground[4][2] = (TextView) findViewById(R.id.R042);
        DisplayBoard[5][2] = (TextView) findViewById(R.id.R52);
        DisplayBoardBackground[5][2] = (TextView) findViewById(R.id.R052);
        DisplayBoard[6][2] = (TextView) findViewById(R.id.R62);
        DisplayBoardBackground[6][2] = (TextView) findViewById(R.id.R062);
        DisplayBoard[7][2] = (TextView) findViewById(R.id.R72);
        DisplayBoardBackground[7][2] = (TextView) findViewById(R.id.R072);

        DisplayBoard[0][3] = (TextView) findViewById(R.id.R03);
        DisplayBoardBackground[0][3] = (TextView) findViewById(R.id.R003);
        DisplayBoard[1][3] = (TextView) findViewById(R.id.R13);
        DisplayBoardBackground[1][3] = (TextView) findViewById(R.id.R013);
        DisplayBoard[2][3] = (TextView) findViewById(R.id.R23);
        DisplayBoardBackground[2][3] = (TextView) findViewById(R.id.R023);
        DisplayBoard[3][3] = (TextView) findViewById(R.id.R33);
        DisplayBoardBackground[3][3] = (TextView) findViewById(R.id.R033);
        DisplayBoard[4][3] = (TextView) findViewById(R.id.R43);
        DisplayBoardBackground[4][3] = (TextView) findViewById(R.id.R043);
        DisplayBoard[5][3] = (TextView) findViewById(R.id.R53);
        DisplayBoardBackground[5][3] = (TextView) findViewById(R.id.R053);
        DisplayBoard[6][3] = (TextView) findViewById(R.id.R63);
        DisplayBoardBackground[6][3] = (TextView) findViewById(R.id.R063);
        DisplayBoard[7][3] = (TextView) findViewById(R.id.R73);
        DisplayBoardBackground[7][3] = (TextView) findViewById(R.id.R073);

        DisplayBoard[0][4] = (TextView) findViewById(R.id.R04);
        DisplayBoardBackground[0][4] = (TextView) findViewById(R.id.R004);
        DisplayBoard[1][4] = (TextView) findViewById(R.id.R14);
        DisplayBoardBackground[1][4] = (TextView) findViewById(R.id.R014);
        DisplayBoard[2][4] = (TextView) findViewById(R.id.R24);
        DisplayBoardBackground[2][4] = (TextView) findViewById(R.id.R024);
        DisplayBoard[3][4] = (TextView) findViewById(R.id.R34);
        DisplayBoardBackground[3][4] = (TextView) findViewById(R.id.R034);
        DisplayBoard[4][4] = (TextView) findViewById(R.id.R44);
        DisplayBoardBackground[4][4] = (TextView) findViewById(R.id.R044);
        DisplayBoard[5][4] = (TextView) findViewById(R.id.R54);
        DisplayBoardBackground[5][4] = (TextView) findViewById(R.id.R054);
        DisplayBoard[6][4] = (TextView) findViewById(R.id.R64);
        DisplayBoardBackground[6][4] = (TextView) findViewById(R.id.R064);
        DisplayBoard[7][4] = (TextView) findViewById(R.id.R74);
        DisplayBoardBackground[7][4] = (TextView) findViewById(R.id.R074);

        DisplayBoard[0][5] = (TextView) findViewById(R.id.R05);
        DisplayBoardBackground[0][5] = (TextView) findViewById(R.id.R005);
        DisplayBoard[1][5] = (TextView) findViewById(R.id.R15);
        DisplayBoardBackground[1][5] = (TextView) findViewById(R.id.R015);
        DisplayBoard[2][5] = (TextView) findViewById(R.id.R25);
        DisplayBoardBackground[2][5] = (TextView) findViewById(R.id.R025);
        DisplayBoard[3][5] = (TextView) findViewById(R.id.R35);
        DisplayBoardBackground[3][5] = (TextView) findViewById(R.id.R035);
        DisplayBoard[4][5] = (TextView) findViewById(R.id.R45);
        DisplayBoardBackground[4][5] = (TextView) findViewById(R.id.R045);
        DisplayBoard[5][5] = (TextView) findViewById(R.id.R55);
        DisplayBoardBackground[5][5] = (TextView) findViewById(R.id.R055);
        DisplayBoard[6][5] = (TextView) findViewById(R.id.R65);
        DisplayBoardBackground[6][5] = (TextView) findViewById(R.id.R065);
        DisplayBoard[7][5] = (TextView) findViewById(R.id.R75);
        DisplayBoardBackground[7][5] = (TextView) findViewById(R.id.R075);

        DisplayBoard[0][6] = (TextView) findViewById(R.id.R06);
        DisplayBoardBackground[0][6] = (TextView) findViewById(R.id.R006);
        DisplayBoard[1][6] = (TextView) findViewById(R.id.R16);
        DisplayBoardBackground[1][6] = (TextView) findViewById(R.id.R016);
        DisplayBoard[2][6] = (TextView) findViewById(R.id.R26);
        DisplayBoardBackground[2][6] = (TextView) findViewById(R.id.R026);
        DisplayBoard[3][6] = (TextView) findViewById(R.id.R36);
        DisplayBoardBackground[3][6] = (TextView) findViewById(R.id.R036);
        DisplayBoard[4][6] = (TextView) findViewById(R.id.R46);
        DisplayBoardBackground[4][6] = (TextView) findViewById(R.id.R046);
        DisplayBoard[5][6] = (TextView) findViewById(R.id.R56);
        DisplayBoardBackground[5][6] = (TextView) findViewById(R.id.R056);
        DisplayBoard[6][6] = (TextView) findViewById(R.id.R66);
        DisplayBoardBackground[6][6] = (TextView) findViewById(R.id.R066);
        DisplayBoard[7][6] = (TextView) findViewById(R.id.R76);
        DisplayBoardBackground[7][6] = (TextView) findViewById(R.id.R076);

        DisplayBoard[0][7] = (TextView) findViewById(R.id.R07);
        DisplayBoardBackground[0][7] = (TextView) findViewById(R.id.R007);
        DisplayBoard[1][7] = (TextView) findViewById(R.id.R17);
        DisplayBoardBackground[1][7] = (TextView) findViewById(R.id.R017);
        DisplayBoard[2][7] = (TextView) findViewById(R.id.R27);
        DisplayBoardBackground[2][7] = (TextView) findViewById(R.id.R027);
        DisplayBoard[3][7] = (TextView) findViewById(R.id.R37);
        DisplayBoardBackground[3][7] = (TextView) findViewById(R.id.R037);
        DisplayBoard[4][7] = (TextView) findViewById(R.id.R47);
        DisplayBoardBackground[4][7] = (TextView) findViewById(R.id.R047);
        DisplayBoard[5][7] = (TextView) findViewById(R.id.R57);
        DisplayBoardBackground[5][7] = (TextView) findViewById(R.id.R057);
        DisplayBoard[6][7] = (TextView) findViewById(R.id.R67);
        DisplayBoardBackground[6][7] = (TextView) findViewById(R.id.R067);
        DisplayBoard[7][7] = (TextView) findViewById(R.id.R77);
        DisplayBoardBackground[7][7] = (TextView) findViewById(R.id.R077);
        for (int i = 0; i < 8; i++) {
            DisplayBoard[1][i].setBackgroundResource(R.drawable.wpawn);
        }
        for (int i = 0; i < 8; i++) {
            DisplayBoard[1][i].setBackgroundResource(R.drawable.wpawn);
            DisplayBoard[6][i].setBackgroundResource(R.drawable.bpawn);
        }
        for (int i = 2; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                DisplayBoard[i][j].setBackgroundResource(android.R.color.transparent);
            }
        }

        DisplayBoard[0][0].setBackgroundResource(R.drawable.wrook);
        DisplayBoard[0][1].setBackgroundResource(R.drawable.wknight);
        DisplayBoard[0][2].setBackgroundResource(R.drawable.wbishop);
        DisplayBoard[0][3].setBackgroundResource(R.drawable.wqueen);
        DisplayBoard[0][4].setBackgroundResource(R.drawable.wking);
        DisplayBoard[0][5].setBackgroundResource(R.drawable.wbishop);
        DisplayBoard[0][6].setBackgroundResource(R.drawable.wknight);
        DisplayBoard[0][7].setBackgroundResource(R.drawable.wrook);

        DisplayBoard[7][0].setBackgroundResource(R.drawable.brook);
        DisplayBoard[7][1].setBackgroundResource(R.drawable.bknight);
        DisplayBoard[7][2].setBackgroundResource(R.drawable.bbishop);
        DisplayBoard[7][3].setBackgroundResource(R.drawable.bqueen);
        DisplayBoard[7][4].setBackgroundResource(R.drawable.bking);
        DisplayBoard[7][5].setBackgroundResource(R.drawable.bbishop);
        DisplayBoard[7][6].setBackgroundResource(R.drawable.bknight);
        DisplayBoard[7][7].setBackgroundResource(R.drawable.brook);
        if (id == -1) {
            nextMoveButton.setClickable(false);
            nextMoveButton.setBackgroundColor(Color.GRAY);
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    int finalI = i;
                    int finalJ = j;
                    DisplayBoard[i][j].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Drawable.ConstantState state = DisplayBoard[finalI][finalJ].getBackground().getConstantState();
                            if (selected) {
                                String move = createMove(selectPosition, new Position(finalI, finalJ));
                                copyPieceSave();
                                if (preIsWhite) {    // white
                                    action = black.nextAction(move);
                                } else {  // black
                                    action = white.nextAction(move);
                                }
                                if (action.upgrade != null) {
                                    upgrade(move, null);
                                } else {
                                    if (preIsWhite) {
                                        result = execute(action, Player.Color.BLACK, enemyAction, false);
                                    } else {
                                        result = execute(action, Player.Color.WHITE, enemyAction, false);
                                    }
                                    if (result != MoveResult.ILLEGAL) {
                                        if (result == MoveResult.CHECKMATE) { // 将死
                                            if (preIsWhite == false) {
                                                showDialog("White wins");
                                            } else {
                                                showDialog("Black wins");
                                            }
                                        }
                                        updatePre();
                                        Display();
                                        gameRecord.addRole(preIsWhite);
                                        gameRecord.addNewCommand(move);
                                        Log.d("Board", "add" + gameRecord.toString());
                                        enemyAction = action;
                                        preIsWhite = !preIsWhite;
                                        undoFinish = false;
                                    } else {
                                        copyPieceNULL();
                                    }
                                    changeState(false);
                                }
                            } else {
                                if (isBlackPiece(state)) {
                                    if (preIsWhite == false) {
                                        changeState(false);
                                        Toast.makeText(getApplicationContext(), "Now is the turn of a white chess player!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        changeState(true, finalI, finalJ);
                                    }
                                } else if (isWhitePiece(state)) {
                                    if (preIsWhite == true) {
                                        changeState(false);
                                        Toast.makeText(getApplicationContext(), "Now is the turn of a black chess player!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        changeState(true, finalI, finalJ);
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "Please select a chess piece!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                }
            }
        } else {
            undoButton.setClickable(false);
            undoButton.setBackgroundColor(Color.GRAY);
            aiButton.setClickable(false);
            aiButton.setBackgroundColor(Color.GRAY);
            resignButton.setClickable(false);
            resignButton.setBackgroundColor(Color.GRAY);
            drawButton.setClickable(false);
            drawButton.setBackgroundColor(Color.GRAY);
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    DisplayBoard[i][j].setClickable(false);
                }
            }
        }
    }

    private void updatePre() {
        if (preIsWhite) {
            preBlackPiece = pieces[action.dstPos.row][action.dstPos.col];
        } else {
            preWhitePiece = pieces[action.dstPos.row][action.dstPos.col].clone();
        }
    }


    public void copyPieceSave() {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COLUMN; j++) {
                if (pieces[i][j] != null) {
                    copypieces[i][j] = pieces[i][j].clone();
                } else {
                    copypieces[i][j] = null;
                }
            }
        }
    }

    public void copyPieceNULL() {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COLUMN; j++) {
                copypieces[i][j] = null;
            }
        }
    }

    public boolean copyPieceISNULL() {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COLUMN; j++) {
                if (copypieces[i][j] != null) {
                    return false;
                }
            }
        }
        return true;
    }

    private void upgrade(String move, String type) {
        final String[] items = {"ROOK", "BISHOP", "KNIGHT", "QUEEN"};
        final Hero[] heroes = {Hero.ROOK, Hero.BISHOP, Hero.KNIGHT, Hero.QUEEN};
        final PieceStrategy[] strategies = {new RookStrategy(), new BishopStrategy(), new KnightStrategy(), new QueenStrategy()};
        if (type == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Select a piece to upgrade to:");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    action.upgrade.hero = heroes[which];
                    action.upgrade.pieceStrategy = strategies[which];
                    Toast.makeText(getApplicationContext(), "You have selected " + items[which], Toast.LENGTH_SHORT).show();
                    if (preIsWhite) {
                        result = execute(action, Player.Color.BLACK, enemyAction, false);
                    } else {
                        result = execute(action, Player.Color.WHITE, enemyAction, false);
                    }
                    if (result != MoveResult.ILLEGAL) {
                        if (result == MoveResult.CHECKMATE) { // 将死
                            if (preIsWhite == false) {
                                showDialog("White wins");
                            } else {
                                showDialog("Black wins");
                            }
                        }
                        pieces[action.dstPos.row][action.dstPos.col].pieceStrategy = strategies[which];
                        pieces[action.dstPos.row][action.dstPos.col].hero = heroes[which];
                        pieces[action.dstPos.row][action.dstPos.col].isUpgraded = true;

                        updatePre();
                        gameRecord.addRole(preIsWhite);
                        gameRecord.addNewCommand(String.format("%s %s", move, items[which]));
                        enemyAction = action;
                        Display();
                        preIsWhite = !preIsWhite;
                        undoFinish = false;
                    }
                    changeState(false);
                }
            }).setCancelable(false);
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            int index = Arrays.binarySearch(items, type);
            action.upgrade.hero = heroes[index];
            action.upgrade.pieceStrategy = strategies[index];
            if (preIsWhite) {
                result = execute(action, Player.Color.BLACK, enemyAction, false);
            } else {
                result = execute(action, Player.Color.WHITE, enemyAction, false);
            }
            if (result != MoveResult.ILLEGAL) {
                if (result == MoveResult.CHECKMATE) { // 将死
                    if (preIsWhite == false) {
                        showDialog("White wins");
                    } else {
                        showDialog("Black wins");
                    }
                }
                pieces[action.dstPos.row][action.dstPos.col].pieceStrategy = strategies[index];
                pieces[action.dstPos.row][action.dstPos.col].hero = heroes[index];
                pieces[action.dstPos.row][action.dstPos.col].isUpgraded = true;
                updatePre();
                enemyAction = action;
                Display();
                preIsWhite = !preIsWhite;
                undoFinish = false;
            }
            changeState(false);
        }
    }

    /**
     * Perform a Move
     *
     * @param move
     */
    public void executeMove(String move, boolean isAi) {
        if (preIsWhite) {    // white
            action = black.nextAction(move);
            result = execute(action, Player.Color.BLACK, enemyAction, isAi);
        } else {  // black
            action = white.nextAction(move);
            result = execute(action, Player.Color.WHITE, enemyAction, isAi);
        }
    }

    /**
     * Construct move command
     *
     * @param src  src Position
     * @param dest dest Position
     * @return command
     */
    public String createMove(Position src, Position dest) {
        return String.format("%c%d %c%d", src.col + 'a', src.row + 1, dest.col + 'a', dest.row + 1);
    }

    /**
     * create a Dialog
     *
     * @param str the title of dialog
     */
    public void showDialog(String str) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(str);
        builder.setCancelable(false);

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setHint("Please enter the title");
        builder.setView(input);


        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (input.getText().toString().length() != 0) {
                    gameRecord.setTitle(input.getText().toString());
                    gameRecord.setDate(new Date());
                    gameRecord.save();
                    Toast.makeText(getApplicationContext(), "The current game has been saved.", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter title.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    /**
     * Change the state of the chessboard and its related attributes
     */
    public void changeState(boolean isSelected, int... site) {
        if (isSelected) {
            changeBackground(selectPosition.row, selectPosition.col, true);
            selected = true;
            selectPosition.setNewPosition(site[0], site[1]);
            changeBackground(selectPosition.row, selectPosition.col, false);
        } else {
            changeBackground(selectPosition.row, selectPosition.col, true);
            selected = false;
            selectPosition.setNewPosition(-1, -1);
        }
    }

    /**
     * change the color of the grid on the chessboard
     *
     * @param i
     * @param j
     */
    public void changeBackground(int i, int j, boolean restore) {
        if (i == -1)
            return;
        if (restore) {
            if ((i + j) % 2 == 0) {
                DisplayBoardBackground[i][j].setBackgroundResource(R.color.colorBoardLight);
            } else {
                DisplayBoardBackground[i][j].setBackgroundResource(R.color.colorBoardDark);
            }
        } else {
            DisplayBoardBackground[i][j].setBackgroundResource(android.R.color.darker_gray);
        }
    }

    /**
     * Determine whether to click on the black chess piece or not
     *
     * @param state
     * @return
     */
    public boolean isBlackPiece(Drawable.ConstantState state) {
        if (state.equals(getResources().getDrawable(R.drawable.brook).getConstantState()))
            return true;
        if (state.equals(getResources().getDrawable(R.drawable.bknight).getConstantState()))
            return true;
        if (state.equals(getResources().getDrawable(R.drawable.bbishop).getConstantState()))
            return true;
        if (state.equals(getResources().getDrawable(R.drawable.bqueen).getConstantState()))
            return true;
        if (state.equals(getResources().getDrawable(R.drawable.bking).getConstantState()))
            return true;
        if (state.equals(getResources().getDrawable(R.drawable.bpawn).getConstantState()))
            return true;
        return false;
    }

    /**
     * Determine whether to click on the white chess piece or not
     *
     * @param state
     * @return
     */
    public boolean isWhitePiece(Drawable.ConstantState state) {
        if (state.equals(getResources().getDrawable(R.drawable.wrook).getConstantState()))
            return true;
        if (state.equals(getResources().getDrawable(R.drawable.wknight).getConstantState()))
            return true;
        if (state.equals(getResources().getDrawable(R.drawable.wbishop).getConstantState()))
            return true;
        if (state.equals(getResources().getDrawable(R.drawable.wqueen).getConstantState()))
            return true;
        if (state.equals(getResources().getDrawable(R.drawable.wking).getConstantState()))
            return true;
        if (state.equals(getResources().getDrawable(R.drawable.wpawn).getConstantState()))
            return true;
        return false;
    }

    /**
     * whiteKing getter, use it for checking logic. To determine the special piece is white King or not
     *
     * @return Piece type variable whiteKing
     */
    public Piece getWhiteKing() {
        return whiteKing;
    }

    /**
     * blackKing getter, use it for checking logic. To determine the special piece is black King or not
     *
     * @return Piece type variable blackKing
     */
    public Piece getBlackKing() {
        return blackKing;
    }

    /**
     * pieces getter, use it to get the exact position in the 2D array of a special piece.
     *
     * @return Piece[][] type variable pieces
     */
    public Piece[][] GetPieces() {
        return pieces;
    }

//    DisplayBoard[0][0] = (TextView) findViewById(R.id.R00);
//    DisplayBoardBackground[0][0] = (TextView)findViewById(R.id.R000);


//    @SuppressLint("NonConstantResourceId")

    /**
     * execute action
     *
     * @param action      an action object, which represent the action type made by current player(legal, draw, etc.)
     * @param color       a color object, which represent the moving player's color
     * @param enemyAction an action object, which represent the action type made by previous player. For draw request implement
     * @return a MoveResult object, which can help us to know what kind of move a player did. Display() will use it for printing the correct prompt or reminder
     */
    public MoveResult execute(Action action, Player.Color color, Action enemyAction,
                              boolean isAi) {
        if (action.type == Action.Type.ILLEGAL) {
            if (isAi == false) {
                Toast.makeText(getApplicationContext(), "Illegal move, try again1", Toast.LENGTH_SHORT).show();
            }
            return MoveResult.ILLEGAL;
        }
        //FIXME

        if (action.type == Action.Type.RESIGN) {
            if (color == Player.Color.WHITE) {
                System.out.println("Black wins");
            } else {
                System.out.println("White wins");
            }
            return MoveResult.RESIGN;
        }

        if (action.type == Action.Type.DRAW_REQ) {
            return MoveResult.DRAW;
        }

        if (pieces[action.srcPos.row][action.srcPos.col] == null) {
            if (isAi == false) {
                Toast.makeText(getApplicationContext(), "Illegal move, try again2", Toast.LENGTH_SHORT).show();
            }
            return MoveResult.ILLEGAL;
        }
        Piece piece = pieces[action.srcPos.row][action.srcPos.col];
        Log.d("Board", piece.toString());
        if (piece.getColor() != color) {
            if (isAi == false) {
                Toast.makeText(getApplicationContext(), "Illegal move, try again3", Toast.LENGTH_SHORT).show();
            }
            return MoveResult.ILLEGAL;
        }
        MoveResult result = piece.pieceStrategy.NextStep(piece, action);
        if (result == MoveResult.ILLEGAL) {
            if (isAi == false) {
                Toast.makeText(getApplicationContext(), "Illegal move, try again4", Toast.LENGTH_SHORT).show();
            }
        } else {
            piece.setStepNum(piece.getStepNum() + 1);
            if (action.type == Action.Type.DRAW_REQ) {

//                System.out.println();
//                Display();
//                System.out.println();

                System.out.println("draw");
                return MoveResult.DRAW;
            }
        }


        if (result == MoveResult.CHECK) {
            Toast.makeText(getApplicationContext(), "Check", Toast.LENGTH_SHORT).show();
        } else if (result == MoveResult.CHECKMATE) {
//            System.out.println();
//            Display();
//            System.out.println();
//            System.out.println("Checkmate");
            if (color == Player.Color.WHITE) {
                System.out.println("White wins");
            } else {
                System.out.println("Black wins");
            }
        }
        return result;
    }

    // Monitoring events of each button
    public void back(View view) {
        finish();
    }

    public void undo(View view) {
        if (copyPieceISNULL() == false && undoFinish == false) {
            for (int i = 0; i < ROW; i++) {
                for (int j = 0; j < COLUMN; j++) {
                    if (copypieces[i][j] != null) {
                        pieces[i][j] = copypieces[i][j].clone();
                    } else {
                        pieces[i][j] = null;
                    }
                }
            }
            copyPieceNULL();
            Display();
            undoFinish = true;
            preIsWhite = !preIsWhite;
            gameRecord.getLastRole();
            gameRecord.getLastCommand();
            Log.d("Board", "addd" + gameRecord.toString());
        } else {
            Toast.makeText(getApplicationContext(), "There is no undo to do.", Toast.LENGTH_SHORT).show();
        }
    }

    public ArrayList<Position> createAiMove(Piece piece) {
        ArrayList<Position> positions = null;
        switch (piece.hero) {
            case KING:
                positions = new KingStrategy().GetPossibleMoves(piece);
                break;
            case PAWN:
                positions = new PawnStrategy().GetPossibleMoves(piece);
                break;
            case ROOK:
                positions = new RookStrategy().GetPossibleMoves(piece);
                break;
            case QUEEN:
                positions = new QueenStrategy().GetPossibleMoves(piece);
                break;
            case BISHOP:
                positions = new BishopStrategy().GetPossibleMoves(piece);
                break;
            case KNIGHT:
                positions = new KingStrategy().GetPossibleMoves(piece);
                break;
        }
        return positions;
    }

    public void ai(View view) {
        Player player = null;
        if (preIsWhite) {
            player = black;
        } else {
            player = white;
        }
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (player.pieces[i][j] != null && ((player.pieces[i][j].color == Player.Color.WHITE && preIsWhite == false) || (player.pieces[i][j].color == Player.Color.BLACK && preIsWhite))) {
                    list.add(i + " " + j);
                }
            }
        }
        Random rand = new Random();
        while (true) {
            String[] index = list.get(rand.nextInt(list.size())).split(" ");
            ArrayList<Position> positions = null;
            positions = createAiMove(player.pieces[Integer.parseInt(index[0])][Integer.parseInt(index[1])]);
            boolean success = false;
            for (Position position : positions) {
                String move = createMove(player.pieces[Integer.parseInt(index[0])][Integer.parseInt(index[1])].getPosition(), position);
                copyPieceSave();
                action = player.nextAction(move);
                if (action.upgrade != null) {
                    final Hero[] heroes = {Hero.ROOK, Hero.BISHOP, Hero.KNIGHT, Hero.QUEEN};
                    final PieceStrategy[] strategies = {new RookStrategy(), new BishopStrategy(), new KnightStrategy(), new QueenStrategy()};
                    int upindex = randInt();
                    action.upgrade.hero = heroes[upindex];
                    action.upgrade.pieceStrategy = strategies[upindex];
                    pieces[action.srcPos.row][action.srcPos.col].pieceStrategy = strategies[upindex];
                    pieces[action.srcPos.row][action.srcPos.col].hero = heroes[upindex];
                    if (preIsWhite) {
                        result = execute(action, Player.Color.BLACK, enemyAction, true);
                    } else {
                        result = execute(action, Player.Color.WHITE, enemyAction, true);
                    }
                } else {
                    if (preIsWhite) {
                        result = execute(action, Player.Color.BLACK, enemyAction, true);
                    } else {
                        result = execute(action, Player.Color.WHITE, enemyAction, true);
                    }
                }
                if (result != MoveResult.ILLEGAL) {
                    if (result == MoveResult.CHECKMATE) { // 将死
                        if (preIsWhite == false) {
                            showDialog("White wins");
                        } else {
                            showDialog("Black wins");
                        }
                    }
                    undoFinish = false;
                    updatePre();
                    gameRecord.addRole(preIsWhite);
                    gameRecord.addNewCommand(move);
                    enemyAction = action;
                    Display();
                    preIsWhite = !preIsWhite;
                    success = true;
                    changeState(false);
                    break;
                } else {
                    copyPieceNULL();
                    continue;
                }
            }
            if (success) {
                break;
            }
        }

    }

    public int randInt() {
        int min = 1;
        int max = 4;
        int range = max - min + 1;
        int randomNum = (int) (Math.random() * range) + min;
        return randomNum - 1;
    }


    public void resign(View view) {
        String move = "resign";
        executeMove(move, false);
        if (result == MoveResult.RESIGN) {
            gameRecord.addRole(preIsWhite);
            gameRecord.addNewCommand(move);
            if (preIsWhite == false) {
                showDialog("Black wins");
            } else {
                showDialog("White wins");
            }
        }
    }

    public void draw(View view) {
        String move = "draw?";
        executeMove(move, false);
        if (result == MoveResult.DRAW) {
            gameRecord.addRole(preIsWhite);
            gameRecord.addNewCommand(move);
            showDialog("Draw");
        }
    }

    public void nextMove(View view) {
        if (gameRecord.getIndex() == -1) {
            nextMoveButton.setClickable(false);
            nextMoveButton.setBackgroundColor(Color.GRAY);
        } else {
            String[] moves = gameRecord.getCommand().get(gameRecord.getIndex()).split(" ");
            String move = moves[0];
            if (moves.length > 1) {
                move += " " + moves[1];
            }
            String type = null;
            if (moves.length == 3) {
                type = moves[2];
            }
            if (gameRecord.getRole().get(gameRecord.getIndex()).equals("white")) {
                action = black.nextAction(move);
            } else {
                action = white.nextAction(move);
            }
            if (action.upgrade != null && type != null) {
                upgrade(move, type);
            } else {
                if (gameRecord.getRole().get(gameRecord.getIndex()).equals("white")) {
                    result = execute(action, Player.Color.BLACK, enemyAction, false);
                } else {
                    result = execute(action, Player.Color.WHITE, enemyAction, false);
                }
                if (result == MoveResult.CHECKMATE) { // 将死
                    if (preIsWhite == false) {
                        showDialog("White wins");
                    } else {
                        showDialog("Black wins");
                    }
                } else if (result == MoveResult.RESIGN) {
                    if (gameRecord.getRole().get(gameRecord.getIndex()) == "white") {
                        Toast.makeText(getApplicationContext(), "White wins", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Black wins", Toast.LENGTH_LONG).show();
                    }
                    nextMoveButton.setClickable(false);
                    nextMoveButton.setBackgroundColor(Color.GRAY);
                } else if (result == MoveResult.DRAW) {
                    Toast.makeText(getApplicationContext(), "Draw", Toast.LENGTH_LONG).show();
                    nextMoveButton.setClickable(false);
                    nextMoveButton.setBackgroundColor(Color.GRAY);
                } else {
                    enemyAction = action;
                    Display();
                }
                gameRecord.addIndex();
            }
        }
    }
}

