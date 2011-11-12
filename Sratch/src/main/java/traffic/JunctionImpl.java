package traffic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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
	private final MyEventBus eventBus;

	private final JunctionMeasuresMessageFactory messageFactory;

	@Inject
	JunctionImpl(final MyEventBus eventBus,  final JunctionMeasuresMessageFactory messageFactory, @Assisted final String name) {
		this.eventBus = eventBus;
		this.messageFactory = messageFactory;
		this.name = name;
	}


	@Override
	public boolean enter(final Vehicle vehicle) {
		if (!occupied) {
			if (vehiclesWaiting.isEmpty()) {
				//noteFlow(vehicle.location());
				occupied = true;
				return true;
			}
			if (!inQueue(vehicle) || inQueue(vehicle) && isFirstInQueue(vehicle)) {
				occupied = true;
				vehiclesWaiting.pop();
				return true;
			}
		} return false;
	}


	private void noteFlow(final Cell location) {
		// TODO Auto-generated method stub
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
		//JunctionOccupancy junctionOccupancy = junctionOccupancyFactory.create()
		//eventBus.post(junctionOccupancy)
	}

	@Override
	public void addIncomingLinks(final Link link) {
		incomingLinks.add(link);
	}

	@Override
	public void addOutgoingLink(final Link link) {
		outgoingLinks.add(link);
	}

	@Override
	public int inBoundLinksOccupancy() {
		int occupiedCount = 0;
		for (final Link link : incomingLinks) {
			occupiedCount += link.occupiedCount();
		}
		return occupiedCount;
	}

	@Override
	public int occupancy() {
		return occupied ? 1 : 0;
	}

	@Override
	public int capacity() {
		return 1;
	}

	@Override
	public int inBoundLinksCapacity() {
		int cellCount = 0;
		for (final Link link : incomingLinks) {
			cellCount += link.cellCount();
		}
		return cellCount;
	}

	@Override
	public Object vehiclesWaitingToJoin() {
		return vehiclesWaiting.size();
	}

}
