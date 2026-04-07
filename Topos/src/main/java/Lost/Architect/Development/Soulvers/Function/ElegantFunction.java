package Lost.Architect.Development.Soulvers.Function;

import Lost.Architect.Development.Annotation.Invariant;
import Lost.Architect.Development.Enum.TrigonometricOperator;
import Lost.Architect.Development.Enum.TypeAngle;

@Invariant("Интерфейс абстрактного мат. движка с минимумом для любого численного метода.")
public interface ElegantFunction {

    @Invariant("Сложение/Вычитание с возведением в степень.")
    public double calculation(double x, double a, double n);

    @Invariant("Умножение/деление с возведением в степень.")
    public double merge(double k, double x, double n);

    @Invariant("Расчет тригонометрических функции.")
    public double trigonometric(double x, TrigonometricOperator op, TypeAngle type);

    @Invariant("Извлечение значения из тригонометрической функций.")
    public double extract(double x, TrigonometricOperator op, TypeAngle type);

}
