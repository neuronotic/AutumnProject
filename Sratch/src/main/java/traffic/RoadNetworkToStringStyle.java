package traffic;

import org.apache.commons.lang3.builder.StandardToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class RoadNetworkToStringStyle {
	private static final StandardToStringStyle toStringStyle = new StandardToStringStyle();
	static {
		toStringStyle.setUseShortClassName(true);
		toStringStyle.setUseIdentityHashCode(false);
	}

	public static ToStringStyle roadNetworkToStringStyle() {
		return toStringStyle;
	}

	public static String roadNetworkReflectionToString(final Object object) {
		return ToStringBuilder.reflectionToString(object, toStringStyle);
	}
}
