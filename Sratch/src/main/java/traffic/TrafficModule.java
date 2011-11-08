package traffic;


import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * build the project with "mvn clean package" in the directory containing pom.xml
 *
 * see http://maven.apache.org/plugins/maven-assembly-plugin/usage.html for details
 *
 * @author daz
 */

public class TrafficModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(Traffic.class).to(TrafficImpl.class);

		bind(VehicleManager.class).to(VehicleManagerImpl.class);
		bind(VehicleManagerBuilder.class).to(VehicleManagerBuilderImpl.class);
		bind(CellChainBuilder.class).to(CellChainBuilderImpl.class);
		bind(SimulationBuilder.class).to(SimulationBuilderImpl.class);
		bind(SegmentBuilder.class).to(SegmentBuilderImpl.class);
		bind(VehicleBuilder.class).to(VehicleBuilderImpl.class);
		bind(JourneyStepper.class).to(JourneyStepperImpl.class);
		bind(RoadNetworkBuilder.class).to(RoadNetworkBuilderImpl.class);
		bind(VehicleStateFactory.class).to(VehicleStateFactoryImpl.class);
		bind(VehicleStateContextBuilder.class).to(VehicleStateContextBuilderImpl.class);
		bind(JourneyHistoryBuilder.class).to(JourneyHistoryBuilderImpl.class);

		install(new FactoryModuleBuilder()
			.implement(JourneyHistory.class, JourneyHistoryImpl.class)
			.build(JourneyHistoryFactory.class));

		install(new FactoryModuleBuilder()
    		.implement(Cell.class, NullCell.class)
    		.build(NullCellFactory.class));

		install(new FactoryModuleBuilder()
	    	.implement(VehicleStateContext.class, VehicleStateContextImpl.class)
	    	.build(VehicleStateContextFactory.class));

		install(new FactoryModuleBuilder()
		    .implement(RoadNetwork.class, RoadNetworkImpl.class)
		    .build(RoadNetworkFactory.class));

		install(new FactoryModuleBuilder()
		    .implement(Junction.class, JunctionImpl.class)
		    .build(JunctionFactory.class));

		install(new FactoryModuleBuilder()
		    .implement(Segment.class, SegmentImpl.class)
		    .build(SegmentFactory.class));

		install(new FactoryModuleBuilder()
		    .implement(Cell.class, CellImpl.class)
		    .build(CellFactory.class));

		install(new FactoryModuleBuilder()
		    .implement(CellChain.class, CellChainImpl.class)
		    .build(CellChainFactory.class));

		install(new FactoryModuleBuilder()
		    .implement(Vehicle.class, VehicleImpl.class)
		    .build(VehicleFactory.class));

		install(new FactoryModuleBuilder()
			.implement(RouteFinder.class, ShortestPathRouteFinder.class)
			.build(RouteFinderFactory.class));

		install(new FactoryModuleBuilder()
		.implement(VehicleManager.class, VehicleManagerImpl.class)
		.build(VehicleManagerFactory.class));

		install(new FactoryModuleBuilder()
			.implement(Simulation.class, SimulationImpl.class)
			.build(SimulationFactory.class));
	}

	public static void main(final String args[]) {
		Guice.createInjector(new TrafficModule()).getInstance(Traffic.class).start();
	}
}