package experiments;

import sfa.timeseries.TimeSeries;
import sfa.timeseries.TimeSeriesLoader;

import java.io.File;
import java.io.IOException;

/**
 * Created by sebastian on 3/8/16.
 */
public class Experiment {


    public static void main(String[] args) throws Exception {

        TimeSeries[] train = TimeSeriesLoader.loadDatset(new File("./datasets/CBF/CBF_TEST"));
    }
}
