package Lost.Architect.Development.Soulvers.Metrics;

import Lost.Architect.Development.Annotation.Invariant;
import Lost.Architect.Development.Annotation.Parameters;
import Lost.Architect.Development.Soulvers.Function.ElegantFunction;

import java.util.List;

@Invariant("Рассчитываем гипотенузу зная катеты")
public class MetricHypotenuse implements ElegantMetric<Double,List<Double>> {
    @Override
    @Invariant("Расчет гипотенузы через катеты")
    @Parameters({"parameters.get(0) - это х",
                 "parameters.get(1) - это y",
                 "результат l = √(x² + y²)"})
    public Double solvedMetric(ElegantFunction el, List<Double> parameters) {
        return el.calculation(el.calculation(parameters.get(0),0,2),  // x²
                              el.calculation(parameters.get(1),0,2 ), // y²
                              0.5); // √
    }
}
