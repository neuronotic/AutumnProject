package traffic;

public interface TripOrigin {
	Trip to(Junction destinationJunction);

	Junction origin();
}
