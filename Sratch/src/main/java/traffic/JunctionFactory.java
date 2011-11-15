package traffic;


public interface JunctionFactory {
	Junction createJunction(String junctionName, JunctionControllerStrategyBuilder junctionControllerStrategyBuilder);
}
