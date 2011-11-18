package traffic;


import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.matcher.AbstractMatcher;
import com.google.inject.name.Names;

/**
 * build the project with "mvn clean package" in the directory containing pom.xml
 *
 * see http://maven.apache.org/plugins/maven-assembly-plugin/usage.html for details
 *
 * @author daz
 */

public class TrafficModuleCommon extends AbstractModule {
	@Override
	protected void configure() {
		final EventBus eventBus = new EventBus();
		bind(MyEventBus.class).toInstance(new MyEventBusImpl(eventBus));
		bindListener(new AbstractMatcher<TypeLiteral<?>>()
			      {
			         @Override
			         public boolean matches(@SuppressWarnings("unused") final TypeLiteral<?> t)
			         {
			            return true;
			         }
			      }, new EventBusTypeListener(eventBus));
		//bind(VehicleUpdateOrdering.class).to(VehicleUpdateOrderingUnmodified.class);  //how to split this, so one for tests, other for real maccoy?
		  //how to split this, so one for tests, other for real maccoy?
		//bind(VehicleUpdateOrdering.class).to(VehicleUpdateOrderingUnmodified.class);

		bind(NetworkOccupancyBuilder.class).to(NetworkOccupancyBuilderImpl.class);
		bind(JunctionOccupancyBuilder.class).to(JunctionOccupancyBuilderImpl.class);
		bind(Cell.class).annotatedWith(Names.named("NullCell")).to(NullCell.class);
		bind(Flow.class).annotatedWith(Names.named("null")).to(NullFlow.class);
		bind(JunctionController.class).annotatedWith(Names.named("defaultJunctionController")).to(NullJunctionController.class);
		bind(Traffic.class).to(TrafficImpl.class);
		bind(MyRandom.class).to(MyRandomImpl.class);
		//bind(Itinerary.class).to(ItineraryImpl.class);
		bind(FlowBuilder.class).to(FlowBuilderImpl.class);
		bind(FlowGroupBuilder.class).to(FlowGroupBuilderImpl.class);
		bind(VehicleManager.class).to(VehicleManagerImpl.class);
		bind(CellChainBuilder.class).to(CellChainBuilderImpl.class);
		bind(SimulationBuilder.class).to(SimulationBuilderImpl.class);
		bind(LinkBuilder.class).to(LinkBuilderImpl.class);
		bind(JunctionBuilder.class).to(JunctionBuilderImpl.class);
		bind(VehicleBuilder.class).to(VehicleBuilderImpl.class);
		bind(NetworkBuilder.class).to(NetworkBuilderImpl.class);
		bind(VehicleStateFactory.class).to(VehicleStateFactoryImpl.class);
		bind(JourneyHistoryBuilder.class).to(JourneyHistoryBuilderImpl.class);
		bind(TimeKeeper.class).to(TimeKeeperImpl.class);
		bind(DefaultNetworks.class).to(DefaultNetworksImpl.class);
		bind(NetworkFluxBuilder.class).to(NetworkFluxBuilderImpl.class);
		bind(NetworkOccupancyTimeSeries.class).to(NetworkOccupancyTimeSeriesImpl.class);
		bind(LightsManager.class).to(LightsManagerImpl.class);

		install(new FactoryModuleBuilder()
			.implement(Lights.class, LightsImpl.class)
			.build(LightsFactory.class));

		install(new FactoryModuleBuilder()
			.implement(NetworkFluxBuilder.class, NetworkFluxBuilderImpl.class)
			.build(NetworkFluxBuilderFactory.class));

		install(new FactoryModuleBuilder()
			.implement(FluxReceiver.class, FluxReceiverImpl.class)
			.build(FluxReceiverFactory.class));

		install(new FactoryModuleBuilder()
			.implement(NetworkFlux.class, NetworkFluxImpl.class)
			.build(NetworkFluxFactory.class));

		install(new FactoryModuleBuilder()
			.implement(LinkFlux.class, LinkFluxImpl.class)
			.build(LinkFluxFactory.class));


		install(new FactoryModuleBuilder()
			.implement(StatisticsManager.class, StatisticsManagerImpl.class)
			.build(StatisticsManagerFactory.class));


		install(new FactoryModuleBuilder()
			.implement(NetworkOccupancy.class, NetworkOccupancyImpl.class)
			.build(NetworkOccupancyFactory.class));


		install(new FactoryModuleBuilder()
			.implement(JunctionOccupancy.class, JunctionOccupancyImpl.class)
			.build(JunctionOccupancyFactory.class));

		install(new FactoryModuleBuilder()
			.implement(Occupancy.class, OccupancyImpl.class)
			.build(OccupancyFactory.class));

		install(new FactoryModuleBuilder()
			.implement(LinkOccupancy.class, LinkOccupancyImpl.class)
			.build(LinkOccupancyFactory.class));

		install(new FactoryModuleBuilder()
			.implement(NetworkBuilder.class, NetworkBuilderImpl.class)
			.build(NetworkBuilderFactory.class));

		install(new FactoryModuleBuilder()
			.implement(LinkBuilder.class, LinkBuilderImpl.class)
			.build(LinkBuilderFactory.class));

		install(new FactoryModuleBuilder()
			.implement(VehicleCreator.class, VehicleCreatorImpl.class)
			.build(VehicleCreatorFactory.class));

		install(new FactoryModuleBuilder()
			.implement(Flow.class, FlowImpl.class)
			.build(FlowFactory.class));

		install(new FactoryModuleBuilder()
			.implement(FlowGroup.class, FlowGroupImpl.class)
			.build(FlowGroupFactory.class));

		install(new FactoryModuleBuilder()
			.implement(JunctionMeasuresMessage.class, JunctionMeasuresMessageImpl.class)
			.build(JunctionMeasuresMessageFactory.class));


		install(new FactoryModuleBuilder()
			.implement(JourneyStartedMessage.class, JourneyStartedMessageImpl.class)
			.build(JourneyStartedMessageFactory.class));

		install(new FactoryModuleBuilder()
			.implement(JourneyEndedMessage.class, JourneyEndedMessageImpl.class)
			.build(JourneyEndedMessageFactory.class));

		install(new FactoryModuleBuilder()
			.implement(JourneyHistory.class, JourneyHistoryImpl.class)
			.build(JourneyHistoryFactory.class));

		install(new FactoryModuleBuilder()
	    	.implement(VehicleStateContext.class, VehicleStateContextImpl.class)
	    	.build(VehicleStateContextFactory.class));

		install(new FactoryModuleBuilder()
		    .implement(Network.class, NetworkImpl.class)
		    .build(NetworkFactory.class));

		install(new FactoryModuleBuilder()
		    .implement(Junction.class, JunctionImpl.class)
		    .build(JunctionFactory.class));

		install(new FactoryModuleBuilder()
		    .implement(Link.class, LinkImpl.class)
		    .build(LinkFactory.class));

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
}