import java.util.ArrayList;
import java.util.List;

public class Board8 extends  Board
{
    public Board8(int width, int height)
    {
        super(width, height);
    }

    @Override
    protected Cell[] getAliveNeighbours(int x, int y)
    {
        List<Cell> neighbours = new ArrayList<>();

        int mi = Util.clamp(x + 1, 0, width);
        int mj = Util.clamp(y + 1, 0, height);

        for(int j = Util.clamp( y - 1, 0, height); j <= mj; j++)
            for(int i = Util.clamp( x - 1, 0, width); i <= mi; i++)
            {
                if(i == x && j == y)
                    continue;
                neighbours.add(cells[j][i]);

            }

        return neighbours.toArray(new Cell[0]);
    }
}
