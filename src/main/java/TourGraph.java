import entities.City;
import entities.Route;
import entities.World;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

import java.awt.*;
import java.util.ArrayList;

public class TourGraph extends ApplicationFrame {

    public TourGraph(World world, Route route) {
        super("Tour Graph");
        JFreeChart chart = ChartFactory.createXYLineChart("Tour Graph", "X", "Y",
                createDataset(world, route), PlotOrientation.VERTICAL, false, false, false);
        ChartPanel chartPanel = new ChartPanel(chart);
        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.BLUE);
        renderer.setSeriesStroke(0, new BasicStroke(1.0f));
        plot.setRenderer(renderer);
        renderer.setBaseItemLabelsVisible(true);
        chartPanel.setPreferredSize(new java.awt.Dimension(1920, 1080));
        setContentPane(chartPanel);
    }

    public XYDataset createDataset(World world, Route route) {
        ArrayList<City> cities = world.cities;
        XYSeriesCollection dataSet = new XYSeriesCollection();
        XYSeries xySeries = new XYSeries("plot", false);
        for (Integer index : route.getSequence()) {
            xySeries.add(cities.get(index).x, cities.get(index).y);
        }
        xySeries.add(cities.get(route.getSequence().get(0)).x, cities.get(route.getSequence().get(0)).y);
        dataSet.addSeries(xySeries);
        return dataSet;
    }
}
