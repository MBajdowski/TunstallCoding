package Gui.view;

import FileAnalysis.HistogramData;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.Collection;

public class HistogramGraphBuilder {

    public static BarChart<String, Number> createGraph(final Collection<HistogramData> histogramDataList) {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> barChart =
                new BarChart<>(xAxis, yAxis);
        barChart.setCategoryGap(0);
        barChart.setBarGap(0);

        xAxis.setLabel("Wartość");
        yAxis.setLabel("Liczba wystąpień");

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Histogram");
        for (HistogramData histogramData : histogramDataList) {
            series1.getData().add(new XYChart.Data(String.valueOf(histogramData.getX()), histogramData.getY()));
        }
        barChart.getData().addAll(series1);
        barChart.setLegendVisible(false);

        return barChart;
    }
}
