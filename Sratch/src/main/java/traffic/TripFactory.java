package traffic;

public class TripFactory {

	public static TripOrigin tripFrom(final Junction junction) {
		return new TripOriginImpl(junction);
	}

}
