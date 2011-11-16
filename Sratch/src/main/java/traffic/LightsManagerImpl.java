package traffic;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;


public class LightsManagerImpl implements LightsManager {

	Map<Link, Lights> linkLights = new HashMap<Link, Lights>();
	private final LightsFactory lightsFactory;

	@Inject LightsManagerImpl(final LightsFactory lightsFactory) {
		this.lightsFactory = lightsFactory;
	}

	@Override
	public void addIncomingLink(final Link link) {
		linkLights.put(link, lightsFactory.create());
	}

	@Override
	public boolean isGreen(final Link link) {
		return linkLights.get(link).areGreen();
	}

	@Override
	public void setRed(final Link link) {
		linkLights.get(link).setRed();
	}

	@Override
	public void setGreen(final Link link) {
		linkLights.get(link).setGreen();
	}

	@Override
	public void setAllRed() {
		for (final Link link : linkLights.keySet()) {
			setRed(link);
		}
	}

}
