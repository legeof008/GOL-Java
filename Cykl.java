public abstract class Cykl {
    protected int width;
    protected int heigh;
    protected Cell[][] cells;
    protected Cell[][] tempCells;

    public void MakeACycle() {
        int i;
        int j;
        tempCells = new Cell[width][heigh];
        for (i = 0; i < width; i++) {
            for (j = 0; j < heigh; j++) {
                if(cells.isWall()==true){
                    tempCells[i][j]=cells[i][j];
                }
                if(cells.isalive()==true){
                    if(cells.getNeighbourCount()==3 || cells.getNeighbourCount()==2)
                    {
                    tempCells[i][j]=cells[i][j];
                    }

                }
                if(cells.isDead()==true){     //nie pasuje mi tutaj to rzowiązanie na sztywno zmiana w cells problemy z przepisywaniem potem. rozwiązane ale na około
                    if(cells.getNeighbourCount()==3){
                        cells.makeCellAlive(i,j);
                        subNeighbour();
                        tempCells[i][j]=cells[i][j];
                        this.CellType.Dead;
                    }
                }
            }
        }
        cells=tempCells;
    }
    //chwilowe obejście problemu tworzenia
    public void subNeighbour()
    {
        neighbourCount--;
    }
    public void subNeighbourParameter(int x, int y)
    {
        Cell[] neighbours = getAliveNeighbours(x, y);

        for (Cell c : neighbours)
            c.subNeighbour();
    }
}
