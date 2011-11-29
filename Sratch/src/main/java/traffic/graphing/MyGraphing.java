package traffic.graphing;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import traffic.JourneyHistory;
import traffic.JunctionController;
import traffic.Link;
import traffic.LinkOccupancy;
import traffic.Network;
import traffic.NetworkOccupancy;
import traffic.NetworkOccupancyTimeSeries;

import com.google.inject.Inject;

public class MyGraphing extends ApplicationFrame {
	@Inject Logger logger = Logger.getAnonymousLogger();
	private static final long serialVersionUID = 1L;
	private final int XDIM = 800;
	private final int YDIM = 250;
	String location = "/home/daz/output/";
	String congestionLocation = "/home/daz/output/congestion.jpg";

	public MyGraphing(final String title, final Network network, final NetworkOccupancyTimeSeries networkOccupancyTimeSeries, final JunctionController junctionController, final double flow1probability, final double flow2probability) {
		super(title);
		final XYSeriesCollection dataset = createLinkCongestionDataSeries(network.links(), networkOccupancyTimeSeries);
		final String chartTitle = String.format("Congestion on links - %s - (%s, %s)", junctionController, flow1probability, flow2probability);
		final JFreeChart chart = createChartWithRange1(chartTitle, "time", "congestion", dataset);

		saveGraphToFile(junctionController,
				flow1probability, flow2probability,
				chart, "congestion");

	    makeGraphVisible(chart);

	}

	public MyGraphing(final String title, final List<JourneyHistory> journeyHistories, final JunctionController junctionController, final double flow1probability, final double flow2probability) {
		super(title);
		final XYSeriesCollection dataset = createJourneyTimeDataSeries(journeyHistories);
		final String chartTitle = String.format("Journey durations - %s - (%s, %s)", junctionController, flow1probability, flow2probability);
		final JFreeChart chart = createChart(chartTitle, "Simulation time at which journey ended", "duration", dataset);

		final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, false);
        renderer.setSeriesShapesVisible(0, true);
        renderer.setSeriesLinesVisible(1, false);
        renderer.setSeriesShapesVisible(1, true);
        renderer.setSeriesLinesVisible(2, true);
        renderer.setSeriesShapesVisible(2, false);
		chart.getXYPlot().setRenderer(renderer);

		saveGraphToFile(junctionController,
				flow1probability, flow2probability,
				chart, "Journey_Durations");

		makeGraphVisible(chart);

	}

	private void saveGraphToFile(final JunctionController junctionController,
			final double flow1probability, final double flow2probability,
			final JFreeChart chart, final String fileDifferentiator) {

		final Calendar calendar = Calendar.getInstance();
        final String fileLocation = new StringBuffer(location)
        	.append(junctionController.toString())
        	//.append(String.format("(%s, %s)", myFormatter.format(flow1probability), myFormatter.format(flow2probability)))
        	.append("-")
        	.append(fileDifferentiator)
        	.append(String.format("-%d%s%s-%s%s%s",
        			calendar.get(Calendar.YEAR),
        			String.format("%02d", calendar.get(Calendar.MONTH)),
        			String.format("%02d", calendar.get(Calendar.DATE)),
        			String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY)),
        			String.format("%02d", calendar.get(Calendar.MINUTE)),
        			String.format("%02d", calendar.get(Calendar.SECOND))))
        	.append(".jpg").toString();
        try {
        	ChartUtilities.saveChartAsJPEG(new File(fileLocation), chart, XDIM, YDIM);
        } catch (final Exception e) {
        	System.out.println("error making chart");
        	e.printStackTrace();
        }
	}

	private void makeGraphVisible(final JFreeChart chart) {
		final ChartPanel chartPanel = new ChartPanel(chart);
	    chartPanel.setPreferredSize(new java.awt.Dimension(XDIM, YDIM));
	    setContentPane(chartPanel);
	    pack();
		RefineryUtilities.positionFrameRandomly(this);
        setVisible(true);
	}

	private XYSeriesCollection createJourneyTimeDataSeries(
			final List<JourneyHistory> journeyHistories) {
		final XYSeriesCollection dataset = new XYSeriesCollection();
		XYSeries series;
		String name;
		int duration, endTime;
		final Map<String, XYSeries> seriesMap = new HashMap<String, XYSeries>();
		for (final JourneyHistory history : journeyHistories) {
			name = history.flow().name();
			if (! seriesMap.containsKey(name)) {
				series = new XYSeries(name);
				seriesMap.put(name, series);
				dataset.addSeries(series);
			}
			duration = history.journeyDuration().value();
			endTime = history.endTime().value();

			seriesMap.get(name).add(endTime, duration);
		}


		final int window = 20;
		dataset.addSeries(averageWindow(journeyHistories, window));

		return dataset;
	}

	private XYSeries averageWindow(final List<JourneyHistory> journeyHistories,
			final int window) {
		final XYSeries averageJourneyTime = new XYSeries(String.format("average journey time (n=%d)", window));
		for (int i=0; i<journeyHistories.size(); i++) {
			if (window<i) {
				double averageDuration = 0;
				for (int w=0;w<window;w++) {
					averageDuration += journeyHistories.get(i-w).journeyDuration().value();
				}
				averageDuration /= window;
				averageJourneyTime.add(journeyHistories.get(i).endTime().value(), averageDuration);
			}
		}
		return averageJourneyTime;
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

	private List<Double> linkCongestionTimeSeries(final Link link,
			final NetworkOccupancyTimeSeries networkOccupancyTimeSeries) {
		final List<Double> congestions = new ArrayList<Double>();
		for (final NetworkOccupancy networkOccupancy : networkOccupancyTimeSeries.networkOccupancies()) {
			final LinkOccupancy linkOccupancy = networkOccupancy.occupancyFor(link);
			congestions.add(Double.valueOf((double) linkOccupancy.occupancy()/ (double) linkOccupancy.capacity()));
		}
		return congestions;
	}

	private JFreeChart createChartWithRange1(final String title, final String xAxisLabel, final String yAxisLabel, final XYDataset dataset) {
		final JFreeChart chart = createChart(title, xAxisLabel, yAxisLabel, dataset);
		chart.getXYPlot().getRangeAxis().setRange(0.0, 1.0);
		return chart;
	}

	private JFreeChart createChart(final String title, final String xAxisLabel, final String yAxisLabel, final XYDataset dataset) {

        // create the chart...
        final JFreeChart chart = ChartFactory.createXYLineChart(
            title, xAxisLabel, yAxisLabel, dataset, PlotOrientation.VERTICAL,
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

        // change the auto tick unit selection to integer units only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        // OPTIONAL CUSTOMISATION COMPLETED.

        return chart;

    }
}
