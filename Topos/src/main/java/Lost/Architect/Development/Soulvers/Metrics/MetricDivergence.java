package Lost.Architect.Development.Soulvers.Metrics;

import Lost.Architect.Development.Annotation.Invariant;
import Lost.Architect.Development.Annotation.Parameters;
import Lost.Architect.Development.Soulvers.Function.ElegantFunction;

import java.util.List;

@Invariant("Метрика для расчета дивергенции потока.")
public final class MetricDivergence implements ElegantMetric<Double, List<Double>> {

    @Override
    @Invariant("Расчет дивергенции потока.")
    @Parameters({"parameters.get(0) - поток втекающий",
                 "parameters.get(1) - поток вытекающий",
                 "parameters.get(2) - интервал на котором рассеивается поток"})
    public Double solvedMetric(ElegantFunction el, List<Double> parameters) {
        return el.merge(el.calculation(parameters.get(0),parameters.get(1)*(-1),1), // flow_in - flow_out
                        el.merge(1, parameters.get(2), -1), // 1/delta
                        1);
    }
}
