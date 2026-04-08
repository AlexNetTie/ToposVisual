package Lost.Architect.Development.Soulvers.Function;

import Lost.Architect.Development.Annotation.Invariant;
import Lost.Architect.Development.Enum.TrigonometricOperator;
import Lost.Architect.Development.Enum.TypeAngle;

@Invariant("Имплементация мат. движка")
public final class ElegantFractalFunction implements ElegantFunction{
    @Override
    @Invariant("Для расчет сложения/вычитания с возведением в степень")
    public double calculation(double x, double a, double n) {
        if (n == 0.0) {
            return 1.0;
        }
        if (n == 1.0) {
            return (x + a);
        }
        if (n == 2.0) {
            return (x + a) * (x + a);
        }
        return Math.pow((x + a), n);
    }

    @Override
    @Invariant("Для расчета умножения/деления с коэффициентом пропорциональности")
    public double merge(double k, double x, double n) {
        if (n == -1.0) {
            return 1 / k * x;
        }
        if (n == 0.0) {
            return 0.0;
        }
        if (n == 1.0) {
            return k * x;
        }
        return Math.pow(k * x, n);
    }

    @Override
    @Invariant("Для расчета тригонометрических функций, type = угол в градусах или радианах который передаем")
    public double trigonometric(double x, TrigonometricOperator op, TypeAngle type) {
        double angle = x;
        if(type == TypeAngle.degrees){
            angle = Math.toRadians(angle);
        }
        double result;
        if(op == TrigonometricOperator.cosφ){
            result = Math.cos(angle);
        } else if (op == TrigonometricOperator.sinφ) {
            result = Math.sin(angle);
        } else {
            result = Math.tan(angle);
        }
        return result;
    }

    @Override
    @Invariant("Для извлечения угла, type - тип угла в градусах или радианах")
    public double extract(double x, TrigonometricOperator op, TypeAngle type) {
        double angle;
        if(op == TrigonometricOperator.cosφ) {
            angle = Math.acos(x);
        } else if(op == TrigonometricOperator.sinφ) {
            angle = Math.asin(x);
        } else {
            angle = Math.atan(x);
        }
        if(type == TypeAngle.degrees){
            angle = Math.toDegrees(angle);
        }
        return angle;
    }
}
