package traffic;

import org.apache.commons.lang3.builder.StandardToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class TrafficToStringStyle {
	private static final StandardToStringStyle toStringStyle = new StandardToStringStyle();
	static {
		toStringStyle.setUseShortClassName(true);
		toStringStyle.setUseIdentityHashCode(false);
	}

	public static ToStringStyle networkToStringStyle() {
		return toStringStyle;
	}

	public static String networkReflectionToString(final Object object) {
		return ToStringBuilder.reflectionToString(object, toStringStyle);
	}
}
