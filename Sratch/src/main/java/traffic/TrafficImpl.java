package traffic;

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
		//temporalPattern = new MaskedBinaryTemporalPattern(timeKeeper, asList(1,1,1,1,0));
		//temporalPattern = new SinusoidalTemporalPattern(timeKeeper, time(1500));


		JunctionControllerBuilder controllerBuilder;
		controllerBuilder = firstComeBuilderProvider.get();
		controllerBuilder = equisaturationBuilderProvider.get().withPeriod(time(25)).withSwitchingDelay(time(5));
		//controllerBuilder = periodicDutyCycleBuilderProvider.get().withPeriod(time(25)).withSwitchingDelay(time(5));


		final XYSeries series = new XYSeries("something");
		final XYSeriesCollection dataset = new XYSeriesCollection(series);
		for (double p=0.0; p<1; p+= 0.05) {
			final double averageJourneyTime = simulate(temporalPattern, p, controllerBuilder, 50, 5000);
			series.add(p, averageJourneyTime);
		}
		new GraphBuilderImpl()
		.withWindowTitle("Journey Times")
		.withGraphTitle("Journey time as a function of p")
		.withXAxisLabel("p")
		.withYAxisLabel("journey duration")
		//.withRenderer(journeyTimeRenderer())
		.withDataset(dataset)
		.make()
		.makeVisible();

		//simRangeOfParameters(temporalPattern, controllerBuilder);

	}

	private double simulate(final TemporalPattern temporalPattern,
		final double flowProbability,
		final JunctionControllerBuilder controllerBuilder,
		final int linkLength,
		final int simDuration) {
		//final Network network = defaultNetworks.yNetwork3Link(junctionController);
		//final Network network = defaultNetworks.vNetwork2Link(controllerBuilder, linkLength);
		final Network network = defaultNetworks.crossedDiamond(controllerBuilder, linkLength);
		//final Network network = defaultNetworks.singleLink(controllerBuilder, linkLength);

		final Simulation sim = simulationBuilder
			.withNetwork(network)
			.withFlowGroup(flowGroupBuilderProvider.get()
				.withTemporalPattern(temporalPattern)
				.withFlow(flowBuilderProvider.get()
						.withRouteSpecifiedByLinkNames(network, "link0", "link2")
						.withProbability(1 * flowProbability))
				.withFlow(flowBuilderProvider.get()
						.withRouteSpecifiedByLinkNames(network, "link1", "link4")
						.withProbability(1 * flowProbability))
				.withFlow(flowBuilderProvider.get()
						.withRouteSpecifiedByLinkNames(network, "link1", "link3", "link2")
						.withProbability(1 * flowProbability)) )
//				.withFlow(flowBuilderProvider.get()
//						.withRouteSpecifiedByLinkNames(network, "link0")
//						.withProbability(flowProbability))
//				.withFlow(flowBuilderProvider.get()
//						.withRouteSpecifiedByLinkNames(network, "link1")
//						.withProbability(0.3 * flowProbability)) )
			.make();

		for (final Link link : network.links()) {
			link.headCell().recordFlux();
		}

		sim.step(simDuration);
		return averageJourneyTime(sim);
//		final String title = new StringBuffer()
//			.append(String.format("(%.2f)", flowProbability))
//			.append(String.format("(jt=%.1fts)", averageJourneyTime(sim)))
//			.append(String.format("(l=%d)", linkLength))
//			.append(controllerBuilder.name())
//			.toString();
		//congestionGraph(title, sim);
		//journeyTimeGraph(title, sim);

		//fluxGraph(title, sim, fluxWindowSize);
	}


	private void simRangeOfParameters(final TemporalPattern temporalPattern,
			JunctionControllerBuilder controllerBuilder, final int simDuration) {
		for (int linkLength=10;linkLength<100; linkLength+=10) {
			simulate(temporalPattern, 0.2, controllerBuilder, linkLength, simDuration);
			for (int i=3; i<8; i++) {
				final double flowProbability = i * 0.05 ;
				//simulate(temporalPattern, flowProbability, controllerBuilder, linkLength);
			}
		}

		controllerBuilder = equisaturationBuilderProvider.get().withPeriod(time(25));
		for (int linkLength=10;linkLength<100; linkLength+=10) {
			for (int i=3; i<8; i++) {
				final double flowProbability = i * 0.05 ;
				simulate(temporalPattern, flowProbability, controllerBuilder, linkLength, simDuration);
			}
		}

		controllerBuilder = periodicDutyCycleBuilderProvider.get().withPeriod(time(25));
		for (int linkLength=10;linkLength<100; linkLength+=10) {
			for (int i=3; i<8; i++) {
				final double flowProbability = i * 0.05 ;
				simulate(temporalPattern, flowProbability, controllerBuilder, linkLength, simDuration);
			}
		}
	}



	private void fluxGraph(final String title,
			final Simulation sim,
			final int fluxWindowSize) {
		new GraphBuilderImpl()
		.withWindowTitle("Flux")
		.withGraphTitle(String.format("Flux on links - %s", title))
		.withXAxisLabel("time")
		.withYAxisLabel("flux")
		.withYRange0to1()
		.withDataset(createFluxDataset(sim, fluxWindowSize))
		.withRenderer(lineRenderer())
		.make()
		//.makeVisible()
		.saveToFile(String.format("flux-%s", title));
	}

	private void congestionGraph(
			final String title,
			final Simulation sim) {
		new GraphBuilderImpl()
			.withWindowTitle("Congestion")
			.withGraphTitle(String.format("Congestion on links - %s", title))
			.withXAxisLabel("time")
			.withYAxisLabel("congestion")
			.withYRange0to1()
			.withDataset(createLinkCongestionDataSeries(sim))
			.withRenderer(lineRenderer())

			.make()
			.makeVisible();
			//.saveToFile(String.format("congestion-%s", title));
	}

	private void journeyTimeGraph(
			final String title,
			final Simulation sim) {
		new GraphBuilderImpl()
			.withWindowTitle("Journey Times")
			.withGraphTitle(String.format("Journey times along routes - %s", title))
			.withXAxisLabel("Simulation time at which journey ended")
			.withYAxisLabel("journey duration")
			.withRenderer(journeyTimeRenderer())
			.withDataset(createJourneyTimeDataSeries(sim))
			.make()
			.makeVisible();
			//.saveToFile(String.format("journeytime-%s", title));
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

        renderer.setSeriesLinesVisible(2, true);
        renderer.setSeriesShapesVisible(2, false);
        renderer.setSeriesLinesVisible(3, true);
        renderer.setSeriesShapesVisible(3, false);
        renderer.setSeriesLinesVisible(4, true);
        renderer.setSeriesShapesVisible(4, false);
        return renderer;
	}

	private XYLineAndShapeRenderer journeyTimeRenderer() {
		final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, false);
        renderer.setSeriesShapesVisible(0, true);
        renderer.setSeriesLinesVisible(1, false);
        renderer.setSeriesShapesVisible(1, true);
        renderer.setSeriesLinesVisible(2, false);
        renderer.setSeriesShapesVisible(2, true);
        renderer.setSeriesLinesVisible(3, true);
        renderer.setSeriesShapesVisible(3, false);
//      renderer.setSeriesLinesVisible(2, true);
//      renderer.setSeriesShapesVisible(2, false);
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
