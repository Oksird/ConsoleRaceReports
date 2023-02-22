package ua.foxminded.muzychenko.formulaone;

import ua.foxminded.muzychenko.formulaone.domain.Race;
import ua.foxminded.muzychenko.formulaone.domain.SortedRaceResult;
import ua.foxminded.muzychenko.formulaone.exception.DamagedFileException;
import ua.foxminded.muzychenko.formulaone.exception.WrongFileNameException;
import ua.foxminded.muzychenko.formulaone.exception.WrongPathException;
import ua.foxminded.muzychenko.formulaone.parser.Parser;
import ua.foxminded.muzychenko.formulaone.provider.SortedRaceProvider;
import ua.foxminded.muzychenko.formulaone.provider.StatisticsProvider;

public class FormulaRacingDriversResultsProvider {
    private final Parser parser;
    private final SortedRaceProvider sortedRaceProvider;
    private final StatisticsProvider statisticsProvider;

    public FormulaRacingDriversResultsProvider(Parser parser,
                                               SortedRaceProvider sortedRaceProvider,
                                               StatisticsProvider statisticsProvider) {
        this.parser = parser;
        this.sortedRaceProvider = sortedRaceProvider;
        this.statisticsProvider = statisticsProvider;
    }

    public String getFirstStageResult(String abbreviationsFileName,
                                      String startTimeFileName,
                                      String endTimeFileName)
        throws DamagedFileException, WrongFileNameException, WrongPathException {

        Race race = parser.parse(abbreviationsFileName, startTimeFileName, endTimeFileName);
        SortedRaceResult sortedRaceResult = sortedRaceProvider.sortRacingDrivers(race);

        return statisticsProvider.provideStatistics(sortedRaceResult);
    }

}
