package traffic;


public class VehicleFactoryImpl implements VehicleFactory {

	public VehicleFactoryImpl() {
	}

	@Override
	public Vehicle createVehicle(final Itinerary itinerary) {
		return new VehicleImpl(itinerary.iterator(), new JourneyHistoryImpl());
	}
}
