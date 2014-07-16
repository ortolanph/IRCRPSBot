package org.bot.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RPSAssembly {
    private final Map<String, Piece> pieceMap;
    private final List<String> pieceNames;

    public RPSAssembly() {
        Piece rock = new Piece();
        Piece paper = new Piece();
        Piece scissors = new Piece();
        
        rock.setName("ROCK");
        rock.setLoses(paper);
        rock.setWins(scissors);
        
        paper.setName("PAPER");
        paper.setLoses(scissors);
        paper.setWins(rock);
        
        scissors.setName("SCISSORS");
        scissors.setLoses(rock);
        scissors.setWins(paper);
        
        pieceMap = new HashMap<>();
        pieceMap.put(rock.getName(), rock);
        pieceMap.put(paper.getName(), paper);
        pieceMap.put(scissors.getName(), scissors);
        
        pieceNames = new ArrayList<>();
        pieceNames.add(rock.getName());
        pieceNames.add(paper.getName());
        pieceNames.add(scissors.getName());
    }

    public Map<String, Piece> getPieceMap() {
        return new HashMap<>(pieceMap);
    }

    public List<String> getPieceNames() {
        return new ArrayList<>(pieceNames);
    }
    
}
