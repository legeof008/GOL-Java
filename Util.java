public class Util
{
    /**
     * @return wartość bitu na pozycji <code>index</code>
     */
    public static int getBit(int value, int index)
    {
        if(index >= Integer.BYTES || index < 0)
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
            return maxValue - (minValue - 1 - value);
        if(value >= maxValue)
            return minValue + (value - maxValue);
        return value;
    }
}
