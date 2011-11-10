package traffic;

import java.util.List;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class FlowGroupImpl implements FlowGroup {

	@Inject FlowGroupImpl(
			@Assisted final TemporalPattern temporalPattern,
			@Assisted final List<Flow> flows) {

	}
}
