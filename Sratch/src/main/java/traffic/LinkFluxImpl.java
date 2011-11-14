package traffic;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class LinkFluxImpl implements LinkFlux {

	private final Link link;
	private final int flux;

	@Inject public LinkFluxImpl(
			@Assisted final Link link,
			@Assisted final int flux) {
		this.link = link;
		this.flux = flux;
	}

	@Override
	public Link link() {
		return link;
	}

}
