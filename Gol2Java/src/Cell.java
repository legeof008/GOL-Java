
public class Cell
{
    private final int r;
    private final int g;
    private final int b;
    private final CellType cellType;
    private int neighbourCount;

    /**
     * Tworzy nową martwą komórkę. Kolor jest ustawiany na czarny (#000000)
     */
    public Cell()
    {
        this(0, 0, 0, CellType.Dead);
    }

    public Cell(int neighbourCount)
    {
        this(0, 0, 0, CellType.Dead, neighbourCount);
    }

    /**
     * Tworzy nową komórkę z podanymi parametrami. Typ komórki jest ustawiany na CellType.Alive.
     * Liczba sąsiadów jest ustawiana na 0
     * @param r składowa R koloru komórki. Musi należeć do przedziału <0, 255>
     * @param g składowa G koloru komórki. Musi należeć do przedziału <0, 255>
     * @param b składowa B koloru komórki. Musi należeć do przedziału <0, 255>
     */
    public Cell(int r, int g, int b)
    {
        this(r, g, b, CellType.Alive);
    }

    /**
     * Tworzy nową komórkę z podanymi parametrami. Liczba sąsiadów jest ustawiana na 0
     * @param r składowa R koloru komórki. Musi należeć do przedziału <0, 255>
     * @param g składowa G koloru komórki. Musi należeć do przedziału <0, 255>
     * @param b składowa B koloru komórki. Musi należeć do przedziału <0, 255>
     * @param cellType typ komórki (Dead, Alive, Wall)
     */
    public Cell(int r, int g, int b, CellType cellType)
    {
        this(r, g, b, cellType, 0);
    }

    /**
     * Tworzy nową komórkę z podanymi parametrami
     * @param r składowa R koloru komórki. Musi należeć do przedziału <0, 255>
     * @param g składowa G koloru komórki. Musi należeć do przedziału <0, 255>
     * @param b składowa B koloru komórki. Musi należeć do przedziału <0, 255>
     * @param cellType typ komórki (Dead, Alive, Wall)
     * @param neighbourCount startowa liczba sąsiadów komórki
     */
    public Cell(int r, int g, int b, CellType cellType, int neighbourCount)
    {
        this.r = r;
        this.g = g;
        this.b = b;
        this.cellType = cellType;
        this.neighbourCount = neighbourCount;
    }

    /**
     * Klonuje podaną komórkę
     * @param cell komórka źródłowa
     */
    public Cell(Cell cell)
    {
        r = cell.r;
        g = cell.g;
        b = cell.b;
        cellType = cell.cellType;
        neighbourCount = cell.neighbourCount;
    }

    public int getR()
    {
        return r;
    }

    public int getG()
    {
        return g;
    }

    public int getB()
    {
        return b;
    }

    /**
     * Zwiększa liczbę sąsiadów komórki
     */
    public void addNeighbour()
    {
        neighbourCount++;
    }

    public void substractNeighbour()
    {
        neighbourCount--;
        if(neighbourCount < 0)
            throw new IllegalStateException("Komórka posiada ujemną ilość sąsiadów");
    }

    public int getNeighbourCount()
    {
        return neighbourCount;
    }

    public boolean isAlive()
    {
        return cellType == CellType.Alive;
    }

    public boolean isWall()
    {
        return cellType == CellType.Wall;
    }
}

