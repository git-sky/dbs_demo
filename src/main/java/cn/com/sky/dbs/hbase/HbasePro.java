package cn.com.sky.dbs.hbase;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface HbasePro {

	/**
	 * Hbase属性名称
	 */
	String value();
}
