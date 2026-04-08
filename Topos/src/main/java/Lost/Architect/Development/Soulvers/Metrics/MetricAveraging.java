package Lost.Architect.Development.Soulvers.Metrics;

import Lost.Architect.Development.Annotation.Invariant;
import Lost.Architect.Development.Annotation.Parameters;
import Lost.Architect.Development.Soulvers.Function.ElegantFunction;

import java.util.List;

@Invariant("Метрика для расчета среднего значения")
public final class MetricAveraging implements ElegantMetric<Double, List<Double>> {

    @Override
    @Invariant("Расчет среднего значения")
    @Parameters("List<Double> parameters - список значений для которых надо рассчитать среднее")
    public Double solvedMetric(ElegantFunction el, List<Double> parameters) {
        double sum = 0;
        for(Double value : parameters){
            sum = el.calculation(sum,value,1);
        }
        sum = el.merge(sum, el.merge(1,parameters.size(),-1), 1);
        return sum;
    }
}
