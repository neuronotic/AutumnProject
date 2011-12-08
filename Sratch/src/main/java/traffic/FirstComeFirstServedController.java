package traffic;


public class FirstComeFirstServedController implements
		JunctionController {

	@Override
	public void step(final LightsManager lightsManager) {
	}

	@Override
	public String toString() {
		return "firstComeFirstServedStrategy";
	}
}
