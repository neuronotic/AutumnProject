package traffic;

import org.apache.commons.lang3.builder.StandardToStringStyle;
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
}
