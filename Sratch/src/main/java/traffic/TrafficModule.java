package traffic;


import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

public class TrafficModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(VehicleFactory.class).to(VehicleFactoryImpl.class);
		bind(VehicleManager.class).to(VehicleManagerImpl.class);
		bind(CellChainBuilder.class).to(CellChainBuilderImpl.class);

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
	}
}