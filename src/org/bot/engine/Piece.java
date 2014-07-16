 package org.bot.engine;

import java.util.Objects;

public class Piece implements Comparable<Piece> {
    private Piece loses;
    private String name;
    private Piece wins;
    
    private static final String TO_STRING = "%s {wins=%s, loses=%s}";

    public Piece getLoses() {
        return loses;
    }

    public String getName() {
        return name;
    }

    public Piece getWins() {
        return wins;
    }

    public void setLoses(Piece loses) {
        this.loses = loses;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWins(Piece wins) {
        this.wins = wins;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Piece other = (Piece) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format(TO_STRING, name, wins.getName(), loses.getName());
    }

    @Override
    public int compareTo(Piece o) {
        if(this.equals(o)) {
            return 0;
        } else {
            if(o.equals(getLoses())) {
                return 1;
            } else {
                return -1;
            }
        }
    }
    
}
