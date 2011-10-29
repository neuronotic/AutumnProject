package traffic;


public class RoadUserManagerFactory {
	public static RoadUserManager roadUserManager() {
		return new RoadUserManagerImpl(new RoadUserFactoryImpl());
	}
}
