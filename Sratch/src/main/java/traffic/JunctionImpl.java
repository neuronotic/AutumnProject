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
	private final List<Segment> incomingSegments = new ArrayList<Segment>();
	private final List<Segment> outgoingSegments = new ArrayList<Segment>();
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
	public void addIncomingSegment(final Segment segment) {
		incomingSegments.add(segment);
	}

	@Override
	public void addOutgoingSegment(final Segment segment) {
		outgoingSegments.add(segment);
	}

	@Override
	public int inBoundSegmentsOccupancy() {
		int occupiedCount = 0;
		for (final Segment segment : incomingSegments) {
			occupiedCount += segment.occupiedCount();
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
	public int inBoundSegmentsCapacity() {
		int cellCount = 0;
		for (final Segment segment : incomingSegments) {
			cellCount += segment.cellCount();
		}
		return cellCount;
	}

	@Override
	public Object vehiclesWaitingToJoin() {
		return vehiclesWaiting.size();
	}

}
