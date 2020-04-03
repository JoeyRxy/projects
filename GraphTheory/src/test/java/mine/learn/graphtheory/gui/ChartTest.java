package mine.learn.graphtheory.gui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.junit.Test;

import mine.learn.graphtheory.bean.WeightedDirectedEdge;

/**
 * ChartTest
 */
public class ChartTest {

    @Test
    public void testJFree() throws IOException {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(1, "rowKey", "a");
        dataset.addValue(2, "rowKey", "b");
        dataset.addValue(3, "rowKey", "c");
        dataset.addValue(4, "rowKey", "d");
        dataset.addValue(5, "rowKey", "e");
        dataset.addValue(6, "rowKey", "f");
        JFreeChart chart = ChartFactory.createLineChart("title", "categoryAxisLabel", "valueAxisLabel", dataset);
        ChartUtils.saveChartAsJPEG(new File("chart.jpeg"), chart, 1000, 1000);
    }

    @Test
    public void testJSON() {
        JSONObject json = new JSONObject();
        WeightedDirectedEdge e = new WeightedDirectedEdge(1, 3, 10.6);
        List<WeightedDirectedEdge> list = new ArrayList<>(2);
        list.add(new WeightedDirectedEdge(2, 3, 66.6));
        list.add(e);
        json.put("edge", list);
        System.out.println(json.toJSONString());
    }
}