package net.vivin.regula.validation.service.testcaseclass;

import javax.validation.Valid;

public class ToplevelPojoWithCascadedConstraint {

    @Valid
    private PojoWithOneCascadedConstraint pojoWithOneCascadedConstraint;
}
