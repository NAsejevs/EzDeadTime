package ezdeadtime;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.function.LineFunction2D;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.statistics.Regression;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class ScatterPlot extends JPanel {
    XYSeriesCollection dataset = new XYSeriesCollection();
    XYSeries series = new XYSeries("MAP/AFR - PW");
    JFreeChart chart = null;

    ScatterPlot() {
        drawGraph(this);
    }

    private void drawGraph(JPanel container) {
        dataset.addSeries(series);
        chart = ChartFactory.createScatterPlot(
                "Injector Dead Time 2",
                "MAP/AFR", "Injector PW (ms)", dataset, PlotOrientation.HORIZONTAL, true, true, true);
        chart.getXYPlot().getRangeAxis(0).setRange(0, 10);
        chart.getXYPlot().getDomainAxis(0).setRange(0, 10);
        chart.setBackgroundPaint(new Color(0, 0, 0, 0));
        drawRegressionLine(dataset);

        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(new Dimension(680, 420));
        panel.setMouseZoomable(false);

        container.add(panel);
    }

    public void addToDataset(double mapAFR, double pw) {
        series.add(mapAFR, pw);
        drawRegressionLine(dataset);
    }

    private void drawRegressionLine(XYDataset dataset) {
        // Get the parameters 'a' and 'b' for an equation y = a + b * x,
        // fitted to the inputData using ordinary least squares regression.
        // a - regressionParameters[0], b - regressionParameters[1]
        if(series.getItemCount() < 2) { return; }

        double regressionParameters[] = Regression.getOLSRegression(dataset, 0);

        onDeadTimeUpdated(-regressionParameters[0] / regressionParameters[1]);

        // Prepare a line function using the found parameters
        LineFunction2D linefunction2d = new LineFunction2D(
                regressionParameters[0], regressionParameters[1]);

        // Creates a dataset by taking sample values from the line function
        XYDataset lineDataset = DatasetUtilities.sampleFunction2D(linefunction2d,
                0D, 10, series.getItemCount(), "Regression Line");

        // Draw the line dataset
        XYPlot xyplot = chart.getXYPlot();
        xyplot.setDataset(1, lineDataset);
        XYLineAndShapeRenderer xylineandshaperenderer = new XYLineAndShapeRenderer(
                true, false);
        xylineandshaperenderer.setSeriesPaint(0, Color.YELLOW);
        xyplot.setRenderer(1, xylineandshaperenderer);
    }

    private void onDeadTimeUpdated(double deadTime) {
//        DecimalFormat df = new DecimalFormat("#.####");
//        deadTimeText.setText("Estimated Injector Dead Time: " + df.format(deadTime));
    }
}
