package traffic;

import java.util.LinkedList;
import java.util.logging.Logger;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;


class JunctionImpl implements Junction {
	@Inject Logger logger = Logger.getAnonymousLogger();

	private final String name;
	private boolean occupied = false;
	private final LinkedList<Vehicle> vehiclesWaiting = new LinkedList<Vehicle>();

	@Inject
	JunctionImpl(@Assisted final String name) {
		this.name = name;
	}

	@Override
	public boolean enter(final Vehicle vehicle) {
		if (!occupied) {
			if (vehiclesWaiting.isEmpty()) {
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
}
