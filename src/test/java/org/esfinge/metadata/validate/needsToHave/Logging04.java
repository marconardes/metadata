package org.esfinge.metadata.validate.needsToHave;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.esfinge.metadata.annotation.validator.NeedsToHave;

@NeedsToHave(Transaction04.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface Logging04 {

}