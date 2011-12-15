package traffic;

import com.google.inject.assistedinject.Assisted;

public interface EquisaturationFactory {

	JunctionController create(@Assisted("period") SimulationTime period, @Assisted("switchingDelay") SimulationTime switchingDelay);

}
