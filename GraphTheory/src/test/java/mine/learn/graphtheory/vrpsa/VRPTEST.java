package mine.learn.graphtheory.vrpsa;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

import mine.learn.graphtheory.bean.WeightedDirectedEdge;
import mine.learn.graphtheory.util.LogInfo;
import mine.learn.graphtheory.util.VRPFileInfo;
import mine.learn.graphtheory.util.VRPProcessor;
import mine.learn.graphtheory.vrp.VRPSA;

/**
 * VRPTEST
 */
public class VRPTEST {

    @Test
    public void testE200() throws IOException {
        File vrpFile = new File("vrp/E200-17b.dat");
        VRPFileInfo info = VRPProcessor.process(vrpFile);
        int[] depots = new int[info.demands.length];
        for (int i = 0; i < depots.length; i++) {
            depots[i] = i;
        }
        BufferedWriter writer = new BufferedWriter(
                new FileWriter(new File("vrp/VRPSA-" + vrpFile.getName() + System.currentTimeMillis() + ".csv")));
        VRPSA vrpsa = new VRPSA(info.graph, depots, info.demands, info.capacity, 4, 4);
        long start = System.currentTimeMillis();
        vrpsa.calculate(0.993, 10, 1, 100);
        long end = System.currentTimeMillis();
        // writer.write("best dist," + vrpsa.getBestDist());
        writer.write("Test File Info : " + vrpFile.getName() + "\n");
        writer.write("calculating duration," + (end - start) + " ms\n");
        int i = 0;
        List<List<WeightedDirectedEdge>> finalBestPaths = vrpsa.getBestPaths();
        Double[] vehicleLoads = vrpsa.getVehicleLoads();

        writer.write("Best Dist," + vrpsa.getBestDist() + "\n");
        writer.write("Best Path:\n,subRouteDist,subRoute,vehicleLoad\n");
        for (List<WeightedDirectedEdge> subPath : finalBestPaths) {
            writer.write("Route #" + i + "," + VRPSA.helperForSubPathDist(subPath) + ",\"" + subPath + "\","
                    + vehicleLoads[i] + "\n");
            i++;
        }
        writer.write(",\nHistory Best Dist,trucks\n");
        writer.write(info.bestDist + "," + info.trucks);
        writer.flush();
        writer.write("\n,\n,\nCalculating Log in Each Thread\n");
        LogInfo[] logInfos = vrpsa.getLogInfos();

        writer.write("\nMemoMapSize,");
        for (int j = 0; j < logInfos.length; j++) {
            writer.write(logInfos[j].memoSize + ",");
        }
        writer.write("\n,\n");
        int maxLineNum = 0;
        for (int j = 0; j < logInfos.length; j++) {
            int size = logInfos[j].calcData.size();
            if (maxLineNum < size) {
                maxLineNum = size;
            }
        }
        writer.flush();
        writer.write("count");
        for (int j = 0; j < logInfos.length; j++) {
            writer.write(",thread" + j);
        }
        writer.write(",Sum\n");
        double[] cache = new double[logInfos.length];
        for (int j = 0; j < maxLineNum; j++) {
            writer.write(j + ",");
            double s = 0;
            for (int k = 0; k < logInfos.length; k++) {
                if (logInfos[k].calcData.size() > j) {
                    Double x = logInfos[k].calcData.get(j);
                    s += x;
                    writer.write(String.format("%10.5f,", x));
                    cache[k] = x;
                } else {
                    writer.write(String.format("%10.5f,", cache[k]));
                    s += cache[k];
                }
            }
            writer.write(String.format("%10.5f\n", s));
        }
        writer.flush();
        writer.close();
        System.out.println("E200 -- duration : " + (end - start) + "\n");
        System.out.println("Best Dist : " + vrpsa.getBestDist());
        System.out.println("History Best Dist : " + info.bestDist);
    }

    @Test
    public void testE484() throws IOException {
        File vrpFile = new File("vrp/E481-38k.dat");
        VRPFileInfo info = VRPProcessor.process(vrpFile);
        int[] depots = new int[info.demands.length];
        for (int i = 0; i < depots.length; i++) {
            depots[i] = i;
        }
        BufferedWriter writer = new BufferedWriter(
                new FileWriter(new File("vrp/VRPSA-" + vrpFile.getName() + System.currentTimeMillis() + ".csv")));
        VRPSA vrpsa = new VRPSA(info.graph, depots, info.demands, info.capacity, 8);
        long start = System.currentTimeMillis();
        vrpsa.calculate(0.993, 10, 1, 100);
        long end = System.currentTimeMillis();
        // writer.write("best dist," + vrpsa.getBestDist());
        writer.write("Test File Info : " + vrpFile.getName() + "\n");
        writer.write("calculating duration," + (end - start) + " ms\n");
        int i = 0;
        List<List<WeightedDirectedEdge>> finalBestPaths = vrpsa.getBestPaths();
        Double[] vehicleLoads = vrpsa.getVehicleLoads();

        writer.write("Best Dist," + vrpsa.getBestDist() + "\n");
        writer.write("Best Path:\n,subRouteDist,subRoute,vehicleLoad\n");
        for (List<WeightedDirectedEdge> subPath : finalBestPaths) {
            writer.write("Route #" + i + "," + VRPSA.helperForSubPathDist(subPath) + ",\"" + subPath + "\","
                    + vehicleLoads[i] + "\n");
            i++;
        }
        writer.write(",\nHistory Best Dist,trucks\n");
        writer.write(info.bestDist + "," + info.trucks);
        writer.flush();
        writer.write("\n,\n,\nCalculating Log in Each Thread\n");
        LogInfo[] logInfos = vrpsa.getLogInfos();

        writer.write("\nMemoMapSize,");
        for (int j = 0; j < logInfos.length; j++) {
            writer.write(logInfos[j].memoSize + ",");
        }
        writer.write("\n,\n");
        int maxLineNum = 0;
        for (int j = 0; j < logInfos.length; j++) {
            int size = logInfos[j].calcData.size();
            if (maxLineNum < size) {
                maxLineNum = size;
            }
        }
        writer.flush();
        writer.write("count");
        for (int j = 0; j < logInfos.length; j++) {
            writer.write(",thread" + j);
        }
        writer.write(",Sum\n");
        double[] cache = new double[logInfos.length];
        for (int j = 0; j < maxLineNum; j++) {
            writer.write(j + ",");
            double s = 0;
            for (int k = 0; k < logInfos.length; k++) {
                if (logInfos[k].calcData.size() > j) {
                    Double x = logInfos[k].calcData.get(j);
                    s += x;
                    writer.write(String.format("%10.5f,", x));
                    cache[k] = x;
                } else {
                    writer.write(String.format("%10.5f,", cache[k]));
                    s += cache[k];
                }
            }
            writer.write(String.format("%10.5f\n", s));
        }
        writer.flush();
        writer.close();
        System.out.println("E484 -- duration : " + (end - start) + "\n");
        System.out.println("Best Dist : " + vrpsa.getBestDist());
        System.out.println("History Best Dist : " + info.bestDist);
    }

    @Test
    public void testM101() throws IOException {
        File vrpFile = new File("vrp/M-n101-k10.vrp");
        VRPFileInfo info = VRPProcessor.process(vrpFile);
        int[] depots = new int[info.demands.length];
        for (int i = 0; i < depots.length; i++) {
            depots[i] = i;
        }
        BufferedWriter writer = new BufferedWriter(
                new FileWriter(new File("vrp/VRPSA-" + vrpFile.getName() + System.currentTimeMillis() + ".csv")));
        VRPSA vrpsa = new VRPSA(info.graph, depots, info.demands, info.capacity, 8);
        long start = System.currentTimeMillis();
        vrpsa.calculate(0.993, 10, 1, 100);
        long end = System.currentTimeMillis();
        // writer.write("best dist," + vrpsa.getBestDist());
        writer.write("Test File Info : " + vrpFile.getName() + "\n");
        writer.write("calculating duration," + (end - start) + " ms\n");
        int i = 0;
        List<List<WeightedDirectedEdge>> finalBestPaths = vrpsa.getBestPaths();
        Double[] vehicleLoads = vrpsa.getVehicleLoads();

        writer.write("Best Dist," + vrpsa.getBestDist() + "\n");
        writer.write("Best Path:\n,subRouteDist,subRoute,vehicleLoad\n");
        for (List<WeightedDirectedEdge> subPath : finalBestPaths) {
            writer.write("Route #" + i + "," + VRPSA.helperForSubPathDist(subPath) + ",\"" + subPath + "\","
                    + vehicleLoads[i] + "\n");
            i++;
        }
        writer.write(",\nHistory Best Dist,trucks\n");
        writer.write(info.bestDist + "," + info.trucks);
        writer.flush();
        writer.write("\n,\n,\nCalculating Log in Each Thread\n");
        LogInfo[] logInfos = vrpsa.getLogInfos();

        writer.write("\nMemoMapSize,");
        for (int j = 0; j < logInfos.length; j++) {
            writer.write(logInfos[j].memoSize + ",");
        }
        writer.write("\n,\n");
        int maxLineNum = 0;
        for (int j = 0; j < logInfos.length; j++) {
            int size = logInfos[j].calcData.size();
            if (maxLineNum < size) {
                maxLineNum = size;
            }
        }
        writer.flush();
        writer.write("count");
        for (int j = 0; j < logInfos.length; j++) {
            writer.write(",thread" + j);
        }
        writer.write(",Sum\n");
        double[] cache = new double[logInfos.length];
        for (int j = 0; j < maxLineNum; j++) {
            writer.write(j + ",");
            double s = 0;
            for (int k = 0; k < logInfos.length; k++) {
                if (logInfos[k].calcData.size() > j) {
                    Double x = logInfos[k].calcData.get(j);
                    s += x;
                    writer.write(String.format("%10.5f,", x));
                    cache[k] = x;
                } else {
                    writer.write(String.format("%10.5f,", cache[k]));
                    s += cache[k];
                }
            }
            writer.write(String.format("%10.5f\n", s));
        }
        writer.flush();
        writer.close();
        System.out.println("M101 -- duration : " + (end - start) + "\n");
        System.out.println("Best Dist : " + vrpsa.getBestDist());
        System.out.println("History Best Dist : " + info.bestDist);
    }

}