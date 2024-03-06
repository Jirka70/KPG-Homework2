package org.kpg2;

import java.util.Objects;

public class Block {
    private final int x;
    private final int y;
    private boolean isWall = false;
    public Block(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isWall() {
        return isWall;
    }

    public void setWall(boolean wall) {
        isWall = wall;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Block block)) return false;
        return x == block.x && y == block.y && isWall == block.isWall;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, isWall);
    }

    @Override
    public String toString() {
        return "Block{" +
                "x=" + x +
                ", y=" + y +
                ", isWall=" + isWall +
                '}';
    }
}
