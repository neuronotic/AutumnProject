package traffic;

import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class MyEventBusImpl implements MyEventBus {
	//TODO: untested

	private final EventBus eventBus;

	@Inject MyEventBusImpl(final EventBus eventBus) {
		this.eventBus = eventBus;
	}

	@Override
	public void register(final Object subscriber) {
		eventBus.register(subscriber);
	}

	@Override
	public void post(final Object event) {
		eventBus.post(event);
	}

}
