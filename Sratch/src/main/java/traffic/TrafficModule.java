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

	public static void main(final String args[]) {
		Guice.createInjector(new TrafficModule()).getInstance(Traffic.class).start();
	}
}