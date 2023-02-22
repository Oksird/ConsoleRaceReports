package ua.foxminded.muzychenko.formulaone.provider;

import ua.foxminded.muzychenko.formulaone.domain.Race;
import ua.foxminded.muzychenko.formulaone.domain.RacingDriver;
import ua.foxminded.muzychenko.formulaone.domain.SortedRaceResult;

import java.text.SimpleDateFormat;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

public class StatisticsProviderImpl implements StatisticsProvider {
    public static final String TIME_PATTERN_FORMAT = "mm:ss:SSS";
    public final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TIME_PATTERN_FORMAT);

    @Override
    public String provideStatistics(SortedRaceResult sortedRaceResult) {

        if (sortedRaceResult == null || sortedRaceResult.racingDrivers().isEmpty()){
            return "Sorted race result is null or empty";
        }

        LinkedList<RacingDriver> racingDrivers = sortedRaceResult.racingDrivers();
        StringBuilder stringBuilder = new StringBuilder();

        AtomicInteger racingDriverPosition = new AtomicInteger();

        int maximumNameLength = racingDrivers.stream().max(Comparator.comparingInt(
            racingDriver -> racingDriver.getName().length())).orElseThrow().getName().length();

        int maximumTeamNameLength = racingDrivers.stream().max(Comparator.comparingInt(
            racingDriver -> racingDriver.getTeam().length())).orElseThrow().getTeam().length();

        racingDrivers.forEach(racingDriver -> {

            if (racingDriverPosition.get() == Race.COUNT_OF_DRIVERS_PASSED_FIRST_STAGE) {
                stringBuilder
                    .append("—".repeat(stringBuilder.substring(stringBuilder.lastIndexOf("."),
                        stringBuilder.length()).length() + 1))
                    .append("\n");
            }

            stringBuilder
                .append(String.format("%d. ", racingDriverPosition.incrementAndGet()))
                .append(String.format("%%%ds| ".formatted(-1 * maximumNameLength), racingDriver.getName()))
                .append(String.format("%%%ds | ".formatted(-1 * maximumTeamNameLength), racingDriver.getTeam()))
                .append(simpleDateFormat.format(racingDriver.getBestTime().toMillis()))
                .append("\n");

        });

        return stringBuilder.toString();
    }
}
