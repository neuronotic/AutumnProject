package traffic;

public interface JourneyEndedMessageFactory {

	JourneyEndedMessage create(Vehicle vehicle, JourneyHistory journeyHistory);

}
