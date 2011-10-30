package traffic;


public class RoadUserManagerImpl implements RoadUserManager {
	private RoadUser roadUser;

	@Override
	public void addRoadUser(final RoadUser roadUser) {
		this.roadUser = roadUser;
	}

	@Override
	public void step() {
		roadUser.step();
	}
}
