package Lost.Architect.Development.Soulvers.Metrics;

import Lost.Architect.Development.Annotation.Invariant;
import Lost.Architect.Development.Annotation.Parameters;
import Lost.Architect.Development.Soulvers.Engine.ElegantEngine;

import java.util.List;

@Invariant("Метрика для расчета среднего значения")
public final class MetricAveraging implements ElegantMetric<Double, List<Double>> {

    @Override
    @Invariant("Расчет среднего значения")
    @Parameters("List<Double> parameters - список значений для которых надо рассчитать среднее")
    public Double solvedMetric(ElegantEngine en, List<Double> parameters) {
        double sum = 0;
        for(Double value : parameters){
            sum = en.calculation(sum,value,1);
        }
        sum = en.merge(sum, en.merge(1,parameters.size(),-1), 1);
        return sum;
    }
}
