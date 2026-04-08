package Lost.Architect.Development.Soulvers.Metrics;

import Lost.Architect.Development.Annotation.Invariant;
import Lost.Architect.Development.Annotation.Parameters;
import Lost.Architect.Development.Soulvers.Engine.ElegantEngine;

import java.util.List;

@Invariant("Расчет модуля разницы величин")
public final class MetricDiffModule implements ElegantMetric<Double, List<Double>> {

    @Override
    @Invariant("Для модуля мы возводим в квадрат и берем корень")
    @Parameters({"parameters.get(0) - x[0]",
                 "parameters.get(1) - x[1]"})
    public Double solvedMetric(ElegantEngine en, List<Double> parameters) {
        return en.merge(
                1,
                   en.calculation(parameters.get(0),
                           parameters.get(1) * -1,
                           2),
                0.5
        );
    }
}
