package Lost.Architect.Development.Soulvers.Metrics;

import Lost.Architect.Development.Annotation.Invariant;
import Lost.Architect.Development.Soulvers.Engine.ElegantEngine;

@Invariant("Универсальный интерфейс для рассчета параметра по метрике")
public interface ElegantMetric<R,I> {

    @Invariant("Рассчет параметра")
    public R solvedMetric(ElegantEngine el, I parameters);
}
