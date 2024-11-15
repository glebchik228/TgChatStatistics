package org.example;

import org.knowm.xchart.*;
import org.knowm.xchart.internal.chartpart.Chart;
import org.knowm.xchart.style.PieStyler;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.markers.SeriesMarkers;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class ChartGenerator {
    private final Analyzer analyzer;

    public ChartGenerator(Analyzer analyzer) {
        this.analyzer = analyzer;
    }

    public void generateCharts(String outputPath) throws IOException {
        saveChart(createMessagesPerUserChart(), Path.of(outputPath, "messages_per_user.png"));
        saveChart(createMessagesPerDayChart(), Path.of(outputPath, "messages_per_day.png"));
        saveChart(createMessagesPerHourChart(), Path.of(outputPath, "messages_per_hour.png"));
        saveChart(createMessagesPerDayOfWeekChart(), Path.of(outputPath, "messages_per_day_of_week.png"));
    }

    private void saveChart(Chart chart, Path path) throws IOException {
        BitmapEncoder.saveBitmap(chart, path.toString(), BitmapEncoder.BitmapFormat.PNG);
    }

    private PieChart createMessagesPerUserChart() {
        PieChart chart = new PieChartBuilder()
                .width(1700)
                .height(1000)
                .title("Messages per User")
                .theme(Styler.ChartTheme.Matlab)
                .build();

        chart.getStyler().setLegendVisible(false);
        chart.getStyler().setAnnotationType(PieStyler.AnnotationType.Label);
        chart.getStyler().setAnnotationDistance(1.1);
        chart.getStyler().setPlotContentSize(.9);
        chart.getStyler().setDefaultSeriesRenderStyle(PieSeries.PieSeriesRenderStyle.Donut);
        chart.getStyler().setChartBackgroundColor(Color.BLACK);
        chart.getStyler().setPlotBackgroundColor(Color.BLACK);
        chart.getStyler().setChartFontColor(Color.WHITE);


        chart.getStyler().setSeriesColors(new Color[]{
                new Color(135, 206, 250), // Light Blue
                new Color(255, 182, 193), // Light Pink
                new Color(144, 238, 144), // Light Green
                new Color(238, 130, 238), // Violet
                new Color(255, 160, 122), // Light Salmon
                new Color(255, 222, 173)  // Light Goldenrod
        });

        try {
            Font emojiFont = Font.createFont(Font.TRUETYPE_FONT, new File("output/ready-html/fonts/raleway-v22-cyrillic-300.ttf"));
            chart.getStyler().setAnnotationsFont(emojiFont.deriveFont(Font.BOLD, 30));
            chart.getStyler().setChartTitleFont(emojiFont.deriveFont(Font.BOLD, 30));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        chart.getStyler().setAnnotationsFontColor(Color.WHITE);
        Map<String, Integer> messagesPerUser = analyzer.getCountOfMessagesPerUser();
        messagesPerUser.forEach(chart::addSeries);
        return chart;
    }


    private XYChart createMessagesPerDayChart() {
        XYChart chart = new XYChartBuilder()
                .width(1700)
                .height(1000)
                .title("Messages per Day")
                .xAxisTitle("Date")
                .yAxisTitle("Message Count")
                .theme(Styler.ChartTheme.Matlab)
                .build();

        chart.getStyler().setLegendVisible(false);
        chart.getStyler().setAxisTitlesVisible(false);
        chart.getStyler().setChartBackgroundColor(Color.BLACK);
        chart.getStyler().setPlotBackgroundColor(Color.BLACK);
        chart.getStyler().setChartFontColor(Color.WHITE);
        chart.getStyler().setAxisTickLabelsColor(Color.WHITE);
        try {
            Font emojiFont = Font.createFont(Font.TRUETYPE_FONT, new File("output/ready-html/fonts/raleway-v22-cyrillic-300.ttf"));
            chart.getStyler().setChartTitleFont(emojiFont.deriveFont(Font.BOLD, 30));
            chart.getStyler().setAxisTickLabelsFont(emojiFont.deriveFont(Font.BOLD, 17));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Scatter);
        chart.getStyler().setPlotGridLinesVisible(false);

        Map<LocalDate, Integer> messagesPerDay = analyzer.getCountOfMessagesPerDay();

        List<Date> dates = messagesPerDay.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> Date.from(entry.getKey().atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .collect(Collectors.toList());

        List<Integer> counts = messagesPerDay.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());

        chart.addSeries("Messages", dates, counts)
                .setMarker(SeriesMarkers.CIRCLE)
                .setMarkerColor(Color.CYAN);

        return chart;
    }


    private CategoryChart createMessagesPerHourChart() {
        CategoryChart chart = new CategoryChartBuilder()
                .width(1700)
                .height(1000)
                .title("Messages per Hour")
                .xAxisTitle("Hour")
                .yAxisTitle("Message Count")
                .theme(Styler.ChartTheme.Matlab)
                .build();

        chart.getStyler().setLegendVisible(false);
        chart.getStyler().setAxisTitlesVisible(false);
        chart.getStyler().setChartBackgroundColor(Color.BLACK);
        chart.getStyler().setPlotBackgroundColor(Color.BLACK);
        chart.getStyler().setChartFontColor(Color.WHITE);
        chart.getStyler().setAxisTickLabelsColor(Color.WHITE);
        chart.getStyler().setPlotGridLinesVisible(false);
        try {
            Font emojiFont = Font.createFont(Font.TRUETYPE_FONT, new File("output/ready-html/fonts/raleway-v22-cyrillic-300.ttf"));
            chart.getStyler().setChartTitleFont(emojiFont.deriveFont(Font.BOLD, 30));
            chart.getStyler().setAxisTickLabelsFont(emojiFont.deriveFont(Font.BOLD, 17));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        chart.getStyler().setSeriesColors(new Color[]{Color.CYAN});
        chart.getStyler().setAvailableSpaceFill(0.8);
        chart.getStyler().setOverlapped(false);

        Map<Integer, Integer> messagesPerHour = analyzer.getCountOfMessagesPerHour();

        List<Integer> hours = messagesPerHour.keySet().stream()
                .sorted()
                .collect(Collectors.toList());
        List<Integer> counts = hours.stream()
                .map(messagesPerHour::get)
                .collect(Collectors.toList());

        chart.addSeries("Messages", hours, counts);
        return chart;
    }

    private CategoryChart createMessagesPerDayOfWeekChart() {
        CategoryChart chart = new CategoryChartBuilder()
                .width(1000)
                .height(588)
                .title("Messages per day of week")
                .xAxisTitle("Day")
                .yAxisTitle("Message Count")
                .theme(Styler.ChartTheme.Matlab)
                .build();

        chart.getStyler().setLegendVisible(false);
        chart.getStyler().setAxisTitlesVisible(false);
        chart.getStyler().setChartBackgroundColor(Color.BLACK);
        chart.getStyler().setPlotBackgroundColor(Color.BLACK);
        chart.getStyler().setChartFontColor(Color.WHITE);
        chart.getStyler().setAxisTickLabelsColor(Color.WHITE);
        chart.getStyler().setPlotGridLinesVisible(false);
        try {
            Font emojiFont = Font.createFont(Font.TRUETYPE_FONT, new File("output/ready-html/fonts/raleway-v22-cyrillic-300.ttf"));
            chart.getStyler().setChartTitleFont(emojiFont.deriveFont(Font.BOLD, 30));
            chart.getStyler().setAxisTickLabelsFont(emojiFont.deriveFont(Font.BOLD, 17));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        chart.getStyler().setSeriesColors(new Color[]{Color.CYAN});
        chart.getStyler().setAvailableSpaceFill(0.8);
        chart.getStyler().setOverlapped(false);

        Map<DayOfWeek, Integer> messagesPerDayOfWeek = analyzer.getCountOfMessagesPerDayOfWeek();

        List<String> dayLabels = messagesPerDayOfWeek.keySet().stream()
                .sorted()
                .map(day -> day.getDisplayName(TextStyle.FULL, Locale.ENGLISH))
                .toList();
        List<Integer> counts = dayLabels.stream()
                .map(day -> messagesPerDayOfWeek.get(DayOfWeek.valueOf(day.toUpperCase(Locale.ENGLISH))))
                .collect(Collectors.toList());

        chart.addSeries("Messages", dayLabels, counts);
        return chart;
    }
}
