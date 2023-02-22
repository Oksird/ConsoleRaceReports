package ua.foxminded.muzychenko.formulaone.domain;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class RacingDriver {

    private final String abbreviation;
    private final String name;
    private final String team;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    private RacingDriver(Builder builder) {
        this.abbreviation = builder.abbreviation;
        this.name = builder.name;
        this.team = builder.team;
        this.startTime = builder.startTime;
        this.endTime = builder.endTime;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public String getName() {
        return name;
    }

    public String getTeam() {
        return team;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public Duration getBestTime() {
        return Duration.between(startTime, endTime);

    }

    @Override
    public String toString() {
        return "RacingDriver{" +
            "abbreviation='" + abbreviation + '\'' +
            ", name='" + name + '\'' +
            ", team='" + team + '\'' +
            ", startTime=" + startTime +
            ", endTime=" + endTime +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RacingDriver that = (RacingDriver) o;

        return Objects.equals(name, that.name) &&
            Objects.equals(team, that.team) &&
            Objects.equals(startTime, that.startTime) &&
            Objects.equals(endTime, that.endTime) &&
            Objects.equals(abbreviation, that.abbreviation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(abbreviation, name, team, startTime, endTime);
    }

    public static class Builder {

        private Builder() {

        }

        private String abbreviation;
        private String name;
        private String team;
        private LocalDateTime startTime;
        private LocalDateTime endTime;

        public Builder withAbbreviation(String abbreviation) {
            this.abbreviation = abbreviation;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withTeam(String team) {
            this.team = team;
            return this;
        }

        public Builder withStartTime(LocalDateTime startTime) {
            this.startTime = startTime;
            return this;
        }

        public Builder withEndTime(LocalDateTime endTime) {
            this.endTime = endTime;
            return this;
        }

        public RacingDriver build() {
            if (startTime == null || endTime == null) {
                throw new NullPointerException("startTime or endTime is null");
            }
            return new RacingDriver(this);
        }
    }
}
