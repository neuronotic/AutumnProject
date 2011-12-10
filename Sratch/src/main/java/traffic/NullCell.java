package traffic;

import java.util.logging.Logger;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.google.inject.Inject;

public class NullCell implements Cell {
	@Inject Logger logger = Logger.getAnonymousLogger();

	@Inject
	public NullCell() {}

	@Override
	public boolean enter(final Vehicle vehicle) {
		return false;
	}

	@Override
	public String name() {
		return "Null cell";
	}

	@Override
	public String toString() {
		return name();
	}

	@Override
	public void leave() {
	}

	@Override
	public boolean isOccupied() {
		return false;
	}

	@Override
	public boolean equals(final Object that) {
		//logger.info(String.format("NULL: %s equals %s", this, that));
		if (that != null && that.getClass().equals(this.getClass())) {
			return true;
		} return false;
	}

	@Override //TODO: is this ok?
	public int hashCode() {
		return new HashCodeBuilder()
			.toHashCode();
	}

	@Override
	public void recordFlux() {
		// TODO Auto-generated method stub

	}

}
