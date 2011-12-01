package traffic;

public interface JunctionBuilder {
	JunctionBuilder withName(String name);
	Junction make();
	//JunctionBuilder withController(JunctionController junctionController);
	JunctionBuilder withControllerBuilder(JunctionControllerBuilder controllerBuilder);
}
