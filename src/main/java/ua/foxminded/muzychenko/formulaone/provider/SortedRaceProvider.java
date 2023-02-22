package ua.foxminded.muzychenko.formulaone.provider;

import ua.foxminded.muzychenko.formulaone.domain.Race;
import ua.foxminded.muzychenko.formulaone.domain.SortedRaceResult;

public interface SortedRaceProvider {
    SortedRaceResult sortRacingDrivers(Race race);
}
