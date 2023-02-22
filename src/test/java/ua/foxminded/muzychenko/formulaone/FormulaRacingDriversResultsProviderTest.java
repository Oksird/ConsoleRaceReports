package ua.foxminded.muzychenko.formulaone;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.foxminded.muzychenko.formulaone.domain.Race;
import ua.foxminded.muzychenko.formulaone.domain.RacingDriver;
import ua.foxminded.muzychenko.formulaone.domain.SortedRaceResult;
import ua.foxminded.muzychenko.formulaone.exception.DamagedFileException;
import ua.foxminded.muzychenko.formulaone.exception.WrongFileNameException;
import ua.foxminded.muzychenko.formulaone.exception.WrongPathException;
import ua.foxminded.muzychenko.formulaone.parser.Parser;
import ua.foxminded.muzychenko.formulaone.provider.SortedRaceProvider;
import ua.foxminded.muzychenko.formulaone.provider.StatisticsProvider;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static ua.foxminded.muzychenko.formulaone.parser.ParserImpl.FORMATTER;


@ExtendWith(MockitoExtension.class)
class FormulaRacingDriversResultsProviderTest {

    @InjectMocks
    private FormulaRacingDriversResultsProvider formulaRacingDriversResultsProvider;
    @Mock
    private Parser parser;
    @Mock
    private StatisticsProvider statisticsProvider;
    @Mock
    SortedRaceProvider sortedRaceProvider;

    @Test
    void getFirstStageResult_shouldReturnResultsTableOfFirstStageWhenInformationOfRacersIsPresent()
        throws DamagedFileException, WrongPathException {

        String abbreviationFileName = "abbreviations.txt";
        String startFileName = "start.log";
        String endFileName = "end.log";
        String expectedResult = """
            1. Alex David | Mercedes | 01:00:000
            2. Oliver Quin| Arrow    | 02:00:000
            """;

        ArrayList<RacingDriver> racingDrivers = new ArrayList<>();

        racingDrivers.add(RacingDriver.builder()
            .withStartTime(LocalDateTime.from(FORMATTER.parse("2018-05-24_12:01:00.000")))
            .withEndTime(LocalDateTime.from(FORMATTER.parse("2018-05-24_12:03:00.000")))
            .withName("Oliver Quin")
            .withTeam("Arrow")
            .build());

        racingDrivers.add(RacingDriver.builder()
            .withStartTime(LocalDateTime.from(FORMATTER.parse("2018-05-24_12:01:00.000")))
            .withEndTime(LocalDateTime.from(FORMATTER.parse("2018-05-24_12:02:00.000")))
            .withName("Alex David")
            .withTeam("Mercedes")
            .build());

        given(parser.parse(abbreviationFileName, startFileName, endFileName)).willReturn(new Race(racingDrivers));

        Race race = parser.parse(abbreviationFileName, startFileName, endFileName);

        LinkedList<RacingDriver> sortedRacingDrivers = new LinkedList<>();
        sortedRacingDrivers.add(racingDrivers.get(1));
        sortedRacingDrivers.add(racingDrivers.get(0));

        given(sortedRaceProvider.sortRacingDrivers(race))
            .willReturn(new SortedRaceResult(sortedRacingDrivers));

        SortedRaceResult sortedRaceResult = sortedRaceProvider.sortRacingDrivers(race);

        given(statisticsProvider.provideStatistics(sortedRaceResult)).willReturn(expectedResult);

        String actualResult = formulaRacingDriversResultsProvider.getFirstStageResult(
            abbreviationFileName,
            startFileName,
            endFileName);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getFirstStageResult_shouldThrowWrongFileNameExceptionWhenFileNameIsIncorrect()
        throws DamagedFileException, WrongPathException {

        String wrongFileName = "Wrong file name";

        doThrow(new WrongFileNameException("Filename is wrong"))
            .when(parser)
            .parse(wrongFileName, wrongFileName, wrongFileName);

        assertThrows(
            WrongFileNameException.class, () -> formulaRacingDriversResultsProvider.getFirstStageResult(
                wrongFileName, wrongFileName, wrongFileName));
    }
}
