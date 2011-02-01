package net.vivin.starship.domain;

import net.vivin.starship.validation.annotation.ValidZip;
import net.vivin.starship.validation.group.AddressGroup;
import org.hibernate.validator.constraints.NotBlank;

public class Address {

    @NotBlank(groups={AddressGroup.class})
    private String street;

    @NotBlank(groups={AddressGroup.class})
    private String city;

    @NotBlank(groups={AddressGroup.class})
    private String state;

    @NotBlank(groups={AddressGroup.class})
    private String country;

    @ValidZip(groups={AddressGroup.class})
    private int zip;

    public Address() {
        super();
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }
}
