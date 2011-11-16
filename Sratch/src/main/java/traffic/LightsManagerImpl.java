package traffic;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;


public class LightsManagerImpl implements LightsManager {

	Map<Link, Lights> links = new HashMap<Link, Lights>();
	private final LightsFactory lightsFactory;

	@Inject LightsManagerImpl(final LightsFactory lightsFactory) {
		this.lightsFactory = lightsFactory;
	}

	@Override
	public void addIncomingLink(final Link link) {
		links.put(link, lightsFactory.create());
	}

}
