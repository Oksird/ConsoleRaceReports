package ua.foxminded.muzychenko.formulaone.provider;

import ua.foxminded.muzychenko.formulaone.domain.Race;
import ua.foxminded.muzychenko.formulaone.domain.RacingDriver;
import ua.foxminded.muzychenko.formulaone.domain.SortedRaceResult;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class SortedRaceProviderImpl implements SortedRaceProvider {
    @Override
    public SortedRaceResult sortRacingDrivers(Race race) {
        return new SortedRaceResult(new LinkedList<>((race.racingDrivers().stream()
            .sorted(Comparator.comparing(RacingDriver::getBestTime))
            .collect(Collectors.toCollection(ArrayList<RacingDriver>::new)))));
    }
}
