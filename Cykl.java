public abstract class Cykl {
    protected int width;
    protected int heigh;
    protected Cell[][] cells;
    protected Cell[][] tempCells;

    public void MakeACycle() {
        int i;
        int j;
        Board copy = board.copy();
        for (i = 0; i < width; i++) {
            for (j = 0; j < heigh; j++) {
                if(board.getCell.isalive()==true){
                    if(board.getCellNeighbourCount(i, j)>3 || board.getCellNeighbourCount(i, j)<2)
                    {
                        copy.killCell(i, j);
                    }
                }
                if(board.getCell.isDead()==true){
                    if(board.getCellNeighbourCount(i, j)==3){
                        copy.makeCellAlive(i,j, board);
                    }
                }
            }
        }
        board=copy;
    }
}
