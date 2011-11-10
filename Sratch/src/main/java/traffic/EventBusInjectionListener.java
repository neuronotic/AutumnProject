package traffic;

import com.google.common.eventbus.EventBus;
import com.google.inject.spi.InjectionListener;


final class EventBusInjectionListener<I> implements InjectionListener<I> {
	private final EventBus eventBus;
	EventBusInjectionListener(final EventBus eventBus) {
		this.eventBus = eventBus;
	}

	@Override
	public void afterInjection(final I injectee) {
		eventBus.register(injectee);
	}
}