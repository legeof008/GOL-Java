package main.java;

public class Util
{
    /**
     * @return wartość bitu na pozycji <code>index</code>
     */
    public static int getBit(int value, int index)
    {
        if(index >= Integer.BYTES * 8 || index < 0)
            throw new IllegalArgumentException(String.format("index (%d) musi należeć do przedziału <0, %d)", index, Integer.BYTES));

        return (value >> index) & 1;
    }

    /**
     * Ogranicza wartość <code>value</code> do przedziału <<code>minValue</code>, <code>maxValue</code>)
     * @param value wartość do ograniczenia
     * @param minValue dolna granica przedziału (włączna)
     * @param maxValue górna granica przedziału (wyłączna)
     * @return ograniczona wartość
     */
    public static int clamp(int value, int minValue, int maxValue)
    {
        if(minValue >= maxValue)
            throw new IllegalArgumentException(String.format("minValue (%d) musi być mniejsza niż maxValue (%d)", minValue, maxValue));

        if(value < minValue)
            return minValue;
        if(value >= maxValue)
            return maxValue - 1;
        return value;
    }

    /**
     * Zawija wartość <code>value</code> w przedziale <<code>minValue</code>, <code>maxValue</code>)
     * @param value wartość do zawinięcia
     * @param minValue dolna granica przedziału (włączna)
     * @param maxValue górna granica przedziału (wyłączna)
     * @return zawinięta wartość
     */
    public static int wrap(int value, int minValue, int maxValue)
    {
        if(minValue >= maxValue)
            throw new IllegalArgumentException(String.format("minValue (%d) musi być mniejsza niż maxValue (%d)", minValue, maxValue));

        if(value < minValue)
            return maxValue - (minValue - value);
        if(value >= maxValue)
            return minValue + (value - maxValue);
        return value;
    }

    public static Cell[][] copy2dArrayOfCells(Cell[][] array)
    {
        if(array == null || array.length < 1 || array[0].length < 1)
            throw new IllegalArgumentException("podana tablica nie posiada poprawnych wymiarów");

        Cell[][] newArray = new Cell[array.length][array[0].length];

        for(int j = 0; j < array.length; j++)
            for(int i = 0; i < array[0].length; i++)
            {
                newArray[j][i] = new Cell(array[j][i]);
            }

        return newArray;
    }
}
