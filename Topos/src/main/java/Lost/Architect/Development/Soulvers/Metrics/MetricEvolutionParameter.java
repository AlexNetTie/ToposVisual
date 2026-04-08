package Lost.Architect.Development.Soulvers.Metrics;

import Lost.Architect.Development.Annotation.Invariant;
import Lost.Architect.Development.Annotation.Parameters;
import Lost.Architect.Development.Soulvers.Function.ElegantFunction;

import java.util.List;

@Invariant("Метрика для пересчета параметра.")
public final class MetricEvolutionParameter implements ElegantMetric<Double, List<Double>>{

    @Override
    @Invariant("Пересчет параметра.")
    @Parameters({"parameters.get(0) - старое значение параметра (значение на прошлой итерации)",
                 "parameters.get(1) - коэффициент",
                 "parameters.get(2) - величина дивергенции"})
    public Double solvedMetric(ElegantFunction el, List<Double> parameters) {
        return el.calculation(parameters.get(0), // old_value
                              el.merge(parameters.get(1),parameters.get(2),1), // alfa * div
                              1);
    }
}
