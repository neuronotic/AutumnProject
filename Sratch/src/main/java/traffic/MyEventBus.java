package traffic;

public interface MyEventBus {
	void register(Object subscriber);
	void post(Object event);
}
