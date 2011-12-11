package traffic.graphing;

import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;

public class GraphBuilderImpl implements GraphBuilder {

	private String windowTitle;
	private String graphTitle;
	private String xAxisLabel;
	private String yAxisLabel;
	private boolean setYRange0To1 = false;
	private XYDataset dataset;
	private XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

	@Override
	public GraphBuilder withWindowTitle(final String windowTitle) {
		this.windowTitle = windowTitle;
		return this;
	}

	@Override
	public GraphBuilder withGraphTitle(final String graphTitle) {
		this.graphTitle = graphTitle;
		return this;
	}

	@Override
	public GraphBuilder withXAxisLabel(final String xAxisLabel) {
		this.xAxisLabel = xAxisLabel;
		return this;
	}

	@Override
	public GraphBuilder withYAxisLabel(final String yAxisLabel) {
		this.yAxisLabel = yAxisLabel;
		return this;
	}

	@Override
	public GraphBuilder withYRange0to1() {
		setYRange0To1 = true;
		return this;
	}

	@Override
	public GraphBuilder withDataset(final XYDataset dataset) {
		this.dataset = dataset;
		return this;
	}

	@Override
	public MyGraph make() {
		return new MyGraphImpl(this, renderer);
	}

	@Override
	public boolean yRange0To1() {
		return setYRange0To1;
	}

	@Override
	public String graphTitle() {
		return graphTitle;
	}

	@Override
	public String xAxisLabel() {
		return xAxisLabel;
	}

	@Override
	public String yAxisLabel() {
		return yAxisLabel;
	}

	@Override
	public XYDataset dataset() {
		return dataset;
	}

	@Override
	public String windowTitle() {
		return windowTitle;
	}

	@Override
	public GraphBuilder withRenderer(final XYLineAndShapeRenderer renderer) {
		this.renderer = renderer;
		return this;
	}

}
