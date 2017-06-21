package pres.wisdom.util;

import java.lang.annotation.ElementType;

import java.lang.annotation.Retention;

import java.lang.annotation.RetentionPolicy;

import java.lang.annotation.Target;

/**
 * 
 * 防止重复提交用
 * 
 * @author wsc
 * 
 *         2016年12月5日
 */

@Target(ElementType.METHOD)
// 如果用在类上，将此行注释
@Retention(RetentionPolicy.RUNTIME)
public @interface Token {

	boolean save() default false;

	boolean remove() default false;

}