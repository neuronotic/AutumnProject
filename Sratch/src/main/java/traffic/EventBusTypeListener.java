package traffic;


import com.google.common.eventbus.EventBus;
import com.google.inject.TypeLiteral;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

final class EventBusTypeListener implements TypeListener {
	private final EventBus eventBus;

	EventBusTypeListener(final EventBus eventBus) {this.eventBus = eventBus;}

	@Override
	public <I> void hear(@SuppressWarnings("unused") final TypeLiteral<I> type, final TypeEncounter<I> encounter) {
		encounter.register(new EventBusInjectionListener<I>(eventBus));
	}
}