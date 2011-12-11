package traffic.graphing;

import java.awt.Color;
import java.io.File;
import java.util.Calendar;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class MyGraphImpl implements MyGraph {
	private final String FILE_PATH = "/home/daz/output/";
	private static final long serialVersionUID = 1L;
	private final int XDIM = 800;
	private final int YDIM = 250;

	private final ApplicationFrame frame;
	private final JFreeChart chart;

	public MyGraphImpl(final GraphBuilder builder, final XYLineAndShapeRenderer renderer) {
		chart = createChart(builder);
		chart.getXYPlot().setRenderer(renderer);
		frame = createFrame(builder);
	}

	@Override
	public MyGraph makeVisible() {
		frame.setVisible(true);
		return this;
	}


	private ApplicationFrame createFrame(final GraphBuilder builder) {
		final ApplicationFrame frame = new ApplicationFrame(builder.windowTitle());
		final ChartPanel chartPanel = new ChartPanel(chart);
	    chartPanel.setPreferredSize(new java.awt.Dimension(XDIM, YDIM));
	    frame.setContentPane(chartPanel);
	    frame.pack();
		RefineryUtilities.positionFrameRandomly(frame);
        return frame;
	}

	private JFreeChart createChart(final GraphBuilder builder) {
		final JFreeChart chart = ChartFactory.createXYLineChart(
		    builder.graphTitle(),
		    builder.xAxisLabel(),
		    builder.yAxisLabel(),
		    builder.dataset(),
		    PlotOrientation.VERTICAL,
		    true,                     // include legend
		    true,                     // tooltips
		    false                     // urls
		);

		chart.setBackgroundPaint(Color.white);
		final XYPlot plot = chart.getXYPlot();
		plot.setBackgroundPaint(Color.lightGray);
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);

		if (builder.yRange0To1()) {
			chart.getXYPlot().getRangeAxis().setRange(0.0, 1.0);
		}

		return chart;
	}

	@Override
	public MyGraph saveToFile(final String fileName) {
		final Calendar calendar = Calendar.getInstance();
        final String fileLocation = new StringBuffer(FILE_PATH)
        	.append(fileName)
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
        return this;
	}

}
