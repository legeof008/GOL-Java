package main.java.gameOfLife;

import main.java.BoardParametersException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FirstBoard {

    private static final int MAXHEIGHT = 1000;
    private static final int MAXWIDTH = 1000;

    /**
     * @param f plik z parametrami wejściowymi
     * @return plansza startowa zgodna z parametrami wejściowymi
     */
    public static Board readBoard(File f) throws IOException, CellParametersException, BoardParametersException {

        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);
        Board board;

        /*
         * Czytamy pierwszą linię z parametrami sąsiedztwa i zawijania oraz rozmiarami planszy.
         */

        String line = br.readLine();
        String[] words = line.split("\\s+");

        if (words.length != 4)
            throw new BoardParametersException("Nieprawidłowo podane parametry planszy");

        int neighborsType = Integer.parseInt(words[0]);
        if (!isCorrectNeighborsType(neighborsType))
            throw new BoardParametersException("Nieprawidłowy parametr sąsiedztwa");
        int foldType = Integer.parseInt(words[1]);
        if (!isCorrectFoldType(foldType))
            throw new BoardParametersException("Nieprawidłowy parametr zawijania");

        int width = Integer.parseInt(words[2]);
        int height = Integer.parseInt(words[3]);
        if (!isCorrectSizeOfBoard(width, height)) {
            throw new BoardParametersException("Nieprawidłowe wymiary planszy");
        }

        //Tworzymy odpowiednią planszę
        board = createBoard(neighborsType, foldType, width, height);

        /*
         * Czytamy parametry poszczególnych komórek
         */

        while ((line = br.readLine()) != null) {
            words = line.split("\\s+");

            if (words.length != 6 && words.length != 3)
                throw new CellParametersException("Nieprawidłowo podane parametry komórek");

            int x = Integer.parseInt(words[0]);
            int y = Integer.parseInt(words[1]);
            int cellType = Integer.parseInt(words[2]);

            //Domyślnie ustawiam niebieski - kolor ściany
            int R = 0;
            int G = 0;
            int B = 255;
            if (words.length == 6) {
                R = Integer.parseInt(words[3]);
                G = Integer.parseInt(words[4]);
                B = Integer.parseInt(words[5]);
            }

            if (!isCorrectCellParameters(width, height, x, y, cellType, R, G, B))
                throw new CellParametersException("Nieprawidłowe wartości parametrów komórki");

            //Ustalamy typ komórki
            CellType ct = typeOfCell(cellType);

            //Dodajemy komórkę do planszy
            board.setCell(x, y, R, G, B, ct);

        }
        return board;
    }

    /**
     * @param neighborsType typ sąsiedztwa - 4 lub 8
     * @param foldType      zawijanie - 1 zawija lub 0 brak zawijania
     * @param width         szerokość planszy
     * @param height        wysokość planszy
     * @return odpowiedni rodzaj planszy startowego zależny od parametrów wejściowych
     */
    private static Board createBoard(int neighborsType, int foldType, int width, int height) {
        Board board;

        if (neighborsType == 4 && foldType == 0) {
            board = new Board4(width, height);
        } else if (neighborsType == 4) {
            board = new Board4Wrap(width, height);
        } else if (foldType == 0) {
            board = new Board8(width, height);
        } else {
            board = new Board8Wrap(width, height);
        }
        return board;
    }

    /**
     * @return true jeżeli typ sąsiedztwa został prawidłowo podany (wartość 4 lub 8)
     */
    private static boolean isCorrectNeighborsType(int neighborsType) {
        boolean isCorrect = neighborsType == 4 || neighborsType == 8;

        return isCorrect;
    }

    /**
     * @return true jeżeli typ zawijania został prawidłowo podany (wartość 1 lub 0)
     */
    private static boolean isCorrectFoldType(int foldType) {
        boolean isCorrect = foldType == 1 || foldType == 0;

        return isCorrect;
    }

    /**
     * @return true jeżeli podano prawidłowe wymiary planszy
     */
    private static boolean isCorrectSizeOfBoard(int width, int height) {
        boolean isCorrect = width > 0 && width <= MAXWIDTH && height > 0 && height <= MAXHEIGHT;

        return isCorrect;
    }

    /**
     * @return true jeżeli podano prawidłowe parametry komórki
     */
    private static boolean isCorrectCellParameters(int boardWidth, int boardHeight, int x, int y, int cellType, int R, int G, int B) {
        boolean isCorrect = x >= 0 && x < boardWidth && y >= 0 && y < boardHeight;

        if (cellType != 0 && cellType != 1 && cellType != 2)
            isCorrect = false;

        if (R < 0 || R > 255 || G < 0 || G > 255 || B < 0 || B > 255)
            isCorrect = false;

        //Ściana może być tylko koloru niebieskiego
        if (cellType == 2 && R != 0 && G != 0 && B != 255)
            isCorrect = false;

        return isCorrect;
    }

    /**
     * @param typeNumber typ komórki określony za pomocą cyfry 0, 1 lub 2
     * @return odpowiedni typ komórki
     */
    private static CellType typeOfCell(int typeNumber) {
        CellType ct;

        if (typeNumber == 0)
            ct = CellType.Dead;
        else if (typeNumber == 1)
            ct = CellType.Alive;
        else
            ct = CellType.Wall;

        return ct;
    }
}


