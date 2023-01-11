package ushiosan.jvm_utilities.lang.print.annotations;

import ushiosan.jvm_utilities.lang.Obj;
import ushiosan.jvm_utilities.lang.print.PrintObj;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to tell the method {@link Obj#toInstanceString(Object)} to ignore that element and
 * not take it into account
 *
 * @see Obj#toInstanceString(Object)
 * @see PrintObj#toInstanceString(Object)
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PrintExclude {}