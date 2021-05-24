package main.java.gameOfLife;

import java.util.ArrayList;
import java.util.List;

public class Board8Wrap extends Board {
    public Board8Wrap(int width, int height) {
        super(width, height);
    }

    @Override
    protected Cell[] getNeighbours(int x, int y) {
        List<Cell> neighbours = new ArrayList<>();

        int mi = Util.wrap(x + 2, 0, width);
        int mj = Util.wrap(y + 2, 0, height);

        for (int j = Util.wrap(y - 1, 0, height); j != mj; j = Util.wrap(++j, 0, height))
            for (int i = Util.wrap(x - 1, 0, width); i != mi; i = Util.wrap(++i, 0, width)) {
                if (i == x && j == y)
                    continue;
                neighbours.add(cells[j][i]);
            }

        return neighbours.toArray(new Cell[0]);
    }

    @Override
    public Board copy() {
        Board8Wrap newBoard = new Board8Wrap(width, height);
        newBoard.cells = Util.copy2dArrayOfCells(cells);

        return newBoard;
    }
}
