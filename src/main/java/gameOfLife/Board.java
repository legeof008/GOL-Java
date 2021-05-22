package main.java.gameOfLife;

import java.util.ArrayList;
import java.util.Random;
//import javafx.util.Pair;

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

        for(int j = 0; j < height; j++)
            for(int i = 0; i < width; i++)
                cells[j][i] = new Cell();
    }

    /**
     * Tworzy i zwraca głęboką kopię planszy
     */
    public abstract Board copy();

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
     * Jeżeli tworzona komórka jest żywa, zwiększa ilość sąsiadów komórkom na kooło
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
        cells[y][x] = new Cell(r, g, b, cellType, cells[y][x].getNeighbourCount());

        if(cells[y][x].isAlive())
            incrementNeighbours(x, y);
    }

    /**
     * Zwiększa liczbę sąsiadów komórek znajdujących się wokół komórki o pozycji x, y
     * @param x pozycja x komórki
     * @param y pozycja y komórki
     */
    public void incrementNeighbours(int x, int y)
    {
        Cell[] neighbours = getNeighbours(x, y);

        for (Cell c : neighbours)
            c.addNeighbour();
    }

    /**
     * @param x pozycja x komórki
     * @param y pozycja y komórki
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
     * @param board plansza z początku cyklu
     */
    /*public void makeCellAlive(int x, int y, main.java.Board board)
    {
        if(board == null || board.width != width || board.height != height)
            throw new IllegalArgumentException("wymiary planszy z początku cyklu nie są zgodne z obecną planszą");

        Pair<main.java.Cell, main.java.Cell> parents = board.getParents(x, y);

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
                newBColor += (main.java.Util.getBit(parents.getValue().b, bitIndices[i]) - main.java.Util.getBit(newBColor, bitIndices[i])) << bitIndices[i];
            else if (bitIndices[i] < 16)
                newGColor += (main.java.Util.getBit(parents.getValue().g, bitIndices[i]) - main.java.Util.getBit(newGColor, bitIndices[i])) << (bitIndices[i] % 8);
            else
                newRColor += (main.java.Util.getBit(parents.getValue().r, bitIndices[i]) - main.java.Util.getBit(newRColor, bitIndices[i])) << (bitIndices[i] % 8);
        }

        cells[y][x] = new main.java.Cell(newRColor, newGColor, newBColor, main.java.CellType.Alive, cells[y][x].getNeighbourCount());
        addNeighbourParameter(x, y);
    }*/
    public void makeCellAlive(int x, int y, Board board)
    {
        Cell[] parents = board.getParents(x, y);

        int newRColor = parents[0].getR();
        int newGColor = parents[0].getG();
        int newBColor = parents[0].getB();

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
                newBColor += (Util.getBit(parents[1].getB(), bitIndices[i]) - Util.getBit(newBColor, bitIndices[i])) << bitIndices[i];
            else if (bitIndices[i] < 16)
                newGColor += (Util.getBit(parents[1].getG(), bitIndices[i]) - Util.getBit(newGColor, bitIndices[i])) << (bitIndices[i] % 8);
            else
                newRColor += (Util.getBit(parents[1].getR(), bitIndices[i]) - Util.getBit(newRColor, bitIndices[i])) << (bitIndices[i] % 8);
        }

        cells[y][x] = new Cell(newRColor, newGColor, newBColor, CellType.Alive, cells[y][x].getNeighbourCount());
        incrementNeighbours(x, y);
    }

    /*private Pair<main.java.Cell, main.java.Cell> getParents(int x, int y)
    {
        main.java.Cell[] neighbours = getNeighbours(x, y);

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


        return new Pair<main.java.Cell, main.java.Cell>(neighbours[parentsIndices[0]], neighbours[parentsIndices[1]]);
    }*/
    private Cell[] getParents(int x, int y)
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


        return new Cell[]{neighbours[parentsIndices[0]], neighbours[parentsIndices[1]]};
    }

    private Cell[] getAliveNeighbours(int x, int y)
    {
        ArrayList<Cell> aliveNeighbours = new ArrayList<>();
        Cell[] neighbours = getNeighbours(x, y);

        for(Cell c : neighbours)
        {
            if(c.isAlive())
                aliveNeighbours.add(c);
        }

        return aliveNeighbours.toArray(new Cell[0]);
    }

    protected abstract Cell[] getNeighbours(int x, int y);

    /**
     * Zabija daną komórkę (zmienia typ na main.java.CellType.Dead oraz zmienia kolor na czarny)
     * @param x pozycja x komórki
     * @param y pozycja y komórki
     */
    public void killCell(int x, int y)
    {
        cells[y][x] = new Cell(cells[y][x].getNeighbourCount());

        Cell[] neighbours = getNeighbours(x, y);

        for(Cell c : neighbours)
            c.substractNeighbour();
    }


    // TODO: Tylko do testowania; w normalnym działaniu programu plansza powinna być tworzona z pliku
    public void populateCells()
    {
        for(int j = 0; j < height; j++)
            for(int i = 0; i < width; i++)
                cells[j][i] = new Cell();
    }

    // TODO: Tylko do testowania
    public void writeInfo()
    {
        for(int j = 0; j < height; j++)
        {
            for(int i = 0; i < width; i++)
                System.out.printf("%d", cells[j][i].getNeighbourCount());

            System.out.print('\t');

            for(int i = 0; i < width; i++)
                System.out.printf("%c ", cells[j][i].isAlive()?'*':cells[j][i].isWall()?'Ø':'.');
            System.out.println();
        }
    }
}
