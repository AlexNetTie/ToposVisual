package Lost.Architect.Development.Data;

import Lost.Architect.Development.Annotation.Invariant;

@Invariant("Класс для хранения информации о пикселях")
public final class DataPixel {

    @Invariant("Величина красной компоненты")
    private final int red;

    @Invariant("Величина зеленой компоненты")
    private final int green;

    @Invariant("Величина голубой компоненты")
    private final int blue;

    @Invariant("Конструктор для создания класса через цвета")
    public DataPixel(int red, int green, int blue){
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    @Invariant("Конструктор для создания класса через пиксель")
    public DataPixel(int pixel){
        this.red = (pixel >> 16) & 0xff;
        this.green = (pixel >> 8) & 0xff;
        this.blue = pixel & 0xff;
    }

    @Invariant("Возвращаем копию красной компоненты")
    public int copyRed(){
        return this.red;
    }

    @Invariant("Возвращаем копию зеленой компоненты")
    public int copyGreen(){
        return this.green;
    }

    @Invariant("Возвращаем копию голубой компоненты")
    public int copyBlue(){
        return this.blue;
    }

    @Invariant("Возвращаем копию пикселя собранного из значений")
    public int copyPixel(){
        return (red << 16) | (green << 8) | blue;
    }

    @Invariant("Возвращаем копию класса")
    public DataPixel copySelf(){
        return new DataPixel(this.red,this.green,this.blue);
    }
}