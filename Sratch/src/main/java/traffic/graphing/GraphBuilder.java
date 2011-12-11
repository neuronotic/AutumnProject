package traffic.graphing;

import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;


public interface GraphBuilder {

	public GraphBuilder withWindowTitle(String windowTitle);
	public GraphBuilder withGraphTitle(String graphTitle);
	public GraphBuilder withXAxisLabel(String xAxisLabel);
	public GraphBuilder withYAxisLabel(String yAxisLabel);
	public GraphBuilder withYRange0to1();
	public GraphBuilder withDataset(XYDataset dataset);
	public GraphBuilder withRenderer(XYLineAndShapeRenderer renderer);

	public MyGraph make();
	public boolean yRange0To1();
	public String graphTitle();
	public String windowTitle();
	public String xAxisLabel();
	public String yAxisLabel();
	public XYDataset dataset();
}
