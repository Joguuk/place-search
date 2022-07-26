package com.jjozerg.search.common.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * packageName : com.jjozerg.search.common
 * fileName : WebAdaptor
 * author : joguk
 * description : WebAdaptor Anotation
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface WebAdaptor {
    @AliasFor(annotation = Component.class)
    String value() default "";
}
