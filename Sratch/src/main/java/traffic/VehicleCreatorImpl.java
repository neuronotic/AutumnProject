package traffic;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class VehicleCreatorImpl implements VehicleCreator {
	@Inject Logger logger = Logger.getAnonymousLogger();

	private final List<FlowGroup> flowGroups;
	private final MyRandom random;
	private int vehicleCount = 0;
	private final VehicleBuilder vehicleBuilder;

	@Inject public VehicleCreatorImpl(final MyRandom random, final VehicleBuilder vehicleBuilder, @Assisted final List<FlowGroup> flowGroups) {
		this.random = random;
		this.vehicleBuilder = vehicleBuilder;
		this.flowGroups = flowGroups;
	}

	@Override
	public List<Vehicle> step() {
		//logger.info(String.format("vehicleCreatorStep"));
		final List<Vehicle> vehicles = new ArrayList<Vehicle>();
		double probability;
		double temporalProbabilityModifier;
		List<Flow> flows;
		for (final FlowGroup flowGroup : flowGroups) {
			temporalProbabilityModifier = flowGroup.temporalPattern().modifier();
			flows = flowGroup.flows();
			for (final Flow flow : flows) {
				probability = flow.probability() * temporalProbabilityModifier;
				if (random.bernoulli(probability)) {
					vehicles.add(createVehicle(flow.itinerary()));
				}
			}
		}
		return vehicles;
	}

	private Vehicle createVehicle(final Itinerary itinerary) {
		final Vehicle vehicle = vehicleBuilder
			.withName(vehicleName())
			.withItinerary(itinerary)
			.make();
		if (itinerary.cells().get(0) instanceof Junction) {
			final Junction junction = (Junction) itinerary.cells().get(0);
			junction.addVehicle(vehicle);
		}
		return vehicle;
	}

	private String vehicleName() {
		return String.format("vehicle%d", vehicleCount++);
	}

}
