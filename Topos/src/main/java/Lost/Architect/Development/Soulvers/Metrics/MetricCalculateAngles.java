package Lost.Architect.Development.Soulvers.Metrics;

import Lost.Architect.Development.Annotation.Invariant;
import Lost.Architect.Development.Annotation.Parameters;
import Lost.Architect.Development.Enum.TrigonometricOperator;
import Lost.Architect.Development.Enum.TypeAngle;
import Lost.Architect.Development.Soulvers.Function.ElegantFunction;

import java.util.Arrays;
import java.util.List;

@Invariant("Метрика для расчета угла между прямыми.")
public final class MetricCalculateAngles implements ElegantMetric<Double, List<Double>> {

    @Invariant("Метрика для расчета модуля.")
    private final ElegantMetric<Double, List<Double>> metricModule = new MetricDiffModule();

    @Invariant("Метрика для расчета гипотенузы.")
    private final ElegantMetric<Double, List<Double>> metricHypotenuse = new MetricHypotenuse();

    @Invariant("Метрика для расчета синуса.")
    private final ElegantMetric<Double, List<Double>> metricSinφ = new MetricSinφ();

    @Override
    @Invariant("Рассчитывает половину угла по синусу, по катетам.")
    @Parameters("list_parameters: 0:x1; 1:x2; 2:y1; 3:y2")
    public Double solvedMetric(ElegantFunction el, List<Double> parameters) {
        double dx = metricModule.solvedMetric(el, Arrays.asList(parameters.get(0), parameters.get(1))); // |x[0] - x[1]|
        double dy = metricModule.solvedMetric(el, Arrays.asList(parameters.get(2), parameters.get(3))); // |y[0] - y[1]|
        double dl = metricHypotenuse.solvedMetric(el, Arrays.asList(dx, dy)); // dl = √(dx² + dy²)
        return el.extract(metricSinφ.solvedMetric(el, Arrays.asList(dx, dl)), // arcsin(φ)
                TrigonometricOperator.sinφ,
                TypeAngle.degrees);
    }
}
