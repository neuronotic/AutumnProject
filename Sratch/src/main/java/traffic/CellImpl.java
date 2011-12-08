package traffic;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

class CellImpl implements Cell {

	private final String name;
	private boolean occupied = false;
	private final Link link;
	private final CellOccupantDepartedMessageFactory messageFactory;
	private final MyEventBus eventBus;

	@Inject CellImpl(
			@Assisted final Link link,
			@Assisted final int index,
			final MyEventBus eventBus,
			final CellOccupantDepartedMessageFactory messageFactory) {
		this.link = link;
		this.eventBus = eventBus;
		this.messageFactory = messageFactory;
		name = String.format("%s[%s]", link.name(), index);
	}

	@Override
	public boolean enter(final Vehicle vehicle) {
		if (occupied) {
			return false;
		}
		occupied = true;
		return true;
	}

	public Link link() {
		return link;
	}

	@Override
	public String toString() {
		return name();
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public boolean isOccupied() {
		return occupied;
	}

	@Override
	public void leave() {
		occupied = false;
		eventBus.post(messageFactory.create(this));
	}

}
