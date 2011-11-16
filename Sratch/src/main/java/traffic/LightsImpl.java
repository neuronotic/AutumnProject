package traffic;

public class LightsImpl implements Lights {

	boolean areGreen = true;

	@Override
	public boolean areGreen() {
		return areGreen;
	}

	@Override
	public void setGreen() {
		areGreen = true;
	}

	@Override
	public void setRed() {
		areGreen = false;
	}



}
