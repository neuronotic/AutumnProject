package traffic;


public class NullJunctionController implements
		JunctionController {

	@Override
	public void step(final LightsManager lightsManager) {
	}

	@Override
	public String toString() {
		return "firstComeFirstServedStrategy";
	}
}
