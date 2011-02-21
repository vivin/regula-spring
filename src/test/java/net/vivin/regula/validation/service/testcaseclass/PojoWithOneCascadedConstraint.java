package net.vivin.regula.validation.service.testcaseclass;

import javax.validation.Valid;

public class PojoWithOneCascadedConstraint {

    @Valid
    private PojoWithConstraints pojoWithConstraints;
}
