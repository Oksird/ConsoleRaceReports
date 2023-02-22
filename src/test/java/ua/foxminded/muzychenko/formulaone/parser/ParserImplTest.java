package ua.foxminded.muzychenko.formulaone.parser;

import org.junit.jupiter.api.Test;
import ua.foxminded.muzychenko.formulaone.domain.Race;
import ua.foxminded.muzychenko.formulaone.domain.RacingDriver;
import ua.foxminded.muzychenko.formulaone.exception.DamagedFileException;
import ua.foxminded.muzychenko.formulaone.exception.WrongFileNameException;
import ua.foxminded.muzychenko.formulaone.exception.WrongPathException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class ParserImplTest {

    private final Parser parser = new ParserImpl();

    @Test
    void parse_shouldReturnRaceWithCorrectInfoAboutRacersFromSpecifiedFilesWhenFilesArePresentInResourcesFolder()
        throws DamagedFileException, WrongPathException {

        String abbreviationsFileName = "abbreviations.txt";
        String startFileName = "start.log";
        String endFileName = "end.log";

        Race race = parser.parse(abbreviationsFileName, startFileName, endFileName);

        RacingDriver actualRacingDriver = race.racingDrivers().get(0);

        RacingDriver expectedRacingDriver = RacingDriver.builder()
            .withAbbreviation("DRR")
            .withTeam("RED BULL RACING TAG HEUER")
            .withName("Daniel Ricciardo")
            .withStartTime(LocalDateTime.from(ParserImpl.FORMATTER.parse("2018-05-24_12:14:12.054")))
            .withEndTime((LocalDateTime.from(ParserImpl.FORMATTER.parse("2018-05-24_12:15:24.067"))))
            .build();

        assertEquals(expectedRacingDriver, actualRacingDriver);
    }

    @Test
    void parse_shouldThrowWrongPathsExceptionWhenFileNameIsIncorrect() {
        assertThrows(
            WrongFileNameException.class, () -> parser.parse(
                "sdf", "fds", "hdfgfd"));
    }
}
