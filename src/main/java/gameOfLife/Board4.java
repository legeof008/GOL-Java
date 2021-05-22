package main.java.gameOfLife;

import java.util.ArrayList;
import java.util.List;

public class Board4 extends Board
{
    public Board4(int width, int height)
    {
        super(width, height);
    }

    @Override
    protected Cell[] getNeighbours(int x, int y)
    {
        List<Cell> neighbours = new ArrayList<>();

        //* Nie ma sensu robić pętli dla 4 elementów
        if(x - 1 >= 0)      // Lewo
            neighbours.add(cells[y][x - 1]);
        if(x + 1 < width)   // Prawo
            neighbours.add(cells[y][x + 1]);
        if(y - 1 >= 0)      // Dół
            neighbours.add(cells[y - 1][x]);
        if(y + 1 < height)  // Góra
            neighbours.add(cells[y + 1][x]);

        return neighbours.toArray(new Cell[0]);
    }

    @Override
    public Board copy()
    {
        Board4 newBoard = new Board4(width, height);
        newBoard.cells = Util.copy2dArrayOfCells(cells);

        return newBoard;
    }
}
