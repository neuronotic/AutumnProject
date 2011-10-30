package traffic;


public class VehicleFactoryImpl implements VehicleFactory {
	@Override
	public Vehicle createVehicle(final Itinerary itinerary) {
		return new VehicleImpl(itinerary.iterator(), new JourneyHistoryImpl());
	}
}
