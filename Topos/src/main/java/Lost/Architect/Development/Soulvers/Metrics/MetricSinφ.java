package Lost.Architect.Development.Soulvers.Metrics;

import Lost.Architect.Development.Annotation.Invariant;
import Lost.Architect.Development.Annotation.Parameters;
import Lost.Architect.Development.Soulvers.Function.ElegantFunction;

import java.util.List;

/**
 * Надо будет пересмотреть архитекстуру для численного метода,
 * как будто бы в движок вшито уже это действие
 * */
@Invariant("Метрика для расчета sinφ")
public final class MetricSinφ implements ElegantMetric<Double, List<Double>>{

    @Override
    @Invariant("Расчет величины синуса угла, как отношение противолежащего катета к гипотенузе")
    @Parameters({"parameters.get(0) - противолежащий катет",
                 "parameters.get(1) - гипотенуза"})
    public Double solvedMetric(ElegantFunction el, List<Double> parameters) {
        return el.merge(el.merge(1,parameters.get(0),1),
                el.merge(1,parameters.get(1),-1),
                1);
    }
}
