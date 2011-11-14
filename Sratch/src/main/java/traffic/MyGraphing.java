package traffic;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

public class MyGraphing extends ApplicationFrame {


	MyGraphing(final String title, final Network network, final NetworkOccupancyTimeSeries networkOccupancyTimeSeries) {
		super(title);
		final XYSeriesCollection dataset = createLinkCongestionDataSeries(network.links(), networkOccupancyTimeSeries);
		final JFreeChart chart = createChart(dataset);
	    final ChartPanel chartPanel = new ChartPanel(chart);
	    chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
	    setContentPane(chartPanel);

	}

	private XYSeriesCollection createLinkCongestionDataSeries(final Collection<Link> links,
			final NetworkOccupancyTimeSeries occupancyTimeSeries) {
		final XYSeriesCollection dataset = new XYSeriesCollection();
		XYSeries series;
		int time = 0;
		for (final Link link : links) {
			series = new XYSeries(link.name());
			time = 0;
			for (final Double linkCongestionValue : linkCongestionTimeSeries(link, occupancyTimeSeries)) {
				series.add(time++, linkCongestionValue);
			}
			dataset.addSeries(series);
		}
		return dataset;
	}

	private XYSeriesCollection createJunctionCongestionDataSeries(final Collection<Junction> junctions,
			final NetworkOccupancyTimeSeries occupancyTimeSeries) {
		final XYSeriesCollection dataset = new XYSeriesCollection();
		XYSeries series;
		int time = 0;
		for (final Junction junction : junctions) {
			series = new XYSeries(junction.name());
			time = 0;
			for (final Double junctionCongestionValue : junctionCongestionTimeSeries(junction, occupancyTimeSeries)) {
				series.add(time++, junctionCongestionValue);
			}
			dataset.addSeries(series);
		}
		return dataset;
	}



	private List<Double> linkCongestionTimeSeries(final Link link,
			final NetworkOccupancyTimeSeries networkOccupancyTimeSeries) {
		final List<Double> congestions = new ArrayList<Double>();
		for (final NetworkOccupancy networkOccupancy : networkOccupancyTimeSeries.networkOccupancies()) {
			final LinkOccupancy linkOccupancy = networkOccupancy.occupancyFor(link);
			congestions.add(Double.valueOf((double) linkOccupancy.occupancy()/ (double) linkOccupancy.capacity()));
		}
		return congestions;
	}

	private List<Double> junctionCongestionTimeSeries(final Junction junction,
			final NetworkOccupancyTimeSeries networkOccupancyTimeSeries) {
		final List<Double> congestions = new ArrayList<Double>();
		for (final NetworkOccupancy networkOccupancy : networkOccupancyTimeSeries.networkOccupancies()) {
			final JunctionOccupancy junctionOccupancy = networkOccupancy.occupancyFor(junction);
			congestions.add(Double.valueOf((double) junctionOccupancy.totalOccupancy()/ (double) junctionOccupancy.totalCapacity()));
		}
		return congestions;
	}


	private JFreeChart createChart(final XYDataset dataset) {

        // create the chart...
        final JFreeChart chart = ChartFactory.createXYLineChart(
            "Congestion on X network",      // chart title
            "time",                      // x axis label
            "congestion",                      // y axis label
            dataset,                  // data
            PlotOrientation.VERTICAL,
            true,                     // include legend
            true,                     // tooltips
            false                     // urls
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
        chart.setBackgroundPaint(Color.white);

        // final StandardLegend legend = (StandardLegend) chart.getLegend();
        //legend.setDisplaySeriesShapes(true);

        // get a reference to the plot for further customisation...
        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.lightGray);
        //plot.setAxisOffset(new Spacer(Spacer.ABSOLUTE, 5.0, 5.0, 5.0, 5.0));
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);

        final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, false);
        renderer.setSeriesShapesVisible(1, false);
        plot.setRenderer(renderer);

        // change the auto tick unit selection to integer units only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        // OPTIONAL CUSTOMISATION COMPLETED.

        return chart;

    }
}
