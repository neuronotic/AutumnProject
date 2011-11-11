package traffic;

import java.util.List;

public interface FlowGroup {

	TemporalPattern temporalPattern();

	List<Flow> flows();

}
