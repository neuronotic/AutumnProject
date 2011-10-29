package traffic;

import traffic.endtoend.RoadUser;

public class RoadUserManagerImpl implements RoadUserManager {
	@Override
	public RoadUser roadUser(final Itinerary itinerary) {
		return new RoadUserImpl(itinerary);
	}

	@Override
	public void step() {
		// TODO Auto-generated method stub
	}
}
