package Lost.Architect.Development.Soulvers;

import Lost.Architect.Development.Annotation.Invariant;
import Lost.Architect.Development.Data.DataObject;
import Lost.Architect.Development.Soulvers.Metrics.ElegantMetric;
import Lost.Architect.Development.Soulvers.Metrics.MetricGradient;

import java.util.List;

@Invariant("Первый прототип солвера")
public class SolverTriangleOneStep {

    private final ElegantMetric<Double,List<Double>> metricGradient = new MetricGradient();

    public List<Double> oneStep(DataObject data){
        return null;
    }

}
