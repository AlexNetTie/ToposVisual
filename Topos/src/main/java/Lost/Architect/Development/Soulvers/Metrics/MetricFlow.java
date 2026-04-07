package Lost.Architect.Development.Soulvers.Metrics;

import Lost.Architect.Development.Annotation.Invariant;
import Lost.Architect.Development.Annotation.Parameters;
import Lost.Architect.Development.Soulvers.Function.ElegantFunction;

import java.util.List;

@Invariant("Метрика для расчета потока параметра")
public final class MetricFlow implements ElegantMetric<Double, List<Double>>{

    @Override
    @Invariant("Метрика рассчитывает поток параметра")
    @Parameters({"parameters.get(0) - коэффициент перед градиентом",
                 "parameters.get(1) - значение самого градиента"})
    public Double solvedMetric(ElegantFunction el, List<Double> parameters) {
        return el.merge(parameters.get(0),parameters.get(1),1);
    }
}
