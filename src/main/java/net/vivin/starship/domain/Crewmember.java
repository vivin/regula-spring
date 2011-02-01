package net.vivin.starship.domain;

import net.vivin.starship.validation.group.CrewmemberGroup;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

public class Crewmember {

    @NotBlank(groups={CrewmemberGroup.class})
    private String name;

    @NotBlank(groups={CrewmemberGroup.class})
    private String rank;

    @NotBlank(message="Holaz wazzup \"", groups={CrewmemberGroup.class})
    @Min(value=25, groups={CrewmemberGroup.class})
    private int age;

    @Pattern(regexp="/[A-Z0-9]{2}-[A-Z0-9]{5}/", flags={Pattern.Flag.CASE_INSENSITIVE}, groups={CrewmemberGroup.class})
    private String serviceNumber;

    @Valid
    Address address;

    public Crewmember() {
        super();
        address = new Address();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getServiceNumber() {
        return serviceNumber;
    }

    public void setServiceNumber(String serviceNumber) {
        this.serviceNumber = serviceNumber;
    }
}
