package traffic;

public interface VehicleBuilder {

	Vehicle make();

	VehicleBuilder withName(String vehicleName);

	VehicleBuilder withItinerary(Itinerary itinerary);

	VehicleBuilder withFlow(Flow flow);

}
