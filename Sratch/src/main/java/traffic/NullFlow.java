package traffic;

public class NullFlow implements Flow {

	@Override
	public double probability() {
		// TODO Auto-generated method stub
		return 0;

	}

	@Override
	public Itinerary itinerary() {
		// TODO Auto-generated method stub
		return null;

	}

	@Override
	public String name() {
		return "null flow";
	}

}
