package ua.foxminded.muzychenko.formulaone.parser;

import ua.foxminded.muzychenko.formulaone.domain.Race;
import ua.foxminded.muzychenko.formulaone.domain.RacingDriver;
import ua.foxminded.muzychenko.formulaone.exception.DamagedFileException;
import ua.foxminded.muzychenko.formulaone.exception.WrongFileNameException;
import ua.foxminded.muzychenko.formulaone.exception.WrongPathException;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ParserImpl implements Parser {

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss.SSS")
        .withZone(ZoneId.systemDefault());

    @Override
    public Race parse(String abbreviationsFileName,
                      String startTimeStatisticsFileName,
                      String endTimeStatisticsFileName)
        throws DamagedFileException, WrongFileNameException, WrongPathException {

        ArrayList<RacingDriver> drivers = new ArrayList<>();
        Map<String, LocalDateTime> racersStartTime = new HashMap<>();
        Map<String, LocalDateTime> racersEndTime = new HashMap<>();

        List<String> allLinesOfAbbreviations;
        List<String> allLinesOfStart;
        List<String> allLinesOfEnd;

        URL abbreviationsFileURL = getClass().getClassLoader().getResource(abbreviationsFileName);
        URL startFileURL = getClass().getClassLoader().getResource(startTimeStatisticsFileName);
        URL endFileURL = getClass().getClassLoader().getResource(endTimeStatisticsFileName);

        try {
            allLinesOfAbbreviations = Files.readAllLines(new File(Objects.requireNonNull(abbreviationsFileURL).toURI()).toPath());
            allLinesOfStart = Files.readAllLines(new File(Objects.requireNonNull(startFileURL).toURI()).toPath());
            allLinesOfEnd = Files.readAllLines(new File(Objects.requireNonNull(endFileURL).toURI()).toPath());

        } catch (IOException ioException) {
            throw new DamagedFileException(ioException.getMessage());
        } catch (NullPointerException exception) {
            throw new WrongFileNameException(exception.getMessage());
        } catch (URISyntaxException uriSyntaxException) {
            throw new WrongPathException(uriSyntaxException.getInput(), uriSyntaxException.getReason());
        }

        allLinesOfStart.forEach(
            line -> racersStartTime.put(line.substring(0, 3), formatDate(line.substring(3))));

        allLinesOfEnd.forEach(
            line -> racersEndTime.put(line.substring(0, 3), formatDate(line.substring(3))));

        allLinesOfAbbreviations.forEach(line -> {
            String[] separatedValues = line.split("_");
            drivers.add(RacingDriver.builder()
                .withAbbreviation(separatedValues[0])
                .withName(separatedValues[1])
                .withTeam(separatedValues[2])
                .withStartTime(racersStartTime.get(separatedValues[0]))
                .withEndTime(racersEndTime.get(separatedValues[0]))
                .build());
        });

        return new Race(drivers);
    }

    private LocalDateTime formatDate(String date) {
        return LocalDateTime.from(FORMATTER.parse(date));
    }
}
