package main.java.gameOfLife;


public class Cykl {

    /**
     * Metoda przeprowadza cykl wg logiki gry
     *
     * @param board
     * @return copy (Board)
     */
    public static Board MakeACycle(Board board) {
        int i;
        int j;
        Board copy = board.copy();
        for (i = 0; i < board.width; i++) {
            for (j = 0; j < board.height; j++) {
                if (board.getCell(i, j).isAlive() == true) {
                    if (board.getCellNeighbourCount(i, j) > 3 || board.getCellNeighbourCount(i, j) < 2) {
                        copy.killCell(i, j);
                    }
                } else if (board.getCell(i, j).isWall() == false) {
                    if (board.getCellNeighbourCount(i, j) == 3) {
                        copy.makeCellAlive(i, j, board);
                    }
                }
            }
        }
        return copy;
    }
}
