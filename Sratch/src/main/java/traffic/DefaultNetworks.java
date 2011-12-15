package traffic;

public interface DefaultNetworks {

	public Network yNetwork3Link(JunctionControllerBuilder junctionControllerBuilder, int linkLength);

	public Network xNetwork4Link(JunctionControllerBuilder junctionControllerBuilder, int linkLength);

	public Network vNetwork2Link(JunctionControllerBuilder junctionControllerBuilder, int linkLength);

	public Network crossedDiamond(JunctionControllerBuilder junctionControllerBuilder, int linkLength);

	public Network singleLink(JunctionControllerBuilder junctionControllerBuilder, int linkLength);
}
