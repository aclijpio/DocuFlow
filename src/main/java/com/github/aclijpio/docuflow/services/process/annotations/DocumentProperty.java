package com.github.aclijpio.docuflow.services.process.annotations;

import com.github.aclijpio.docuflow.services.PropertyType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DocumentProperty {
    PropertyType type() default PropertyType.TEXT_FIELD;
    String value();
}
