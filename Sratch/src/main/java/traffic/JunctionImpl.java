package traffic;

import java.util.LinkedList;
import java.util.logging.Logger;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;


class JunctionImpl implements Junction {
	@Inject Logger logger = Logger.getAnonymousLogger();

	private final String name;
	private boolean occupied = false;
	private Vehicle occupationReservee;
	private final LinkedList<Vehicle> vehiclesWaiting = new LinkedList<Vehicle>();

	@Inject
	JunctionImpl(@Assisted final String name) {
		this.name = name;
	}

	@Override
	public boolean enter(final Vehicle vehicle) {
		if (occupied) {
			return vehicle.equals(occupationReservee);
		}
		occupied = true;
		return true;
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
	public void step() {
		//when have lights, also introduce check for lights green....
		if (!isOccupied() && !vehiclesWaiting.isEmpty()) {
			final Vehicle vehicle = vehiclesWaiting.pop();
			vehicle.startJourney();
			occupationReservee = vehicle;
		}
	}

	@Override
	public void addVehicle(final Vehicle vehicle) {
		vehiclesWaiting.add(vehicle);
	}
}
