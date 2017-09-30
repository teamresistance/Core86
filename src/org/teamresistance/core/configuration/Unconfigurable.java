package org.teamresistance.core.configuration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation which marks a FIELD as not capable of being configured.
 * 
 * <p> NOTE: serves a similar purpose to the <code>transient</code> keyword.
 * 
 * @author Frank McCoy
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Unconfigurable {
	
}