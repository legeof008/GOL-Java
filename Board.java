import java.util.Random;
import javafx.util.Pair;

public abstract class Board
{
    protected int width;
    protected int height;
    protected Cell[][] cells;


    protected Board(int width, int height)
    {
        this.width = width;
        this.height = height;

        cells = new Cell[height][width];
    }

    /**
     * @return true jeżeli na planszy nie znajduje się żadna żywa komórka
     */
    public boolean isEmpty()
    {
        for(int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
            {
                if (cells[y][x].isAlive())
                    return false;
            }

        return true;
    }

    /**
     * @param x pozycja x komórki
     * @param y pozycja y komórki
     * @return komórka na pozycji x, y
     */
    public Cell getCell(int x, int y)
    {
        return cells[y][x];
    }

    /**
     * Tworzy nową komórkę z podanych parametrów i ustawia ją w pozycji x, y.
     * Ta metoda powinna być używana tylko przy początkowym tworzeniu planszy;
     * Do tworzenia nowych komórek między cyklami lepiej jest użyć <code>makeCellAlive()</code>
     * @param x pozycja x komórki w tablicy
     * @param y pozycja y komórki w tablicy
     * @param r parametr R komórki z zakresu <0, 255>
     * @param g parametr G komórki z zakresu <0, 255>
     * @param b parametr B komórki z zakresu <0, 255>
     * @param cellType typ komórki (Dead, Alive, Wall)
     */
    public void setCell(int x, int y, int r, int g, int b, CellType cellType)
    {
        cells[y][x] = new Cell(r, g, b, cellType);
    }

    /**
     * Zwiększa liczbę sąsiadów komórek znajdujących się wokół komórki o pozycji x, y
     * @param x pozycja x komórki
     * @param y pozycja y komórki
     */
    public void addNeighbourParameter(int x, int y)
    {
        Cell[] neighbours = getAliveNeighbours(x, y);

        for (Cell c : neighbours)
            c.addNeighbour();
    }

    /**
     * @param x pozycja x komórki
     * @param y pozycjia y komórki
     * @return liczba siąsiadów komórki znajdującej się na pozycji x, y
     */
    public int getCellNeighbourCount(int x, int y)
    {
        return cells[y][x].getNeighbourCount();
    }

    /**
     * Tworzy nową, żywą komórkę na pozycji x, y.
     * Kolor będzie wygenerowany na podstawie sąsiadów komórki.
     * Liczba sąsiadów zostanie skopiowana z poprzedniej komórki znajdującej się na pozycji x, y
     * @param x pozycja x komórki do ożywienia
     * @param y pozycja y komórki do ożywienia
     */
    public void makeCellAlive(int x, int y)
    {
        Pair<Cell, Cell> parents = getParents(x, y);

        int newRColor = parents.getKey().r;
        int newGColor = parents.getKey().g;
        int newBColor = parents.getKey().b;

        int[] bitIndices = new int[3];

        // Losowanie 3 losowych liczb z zakresu <0, 24> bez powtórzeń
        Random rnd = new Random();
        bitIndices[0] = rnd.nextInt(24);

        do
        {
            bitIndices[1] = rnd.nextInt(24);
        } while (bitIndices[1] == bitIndices[0]);

        do
        {
            bitIndices[2] = rnd.nextInt(24);
        } while (bitIndices[2] == bitIndices[1] || bitIndices[2] == bitIndices[0]);

        // Dodawanie kolorów 2 rodzica do kolorów nowej komórki
        for(int i = 0; i < 3; i++)
        {
            if(bitIndices[i] < 8)
                newBColor += (Util.getBit(parents.getValue().b, bitIndices[i]) - Util.getBit(newBColor, bitIndices[i])) << bitIndices[i];
            else if (bitIndices[i] < 16)
                newGColor += (Util.getBit(parents.getValue().g, bitIndices[i]) - Util.getBit(newGColor, bitIndices[i])) << (bitIndices[i] % 8);
            else
                newRColor += (Util.getBit(parents.getValue().r, bitIndices[i]) - Util.getBit(newRColor, bitIndices[i])) << (bitIndices[i] % 8);
        }

        cells[y][x] = new Cell(newRColor, newGColor, newBColor, CellType.Alive, cells[y][x].getNeighbourCount());
    }

    protected Pair<Cell, Cell> getParents(int x, int y)
    {
        Cell[] neighbours = getAliveNeighbours(x, y);

        if(neighbours.length < 2)
            throw new RuntimeException(String.format("komórka na pozycji %d, %d nie posiada 2 żywych sąsiadów", x, y));

        int[] parentsIndices = new int[2];

        // Losowanie 2 losowych liczb odpowiadjących indeksom rodziców
        Random rnd = new Random();
        parentsIndices[0] = rnd.nextInt(neighbours.length);

        do
        {
            parentsIndices[1] = rnd.nextInt(neighbours.length);
        } while (parentsIndices[1] == parentsIndices[0]);


        return new Pair<Cell, Cell>(neighbours[parentsIndices[0]], neighbours[parentsIndices[1]]);
    }

    protected Cell[] getAliveNeighbours(int x, int y)
    {
        throw new UnsupportedOperationException("Nie powinno się to stać");
    }

    // TODO: Tylko do testowania; w normalnym działaniu programu plansza powinna być tworzona z pliku
    public void populateCells()
    {
        for(int j = 0; j < height; j++)
            for(int i = 0; i < width; i++)
                cells[j][i] = new Cell(0,0 ,0);
    }

    // TODO: Tylko do testowania
    public void writeNeighbours()
    {
        for(int j = 0; j < height; j++)
        {
            for(int i = 0; i < width; i++)
                System.out.printf("%d ", cells[j][i].getNeighbourCount());
            System.out.println();
        }
    }
}
