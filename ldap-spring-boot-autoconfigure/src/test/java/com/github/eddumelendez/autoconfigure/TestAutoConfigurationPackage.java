package com.github.eddumelendez.autoconfigure;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.annotation.Import;

/**
 * Test annotation to configure the {@link AutoConfigurationPackages} to an arbitrary
 * value.
 *
 * @author Phillip Webb
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(TestAutoConfigurationPackageRegistrar.class)
public @interface TestAutoConfigurationPackage {

	Class<?> value();

}
