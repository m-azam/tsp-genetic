import entities.Generation;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

import java.util.ArrayList;

public class DistanceGraph extends ApplicationFrame {

    public DistanceGraph(ArrayList<Generation> generations) {
        super("Graph");
        JFreeChart lineChart = ChartFactory.createLineChart(
                "Distance graph",
                "Generation", "Distance",
                createDataset(generations),
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        setContentPane(chartPanel);
    }

    private DefaultCategoryDataset createDataset(ArrayList<Generation> generations) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < generations.size(); i++) {
            dataset.addValue(generations.get(i).getBestRoute().getTotalDistance(), "distance", (i + 1) + "");
        }

        return dataset;
    }
}