package net.vivin.validation.service.testcaseclass;

import javax.validation.Valid;

public class ToplevelPojoWithCascadedConstraint {

    @Valid
    private PojoWithOneCascadedConstraint pojoWithOneCascadedConstraint;
}
