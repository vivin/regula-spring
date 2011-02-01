package net.vivin.starship.domain;

import net.vivin.validation.ValidateClientSide;
import net.vivin.starship.validation.annotation.IsNumeric;
import net.vivin.starship.validation.annotation.RegistryNumber;
import net.vivin.starship.validation.annotation.Stardate;

import net.vivin.starship.validation.group.StarshipGroup;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@ValidateClientSide
public class Starship {

    @NotBlank(groups={StarshipGroup.class})
    private String shipName;

    @NotBlank(groups={StarshipGroup.class})
    @RegistryNumber(groups={StarshipGroup.class})
    private String registry;

    @NotBlank(groups={StarshipGroup.class})
    private String model;

    @NotBlank(groups={StarshipGroup.class})
    @Stardate(groups={StarshipGroup.class})
    private String launchDate;

    @NotBlank(groups={StarshipGroup.class})
    @IsNumeric(groups={StarshipGroup.class})
    @Min(value=1, groups={StarshipGroup.class})
    private double maximumWarp;

    @NotBlank(groups={StarshipGroup.class})
    @IsNumeric(groups={StarshipGroup.class})
    @Min(value=1, groups={StarshipGroup.class})
    private double shieldFrequency;

    @NotBlank(groups={StarshipGroup.class})
    @IsNumeric(groups={StarshipGroup.class})
    @Min(value=12, groups={StarshipGroup.class})
    private int crew;

    @Valid
    private Crewmember captain;

    public Starship() {
        super();
        captain = new Crewmember();
        captain.setRank("Captain [O-6]");
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public String getRegistry() {
        return registry;
    }

    public void setRegistry(String registry) {
        this.registry = registry;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(String launchDate) {
        this.launchDate = launchDate;
    }

    public double getMaximumWarp() {
        return maximumWarp;
    }

    public void setMaximumWarp(double maximumWarp) {
        this.maximumWarp = maximumWarp;
    }

    public double getShieldFrequency() {
        return shieldFrequency;
    }

    public void setShieldFrequency(double shieldFrequency) {
        this.shieldFrequency = shieldFrequency;
    }

    public int getCrew() {
        return crew;
    }

    public void setCrew(int crew) {
        this.crew = crew;
    }

    public Crewmember getCaptain() {
        return captain;
    }

    public void setCaptain(Crewmember captain) {
        this.captain = captain;
    }
}
