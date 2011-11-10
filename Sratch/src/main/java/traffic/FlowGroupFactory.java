package traffic;

import java.util.List;

public interface FlowGroupFactory {

	FlowGroup create(TemporalPattern temporalPattern, List<Flow> flows);

}
