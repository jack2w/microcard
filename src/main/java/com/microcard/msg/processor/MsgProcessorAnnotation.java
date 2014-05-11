/**
 * 
 */
package com.microcard.msg.processor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author jack
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MsgProcessorAnnotation {

	Class<? extends IMsgProcessor> MsgClass();
}
