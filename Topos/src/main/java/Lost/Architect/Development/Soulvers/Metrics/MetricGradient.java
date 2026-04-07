package Lost.Architect.Development.Soulvers.Metrics;

import Lost.Architect.Development.Annotation.Invariant;
import Lost.Architect.Development.Annotation.Parameters;
import Lost.Architect.Development.Soulvers.Function.ElegantFunction;

import java.util.List;

@Invariant("Метрика для расчета градиента")
public final class MetricGradient implements ElegantMetric<Double,List<Double>> {

    @Override
    @Invariant("Расчет градиента ∂φ/∂l")
    @Parameters({"parameters.get(0) - current value",
                 "parameters.get(1) - next value",
                 "parameters.get(2) - ∂l"})
    public Double solvedMetric(ElegantFunction el, List<Double> parameters) {
        return el.merge(
                el.calculation(parameters.get(1), parameters.get(0)*(-1),1), //φ[1] - φ[0]
                el.merge(1, parameters.get(2),-1), // 1/∂l
                1
        );
    }
}
