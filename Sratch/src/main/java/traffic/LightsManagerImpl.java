package traffic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.google.inject.Inject;


public class LightsManagerImpl implements LightsManager {
	@Inject Logger logger = Logger.getAnonymousLogger();


	Map<Link, Lights> linkLights = new HashMap<Link, Lights>();
	List<Link> links = new ArrayList<Link>();
	private final LightsFactory lightsFactory;

	@Inject LightsManagerImpl(final LightsFactory lightsFactory) {
		this.lightsFactory = lightsFactory;
	}

	@Override
	public void addIncomingLink(final Link link) {
		linkLights.put(link, lightsFactory.create());
		links.add(link);
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
		//logger.info(String.format("link %s went green at time %d!", link.name(), timeKeeper.currentTime().value()));
		linkLights.get(link).setGreen();
	}

	@Override
	public void setAllRed() {
		for (final Link link : linkLights.keySet()) {
			setRed(link);
		}
	}

	@Override
	public List<Link> linksInOrderAdded() {
		return Collections.unmodifiableList(links);
	}

	@Override
	public String toString() {
		final StringBuffer lightsStateString = new StringBuffer("(");
		for (final Link link : linksInOrderAdded()) {
			lightsStateString.append(String.format("%s:%s, ", link.name(), isGreen(link)));
		}
		lightsStateString.append(")");
		return lightsStateString.toString();
	}
}
