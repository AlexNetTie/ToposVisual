package Lost.Architect.Development.Soulvers.Metrics;

import Lost.Architect.Development.Annotation.Invariant;
import Lost.Architect.Development.Annotation.Parameters;
import Lost.Architect.Development.Soulvers.Engine.ElegantEngine;

import java.util.List;

@Invariant("Метрика для расчета потока параметра")
public final class MetricFlow implements ElegantMetric<Double, List<Double>>{

    @Override
    @Invariant("Метрика рассчитывает поток параметра")
    @Parameters({"parameters.get(0) - коэффициент перед градиентом",
                 "parameters.get(1) - значение самого градиента"})
    public Double solvedMetric(ElegantEngine en, List<Double> parameters) {
        return en.merge(parameters.get(0),parameters.get(1),1);
    }
}
