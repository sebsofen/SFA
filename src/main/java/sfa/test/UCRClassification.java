// Copyright (c) 2016 - Patrick Schäfer (patrick.schaefer@zib.de)
// Distributed under the GLP 3.0 (See accompanying file LICENSE)
package main.java.sfa.test;

import java.io.File;
import java.io.IOException;

import main.java.sfa.classification.BOSSEnsembleClassifier;
import main.java.sfa.classification.BOSSVSClassifier;
import main.java.sfa.classification.Classifier;
import main.java.sfa.classification.ParallelFor;
import main.java.sfa.timeseries.TimeSeries;
import main.java.sfa.timeseries.TimeSeriesLoader;

public class UCRClassification {

  // The datasets to use
  public static String[] datasets = new String[]{
    "Coffee",
    "CBF",
    "Beef",
  };

  public static void main(String argv[]) throws IOException {
    try {
      // the relative path to the datasets
      File dir = new File("./datasets/");

      for (String s : datasets) {
        File d = new File(dir.getAbsolutePath()+"/"+s);
        if (d.exists() && d.isDirectory()) {
          for (File f : d.listFiles()) {
            if (f.getName().toUpperCase().endsWith("TRAIN")) {
              File train = f;
              File test = new File(f.getAbsolutePath().replaceFirst("TRAIN", "TEST"));

              if (!test.exists()) {
                System.err.println("File " + test.getName() + " does not exist");
                test = null;
              }

              Classifier.DEBUG = false;
                  
              // Load the train/test splits
              TimeSeries[] testSamples = TimeSeriesLoader.loadDatset(test);
              TimeSeries[] trainSamples = TimeSeriesLoader.loadDatset(train);

              // The BOSS ensemble classifier
              BOSSEnsembleClassifier boss = new BOSSEnsembleClassifier(trainSamples, testSamples);
              BOSSEnsembleClassifier.Score scoreBOSS = boss.eval();
              System.out.println(s + ";" + scoreBOSS.toString());
              
              // The BOSS VS classifier
              BOSSVSClassifier bossVS = new BOSSVSClassifier(trainSamples, testSamples);
              BOSSVSClassifier.Score scoreBOSSVS = bossVS.eval();
              System.out.println(s + ";" + scoreBOSSVS.toString());
            }
          }
        }
        else {
          System.err.println("Does not exist!" + d.getAbsolutePath());
        }
      }
    } finally {
      ParallelFor.shutdown();
    }
  }

}