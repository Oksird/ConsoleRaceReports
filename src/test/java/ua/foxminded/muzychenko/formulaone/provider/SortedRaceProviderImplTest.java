package ua.foxminded.muzychenko.formulaone.provider;

import org.junit.jupiter.api.Test;
import ua.foxminded.muzychenko.formulaone.domain.Race;
import ua.foxminded.muzychenko.formulaone.domain.RacingDriver;
import ua.foxminded.muzychenko.formulaone.domain.SortedRaceResult;
import ua.foxminded.muzychenko.formulaone.parser.ParserImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SortedRaceProviderImplTest {

    private final SortedRaceProvider sortedRaceProvider = new SortedRaceProviderImpl();

    @Test
    void sortRacingDrivers_shouldReturnSortedRaceResultThatContainsLinkedListWithSortedByBestTimeRacersWhenSortedRaceResultIsCorrect() {

        RacingDriver firstDriver = RacingDriver.builder()
            .withStartTime(LocalDateTime.from(ParserImpl.FORMATTER.parse("2018-05-24_12:01:00.000")))
            .withEndTime(LocalDateTime.from(ParserImpl.FORMATTER.parse("2018-05-24_12:02:00.000"))).build();
        RacingDriver secondDriver = RacingDriver.builder()
            .withStartTime(LocalDateTime.from(ParserImpl.FORMATTER.parse("2018-05-24_12:01:00.000")))
            .withEndTime(LocalDateTime.from(ParserImpl.FORMATTER.parse("2018-05-24_12:01:30.000"))).build();

        ArrayList<RacingDriver> racingDrivers = new ArrayList<>();
        racingDrivers.add(firstDriver);
        racingDrivers.add(secondDriver);

        LinkedList<RacingDriver> sortedDrivers = new LinkedList<>();
        sortedDrivers.add(secondDriver);
        sortedDrivers.add(firstDriver);

        SortedRaceResult expectedResult = new SortedRaceResult(sortedDrivers);

        assertEquals(expectedResult, sortedRaceProvider.sortRacingDrivers(new Race(racingDrivers)));
    }

    @Test
    void sortRacingDrivers_shouldThrowNullPointerExceptionWhenStartTimeOrEndTimeIsNull() {
        assertThrows(
            NullPointerException.class, () -> sortedRaceProvider.sortRacingDrivers(new Race(
                new ArrayList<>(Arrays.asList(RacingDriver.builder().build(), RacingDriver.builder().build()))))
        );
    }
}
