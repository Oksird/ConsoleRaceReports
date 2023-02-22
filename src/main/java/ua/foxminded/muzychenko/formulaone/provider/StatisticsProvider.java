package ua.foxminded.muzychenko.formulaone.provider;

import ua.foxminded.muzychenko.formulaone.domain.SortedRaceResult;

public interface StatisticsProvider {
    String provideStatistics(SortedRaceResult sortedRaceResult);
}
