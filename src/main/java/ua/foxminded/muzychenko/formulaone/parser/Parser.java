package ua.foxminded.muzychenko.formulaone.parser;

import ua.foxminded.muzychenko.formulaone.domain.Race;
import ua.foxminded.muzychenko.formulaone.exception.DamagedFileException;
import ua.foxminded.muzychenko.formulaone.exception.WrongFileNameException;
import ua.foxminded.muzychenko.formulaone.exception.WrongPathException;

public interface Parser {
    Race parse(String abbreviationsFileName, String startTimeStatisticsFileName,
               String endTimeStatisticsFileName) throws DamagedFileException, WrongFileNameException, WrongPathException;
}
