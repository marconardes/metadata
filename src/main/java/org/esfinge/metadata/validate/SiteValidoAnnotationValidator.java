package org.esfinge.metadata.validate;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

import org.esfinge.metadata.AnnotationPropertyValidator;
import org.esfinge.metadata.AnnotationValidationException;
import org.esfinge.metadata.annotation.validator.MaxValue;

public class SiteValidoAnnotationValidator implements AnnotationPropertyValidator{	
	private int maxValue;
	private MaxValue mv;

	@Override
	public void initialize(Annotation self) {
		mv = (MaxValue) self;
		maxValue = mv.value();
	}
	
	@Override
	public void validate(Annotation annotationOnElement,
			AnnotatedElement annotatedWithMainAnnotation,
			Method annotatedWithValidation, Object annotationPropertyValue)
			throws AnnotationValidationException {
		
		Integer value = (Integer) annotationPropertyValue;
		if(maxValue < value){
			throw new AnnotationValidationException("The attribute "+ annotatedWithValidation.getName() +" of @" +annotationOnElement.annotationType().getName() + " has a value("+value+"), which is greather than the @MaxValue("+maxValue+")");
		}
		
	
		
	}
}
