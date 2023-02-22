package ua.foxminded.muzychenko.formulaone.domain;

import java.util.ArrayList;

public record Race(ArrayList<RacingDriver> racingDrivers) {
    public static final int COUNT_OF_DRIVERS_PASSED_FIRST_STAGE = 15;
}
