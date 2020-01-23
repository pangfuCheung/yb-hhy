package com.yb.cheung.modules.bss.annotation;

import java.lang.annotation.*;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BssMethodLog {

    String remark() default "";

}
