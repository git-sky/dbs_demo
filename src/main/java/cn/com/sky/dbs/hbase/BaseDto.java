package cn.com.sky.dbs.hbase;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class BaseDto implements Serializable, Cloneable {

	private static final long serialVersionUID = -5152170884916847629L;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	@SuppressWarnings("unchecked")
	public <T> T clone(Class<T> cls) throws CloneNotSupportedException {
		return (T) clone();
	}
}
