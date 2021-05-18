import java.util.ArrayList;
import java.util.List;

public class Board4Wrap extends Board
{
    public Board4Wrap(int width, int height)
    {
        super(width, height);
    }

    @Override
    protected Cell[] getNeighbours(int x, int y)
    {
        List<Cell> neighbours = new ArrayList<>();

        //* Nie ma sensu robić pętli dla 4 elementów
        neighbours.add(cells[y][Util.wrap(x - 1, 0, width)]);   // Lewo
        neighbours.add(cells[y][Util.wrap(x + 1, 0, width)]);   // Prawo
        neighbours.add(cells[Util.wrap(y - 1, 0, height)][x]);  // Dół
        neighbours.add(cells[Util.wrap(y + 1, 0, height)][x]);  // Góra

        return neighbours.toArray(new Cell[0]);
    }

    @Override
    public Board copy()
    {
        Board4Wrap newBoard = new Board4Wrap(width, height);
        newBoard.cells = Util.copy2dArrayOfCells(cells);

        return newBoard;
    }
}
