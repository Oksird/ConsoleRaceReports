package ua.foxminded.muzychenko.formulaone.provider;

import org.junit.jupiter.api.Test;
import ua.foxminded.muzychenko.formulaone.domain.RacingDriver;
import ua.foxminded.muzychenko.formulaone.domain.SortedRaceResult;

import java.time.LocalDateTime;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;
import static ua.foxminded.muzychenko.formulaone.parser.ParserImpl.FORMATTER;

class StatisticsProviderImplTest {

    private final StatisticsProvider statisticsProvider = new StatisticsProviderImpl();

    @Test
    void provideStatistics_shouldReturnStringWithTableOfFirstStageResultWhenSortedRaceResultContainsRacers() {

        LinkedList<RacingDriver> sortedDrivers = new LinkedList<>();

        sortedDrivers.add(RacingDriver.builder()
            .withStartTime(LocalDateTime.from(FORMATTER.parse("2018-05-24_12:01:00.000")))
            .withEndTime(LocalDateTime.from(FORMATTER.parse("2018-05-24_12:02:00.000")))
            .withName("Alex David")
            .withTeam("Mercedes")
            .build());
        sortedDrivers.add(RacingDriver.builder()
            .withStartTime(LocalDateTime.from(FORMATTER.parse("2018-05-24_12:01:00.000")))
            .withEndTime(LocalDateTime.from(FORMATTER.parse("2018-05-24_12:03:00.000")))
            .withName("Oliver Quin")
            .withTeam("Arrow")
            .build());

        SortedRaceResult sortedRaceResult = new SortedRaceResult(sortedDrivers);

        String expectedResult = """
            1. Alex David | Mercedes | 01:00:000
            2. Oliver Quin| Arrow    | 02:00:000
            """;
        String actualResult = statisticsProvider.provideStatistics(sortedRaceResult);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void provideStatistics_shouldReturnCorrectInfoAboutSortedRaceWhenSortedRaceResultIsEmpty() {

        SortedRaceResult sortedRaceResult = new SortedRaceResult(new LinkedList<>());

        String expectedResult = "Sorted race result is null or empty";
        String actualResult = statisticsProvider.provideStatistics(sortedRaceResult);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void provideStatistics_shouldReturnCorrectInfoAboutSortedRaceWhenSortedRaceResultIsNull() {
        String expectedResult = "Sorted race result is null or empty";
        String actualResult = statisticsProvider.provideStatistics(null);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void provideStatistics_shouldReturnInfoAboutRaceWithUnderscoreWhenDriversAreMoreThenFifteen() {
        LinkedList<RacingDriver> sortedDrivers = new LinkedList<>();

        for (int i = 0; i < 17; i++) {
            sortedDrivers.add(RacingDriver.builder()
                .withStartTime(LocalDateTime.from(FORMATTER.parse("2018-05-24_12:01:00.000")))
                .withEndTime(LocalDateTime.from(FORMATTER.parse("2018-05-24_12:02:00.000")))
                .withName("Alex David")
                .withTeam("Mercedes")
                .build());
        }

        SortedRaceResult sortedRaceResult = new SortedRaceResult(sortedDrivers);

        String expectedResult = """
            1. Alex David| Mercedes | 01:00:000
            2. Alex David| Mercedes | 01:00:000
            3. Alex David| Mercedes | 01:00:000
            4. Alex David| Mercedes | 01:00:000
            5. Alex David| Mercedes | 01:00:000
            6. Alex David| Mercedes | 01:00:000
            7. Alex David| Mercedes | 01:00:000
            8. Alex David| Mercedes | 01:00:000
            9. Alex David| Mercedes | 01:00:000
            10. Alex David| Mercedes | 01:00:000
            11. Alex David| Mercedes | 01:00:000
            12. Alex David| Mercedes | 01:00:000
            13. Alex David| Mercedes | 01:00:000
            14. Alex David| Mercedes | 01:00:000
            15. Alex David| Mercedes | 01:00:000
            ————————————————————————————————————
            16. Alex David| Mercedes | 01:00:000
            17. Alex David| Mercedes | 01:00:000
            """;
        String actualResult = statisticsProvider.provideStatistics(sortedRaceResult);

        assertEquals(expectedResult, actualResult);
    }
}
