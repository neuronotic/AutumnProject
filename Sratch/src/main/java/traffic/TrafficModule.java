package traffic;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;

public class TrafficModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new TrafficModuleCommon());
		install(new TrafficModuleProduction());
	}

	public static void main(final String args[]) {
		Guice.createInjector(new TrafficModule()).getInstance(Traffic.class).start(args);
	}
}
