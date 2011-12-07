package traffic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;


class JunctionImpl implements Junction {
	@Inject Logger logger = Logger.getAnonymousLogger();

	private final String name;
	private boolean isOccupied = false;
	private final LinkedList<Vehicle> vehiclesWaiting = new LinkedList<Vehicle>();
	private final List<Link> incomingLinks = new ArrayList<Link>();
	private final List<Link> outgoingLinks = new ArrayList<Link>();

	private final JunctionOccupancyFactory junctionOccupancyFactory;
	private final JunctionController junctionController;
	private final OccupancyFactory occupancyFactory;
	private final LightsManager lightsManager;

	@Inject
	JunctionImpl(
			final JunctionOccupancyFactory junctionOccupancyFactory,
			final OccupancyFactory occupancyFactory,
			final LightsManager lightsManager,
			@Assisted final String name,
			@Assisted final JunctionController junctionController) {
		this.junctionOccupancyFactory = junctionOccupancyFactory;
		this.occupancyFactory = occupancyFactory;
		this.name = name;
		this.junctionController = junctionController;
		this.lightsManager = lightsManager;
	}

	@Override
	public boolean enter(final Vehicle vehicle) {
		if (isOccupied) {
			return false;
		}
		if (inNewlyCreatedQueue(vehicle)) {
			if (isFirstInQueue(vehicle)) {
				isOccupied = true;
				vehiclesWaiting.pop();
				return true;
			}
			return false;
		}
		if (lightsGreenForVehicle(vehicle)) {
			isOccupied = true;
			return true;
		}
		return false;
	}

	private boolean lightsGreenForVehicle(final Vehicle vehicle) {
		//TODO: this is a hack!
		if (incomingLinks.isEmpty() || vehicle.location() instanceof NullCell || vehicle.location() instanceof JunctionImpl) {
			return true;
		}
		return lightsManager.isGreen(((CellImpl)vehicle.location()).link());
	}

	private boolean isFirstInQueue(final Vehicle vehicle) {
		return vehicle.equals(vehiclesWaiting.getFirst());
	}

	private boolean inNewlyCreatedQueue(final Vehicle vehicle) {
		return vehiclesWaiting.contains(vehicle);
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public String toString() {
		return String.format("Junction %s", name);
	}

	@Override
	public boolean isOccupied() {
		return isOccupied;
	}

	@Override
	public void leave() {
		isOccupied = false;
	}

	@Override
	public void addVehicle(final Vehicle vehicle) {
		vehiclesWaiting.add(vehicle);
	}

	@Override
	public void step() {
//		logger.info(String.format("%s stepped", name()));
		if (!incomingLinks.isEmpty()) {
			junctionController.step(lightsManager);
		}
	}

	@Override
	public void addIncomingLink(final Link link) {
		incomingLinks.add(link);
		lightsManager.addIncomingLink(link);
		//junctionController.addIncomingLink(link);
	}

	@Override
	public void addOutgoingLink(final Link link) {
		outgoingLinks.add(link);
	}

	@Override
	public Object vehiclesWaitingToJoin() {
		return vehiclesWaiting.size();
	}

	@Override
	public JunctionOccupancy occupancy() {
		final Occupancy occupancy = occupancyFactory.create(isOccupied() ? 1 : 0, 1);

		final Set<LinkOccupancy> linkOccupancies = new HashSet<LinkOccupancy>();
		for (final Link link : incomingLinks) {
			linkOccupancies.add(link.occupancy());
		}
		return junctionOccupancyFactory.create(this, occupancy, linkOccupancies);
	}

}
