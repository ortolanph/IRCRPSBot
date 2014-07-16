package org.bot.engine;

public class RPSEngine {

    private final RPSAssembly assembly;

    public RPSEngine(RPSAssembly assembly) {
        this.assembly = assembly;
    }

    public String play(String piece) {
        Piece myPiece = getRandomPiece();
        Piece yourPiece = parsePiece(piece);

        String message = "";

        if (yourPiece == null) {
            return error();
        }

        int result = myPiece.compareTo(yourPiece);

        if (result == 0) {
            message = "DRAW = " + myPiece.getName() + "=" + yourPiece.getName();
        } else {
            if (result > 0) {
                message = "You won = " + myPiece.getName() + " loses " + yourPiece.getName();
            } else {
                message = "I won = " + myPiece.getName() + " wins " + yourPiece.getName();
            }
        }

        return message;
    }

    public String info(String piece) {
        Piece info = parsePiece(piece);

        if (info == null) {
            return error();
        } else {
            return info.toString();
        }
    }

    public String help() {
        StringBuilder help = new StringBuilder();

        help.append("IRC RPS: ")
                .append("!RPSPLAY «ROCK|PAPER|SCISSORS» or ")
                .append("!RPSINFO «ROCK|PAPER|SCISSORS»");

        return help.toString();
    }

    private Piece parsePiece(String pieceName) {
        return assembly.getPieceMap().get(pieceName);
    }

    private Piece getRandomPiece() {
        return assembly.getPieceMap().get(assembly.getPieceNames().get((int) (Math.random() * 3)));
    }

    private String error() {
        return "ERROR! PIECE NOT AVAILABLE! ONLY «ROCK,PAPER,SCISSORS» AVAILABLE!";
    }
}
