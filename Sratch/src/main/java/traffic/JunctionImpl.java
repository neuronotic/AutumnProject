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
	private boolean occupied = false;
	private final LinkedList<Vehicle> vehiclesWaiting = new LinkedList<Vehicle>();
	private final List<Link> incomingLinks = new ArrayList<Link>();
	private final List<Link> outgoingLinks = new ArrayList<Link>();

	private final JunctionOccupancyFactory junctionOccupancyFactory;
	private final JunctionControllerStrategy junctionController;
	private final OccupancyFactory occupancyFactory;

	private final MyEventBus eventBus;


	@Inject
	JunctionImpl(
			final MyEventBus eventBus,
			final JunctionOccupancyFactory junctionOccupancyFactory,
			final OccupancyFactory occupancyFactory,
			@Assisted final String name,
			@Assisted final JunctionControllerStrategyBuilder junctionControllerStrategyBuilder) {
		this.eventBus = eventBus;
		this.junctionOccupancyFactory = junctionOccupancyFactory;
		this.occupancyFactory = occupancyFactory;
		this.name = name;
		junctionController = junctionControllerStrategyBuilder.make(this);
	}


	@Override
	public boolean enter(final Vehicle vehicle) {
		if (!occupied) {
			if (vehiclesWaiting.isEmpty() || !inQueue(vehicle)) {
				occupied = true;
				return true;
			}
			if (inQueue(vehicle) && isFirstInQueue(vehicle)) {
				occupied = true;
				vehiclesWaiting.pop();
				return true;
			}
		} return false;
	}

	private boolean isFirstInQueue(final Vehicle vehicle) {
		return vehicle.equals(vehiclesWaiting.getFirst());
	}

	private boolean inQueue(final Vehicle vehicle) {
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
		return occupied;
	}

	@Override
	public void leave() {
		occupied = false;
	}

	@Override
	public void addVehicle(final Vehicle vehicle) {
		vehiclesWaiting.add(vehicle);
	}

	@Override
	public void step() {
	}

	@Override
	public void addIncomingLink(final Link link) {
		incomingLinks.add(link);
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
