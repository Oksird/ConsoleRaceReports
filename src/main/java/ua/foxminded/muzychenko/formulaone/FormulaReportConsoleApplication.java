package ua.foxminded.muzychenko.formulaone;

import ua.foxminded.muzychenko.formulaone.parser.ParserImpl;
import ua.foxminded.muzychenko.formulaone.provider.SortedRaceProviderImpl;
import ua.foxminded.muzychenko.formulaone.provider.StatisticsProviderImpl;


import java.io.IOException;
import java.net.URISyntaxException;

public class FormulaReportConsoleApplication {
    public static void main(String[] args) throws IOException, URISyntaxException {

        FormulaRacingDriversResultsProvider formulaRacingDriversResultsProvider =
            new FormulaRacingDriversResultsProvider(
                new ParserImpl(),
                new SortedRaceProviderImpl(),
                new StatisticsProviderImpl()
            );

        String result = formulaRacingDriversResultsProvider.getFirstStageResult(
            "abbreviations.txt",
            "start.log",
            "end.log"
        );

        System.out.println(result);
    }
}
