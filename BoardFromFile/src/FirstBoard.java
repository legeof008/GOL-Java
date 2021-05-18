import java.io.*;

public class FirstBoard {

    private static final int MAXHEIGHT = 1000;
    private static final int MAXWIDTH = 1000;

    public static Board readBoard(String filePath) throws IOException {

        File f = new File(filePath);
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);

        Board board = null;

        //
        //Czytamy pierwszą linię z wielkością planszy, parametrami sąsiedztwa i zawijania.
        //

        String line = br.readLine();
        String[] words = line.split("\\s+");

        int neighbors = Integer.parseInt(words[0]);
        if(neighbors != 4 && neighbors != 8){
            throw new NumberFormatException("Nieprawidłowy parametr sąsiedztwa");
        }
        int foldType = Integer.parseInt(words[1]);
        if(foldType != 1 && foldType != 0)
            throw new NumberFormatException("Nieprawidłowy parametr zawijania");

        int w = Integer.parseInt(words[2]);
        if(w <= 0 || w > MAXWIDTH){
            throw new NumberFormatException("Nieprawidłowa szerokość planszy");
        }
        int h = Integer.parseInt(words[3]);
        if(h <= 0 || h > MAXHEIGHT){
            throw new NumberFormatException("Nieprawidłowa wysokość planszy");
        }

        //Tworzymy odpowiednią planszę
        if(neighbors == 4 && foldType == 0){
            board = new Board4(w, h);
        }
        else if(neighbors == 4 && foldType == 1){
            board = new Board4Wrap(w, h);
        }
        else if(neighbors == 8 && foldType == 0){
            board = new Board8(w, h);
        }
        else{
            board = new Board8Wrap(w, h);
        }

        //
        //Czytamy parametry poszczególnych komórek
        //

        while((line = br.readLine()) != null){
            words = line.split("\\s+");

            if(words.length != 6)
                continue;

            int x = Integer.parseInt(words[0]);
            if(x < 0 || x >= w)
                throw new NumberFormatException("Nieprawidłowe współrzędne komórek");

            int y = Integer.parseInt(words[1]);
            if(y< 0 || y >= h)
                throw new NumberFormatException("Nieprawidłowe współrzędne komórek");

            int cellType = Integer.parseInt(words[2]);
            if(cellType != 0 && cellType != 1 && cellType != 2)
                throw new NumberFormatException("Nieprawidłowy typ komórek");

            CellType ct;
            if(cellType == 0)
                ct = CellType.Dead;
            else if(cellType == 1)
                ct = CellType.Alive;
            else
                ct = CellType.Wall;

            int R = Integer.parseInt(words[3]);
            if(R < 0 || R > 255)
                throw new NumberFormatException("Nieprawidłowe wparametry RGB");

            int G = Integer.parseInt(words[4]);
            if(G < 0 || G > 255)
                throw new NumberFormatException("Nieprawidłowe wparametry RGB");

            int B = Integer.parseInt(words[5]);
            if(B < 0 || B > 255)
                throw new NumberFormatException("Nieprawidłowe wparametry RGB");

            board.setCell(x, y, R, G, B, ct);

            //System.out.println(x + " " + y + " " + cellType + " " + R + " " + G + " " + B);
        }
        return board;
    }

    public static void main(String[] args) {
        Board b = null;

        try {
             b = FirstBoard.readBoard("testdata/data.1");

        }catch(IOException e){
            System.err.println(e.getLocalizedMessage());
        }catch(NumberFormatException e){
            System.err.println(e.getLocalizedMessage());
        }

        if(b == null){
            System.out.println("Mamy nulla");
        }
    }
}
