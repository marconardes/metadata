package org.esfinge.metadata.validate.optimizeExecution;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.esfinge.metadata.annotation.validator.Prohibits;

@Prohibits(OptimizeExecution01.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface Logging01 {

}
