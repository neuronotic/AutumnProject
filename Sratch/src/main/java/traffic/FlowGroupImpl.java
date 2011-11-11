package traffic;

import java.util.List;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class FlowGroupImpl implements FlowGroup {

	private final TemporalPattern temporalPattern;
	private final List<Flow> flows;

	@Inject FlowGroupImpl(
			@Assisted final TemporalPattern temporalPattern,
			@Assisted final List<Flow> flows) {
				this.temporalPattern = temporalPattern;
				this.flows = flows;
	}

	@Override
	public TemporalPattern temporalPattern() {
		return temporalPattern;
	}

	@Override
	public List<Flow> flows() {
		return flows;
	}
}
