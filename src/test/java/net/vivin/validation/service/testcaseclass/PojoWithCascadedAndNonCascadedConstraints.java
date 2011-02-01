package net.vivin.validation.service.testcaseclass;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.Min;

public class PojoWithCascadedAndNonCascadedConstraints {

    @Valid
    private PojoWithConstraints firstPojoWithConstraints;

    @Valid
    private PojoWithConstraints secondPojoWithConstraints;

    @NotBlank
    private String stringField;

    @Min(0)
    private int intField;
}
