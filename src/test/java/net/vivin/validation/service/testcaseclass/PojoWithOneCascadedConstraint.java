package net.vivin.validation.service.testcaseclass;

import javax.validation.Valid;

public class PojoWithOneCascadedConstraint {

    @Valid
    private PojoWithConstraints pojoWithConstraints;
}
