package Lost.Architect.Development.Data;

import Lost.Architect.Development.Annotation.ArchitectSolution;
import Lost.Architect.Development.Annotation.Invariant;

@Invariant("Класс, что хранит в себе параметры градиента для картинки.")
public class DataPixelContainer {

    @Invariant("Хранит градиент красного цвета.")
    private final double red;

    @Invariant("Хранит градиент зеленого цвета.")
    private final double green;

    @Invariant("Хранит градиент голубого цвета.")
    private final double blue;

    @Invariant("Конструктор, что принимает на вход градиенты.")
    @ArchitectSolution("Конструктор один, принимает на вход только градиенты.")
    public DataPixelContainer(double red, double green, double blue){
        this.red = red;
        this.blue = blue;
        this.green = green;
    }

    @Invariant("Возвращает копию значения красного компонента градиента.")
    public double copyRed(){
        return this.red;
    }

    @Invariant("Возвращает копию значения зеленого компонента градиента.")
    public double copyGreen(){
        return this.green;
    }

    @Invariant("Возвращает копию значения голубого компонента градиента.")
    public double copyBlue(){
        return this.blue;
    }

}
