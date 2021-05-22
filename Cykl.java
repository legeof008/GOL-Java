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
                tempCells[i][j]=new cell();
                if(cells.isWall()==true){
                    tempCells[i][j]=cells[i][j];
                }
                if(cells.isalive()==true){
                    if(cells.getNeighbourCount()==3 || cells.getNeighbourCount()==2)
                    {
                    tempCells[i][j]=cells[i][j];
                    }

                }
                if(cells.isDead()==true){     
                    if(cells.getNeighbourCount()==3){
                        cells.makeCellAlive(i,j);
                        tempCells[i][j]=cells[i][j];
                        killCell(i, j);
                    }
                }
            }
        }
        cells=tempCells;
    }

}
