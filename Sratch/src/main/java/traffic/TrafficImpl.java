package traffic;

import static java.util.Arrays.*;
import static traffic.SimulationTime.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import traffic.graphing.GraphBuilderImpl;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class TrafficImpl implements Traffic {
	private final SimulationBuilder simulationBuilder;
	private final DefaultNetworks defaultNetworks;

	@Inject private Provider<FlowGroupBuilder> flowGroupBuilderProvider;
	@Inject private Provider<FlowBuilder> flowBuilderProvider;
	@Inject private Provider<EquisaturationBuilder> equisaturationBuilderProvider;
	@Inject private Provider<DutyCycleBuilder> periodicDutyCycleBuilderProvider;
	@Inject private Provider<FirstComeFirstServedBuilder> firstComeBuilderProvider;
	private final TimeKeeper timeKeeper;

	@Inject public TrafficImpl(final SimulationBuilder simulationBuilder, final DefaultNetworks defaultNetworks, final TimeKeeper timeKeeper) {
		this.simulationBuilder = simulationBuilder;
		this.defaultNetworks = defaultNetworks;
		this.timeKeeper = timeKeeper;
	}

	@Override
	public void start(final String[] args) {

		TemporalPattern temporalPattern;
		temporalPattern = new ConstantTemporalPattern(1);
		temporalPattern = new MaskedBinaryTemporalPattern(timeKeeper, asList(1,1,0,0,0));
		//temporalPattern = new SinusoidalTemporalPattern(timeKeeper, time(500));

		final double flow1probability = 1;
		final double flow2probability = 0.0;
		final int fluxWindowSize = 10;
		final int linkLength = 5;

		JunctionControllerBuilder controllerBuilder;
		controllerBuilder = firstComeBuilderProvider.get();
		//controllerBuilder = equisaturationBuilderProvider.get().withPeriod(time(5));
		controllerBuilder = periodicDutyCycleBuilderProvider.get().withPeriod(time(5));

		//final Network network = defaultNetworks.yNetwork3Link(junctionController);
		//final Network network = defaultNetworks.vNetwork2Link(controllerBuilder, linkLength);
		final Network network = defaultNetworks.crossedDiamond(controllerBuilder, linkLength);

		final Simulation sim = simulationBuilder
			.withNetwork(network)
			.withFlowGroup(flowGroupBuilderProvider.get()
				.withTemporalPattern(temporalPattern)
				.withFlow(flowBuilderProvider.get()
						.withRoute(network, "link0", "link1")
						.withItinerary(new ItineraryImpl(network.linkNamed("link0")))//, network.linkNamed("link1")))
						.withProbability(flow1probability))
				.withFlow(flowBuilderProvider.get()
						.withItinerary(new ItineraryImpl(network.linkNamed("link1")))//, network.linkNamed("link3")))
						.withProbability(flow2probability)) )
			.make();

		for (final Link link : network.links()) {
			link.headCell().recordFlux();
		}

		sim.step(20);

		congestionGraph(flow1probability, flow2probability, controllerBuilder.name(), sim);
		journeyTimeGraph(flow1probability, flow2probability, controllerBuilder.name(), sim);

		fluxGraph(flow1probability, flow2probability, controllerBuilder.name(), sim, fluxWindowSize);

	}

	private void fluxGraph(final double flow1probability,
			final double flow2probability,
			final String controllerName,
			final Simulation sim,
			final int fluxWindowSize) {
		new GraphBuilderImpl()
		.withWindowTitle("Flux")
		.withGraphTitle(String.format("Flux on links with %s controller (%s, %s)", controllerName, flow1probability, flow2probability))
		.withXAxisLabel("time")
		.withYAxisLabel("flux")
		.withYRange0to1()
		.withDataset(createFluxDataset(sim, fluxWindowSize))
		.withRenderer(lineRenderer())
		.make()
		.makeVisible()
		.saveToFile(String.format("flux-%s", controllerName));
	}

	private XYDataset createFluxDataset(final Simulation sim, final int window) {
		final XYSeriesCollection dataset = new XYSeriesCollection();
		final StatisticsManager statistics = sim.statistics();
		for (final Link link : sim.network().links()) {

			dataset.addSeries(generateFluxSeries(statistics.fluxTimes(link.headCell()), link.name(), sim.time().value(), window));
		}
		return dataset;
	}

	private XYSeries generateFluxSeries(final List<SimulationTime> fluxTimes, final String name, final int numberOfTimesteps, final int windowSize) {
		final XYSeries series = new XYSeries(name);
		if (fluxTimes != null) {
			final int[] binarySeries = createBinaryFluxSeries(fluxTimes, numberOfTimesteps);

			for (int i=windowSize-1; i<numberOfTimesteps; i++) {
				int totalFlux = 0;
				for (int j=0; j<windowSize; j++) {
					totalFlux += binarySeries[i-j];
				}
				series.add(i, totalFlux / (double)windowSize);
			}
		}
		return series;
	}

	private int[] createBinaryFluxSeries(final List<SimulationTime> fluxTimes, final int numberOfTimesteps) {
		final int[] series = new int[numberOfTimesteps];
		for (final SimulationTime time : fluxTimes) {
			series[time.value()] = 1;
		}
		return series;
	}

	private void congestionGraph(final double flow1probability,
			final double flow2probability,
			final String controllerName,
			final Simulation sim) {
		new GraphBuilderImpl()
			.withWindowTitle("Congestion")
			.withGraphTitle(String.format("Congestion on links with %s controller (%s, %s)", controllerName, flow1probability, flow2probability))
			.withXAxisLabel("time")
			.withYAxisLabel("congestion")
			.withYRange0to1()
			.withDataset(createLinkCongestionDataSeries(sim))
			.withRenderer(lineRenderer())

			.make()
			.makeVisible();
			//.saveToFile(String.format("congestion-%s", controllerName));
	}

	private void journeyTimeGraph(final double flow1probability,
			final double flow2probability,
			final String controllerName,
			final Simulation sim) {
		new GraphBuilderImpl()
			.withWindowTitle("Journey Times")
			.withGraphTitle(String.format("Journey times along routes, with %s controller (%s, %s)", controllerName, flow1probability, flow2probability))
			.withXAxisLabel("Simulation time at which journey ended")
			.withYAxisLabel("journey duration")
			.withRenderer(journeyTimeRenderer())
			.withDataset(createJourneyTimeDataSeries(sim))
			.make()
			.makeVisible();
			//.saveToFile(String.format("journeytime-%s", controllerName));
	}

	private double averageJourneyTime(final Simulation sim) {
		final Collection<JourneyHistory> journeyHistories = sim.statistics().getEndedJourneyHistories();
		int cumulativeJourneyTime = 0;
		for (final JourneyHistory journeyHistory : journeyHistories) {
			cumulativeJourneyTime += journeyHistory.journeyDuration().value();
		}
		return cumulativeJourneyTime / (double)journeyHistories.size();
	}

	private XYLineAndShapeRenderer lineRenderer() {
		final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, true);
        renderer.setSeriesShapesVisible(0, false);
        renderer.setSeriesLinesVisible(1, true);
        renderer.setSeriesShapesVisible(1, false);
        return renderer;
	}

	private XYLineAndShapeRenderer journeyTimeRenderer() {
		final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, false);
        renderer.setSeriesShapesVisible(0, true);
        renderer.setSeriesLinesVisible(1, false);
        renderer.setSeriesShapesVisible(1, true);
        renderer.setSeriesLinesVisible(2, true);
        renderer.setSeriesShapesVisible(2, false);
        return renderer;
	}

	private XYSeriesCollection createJourneyTimeDataSeries(final Simulation simulation) {
		final List<JourneyHistory> journeyHistories = simulation.statistics().getEndedJourneyHistories();
		final XYSeriesCollection dataset = new XYSeriesCollection();
		XYSeries series;
		String flowName;
		int journeyDuration, endTime;
		final Map<String, XYSeries> seriesMap = new HashMap<String, XYSeries>();
		for (final JourneyHistory history : journeyHistories) {
			flowName = history.flow().name();
			if (! seriesMap.containsKey(flowName)) {
				series = new XYSeries(flowName);
				seriesMap.put(flowName, series);
				dataset.addSeries(series);
			}
			journeyDuration = history.journeyDuration().value();
			endTime = history.endTime().value();
			seriesMap.get(flowName).add(endTime, journeyDuration);
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

	private XYDataset createLinkCongestionDataSeries(final Simulation simulation) {

		final Collection<Link> links = simulation.network().links();
		final NetworkOccupancyTimeSeries occupancyTimeSeries = simulation.statistics().networkOccupancy();

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

}
