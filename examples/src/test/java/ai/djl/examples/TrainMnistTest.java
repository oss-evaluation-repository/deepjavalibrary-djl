/*
 * Copyright 2019 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in compliance
 * with the License. A copy of the License is located at
 *
 * http://aws.amazon.com/apache2.0/
 *
 * or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES
 * OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package ai.djl.examples;

import ai.djl.ModelException;
import ai.djl.examples.inference.ImageClassification;
import ai.djl.examples.training.TrainMnist;
import ai.djl.examples.training.util.ExampleTrainingResult;
import ai.djl.modality.Classifications;
import ai.djl.translate.TranslateException;
import java.io.IOException;
import org.apache.commons.cli.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TrainMnistTest {

    @Test
    public void testTrainMnist()
            throws ModelException, TranslateException, IOException, ParseException {
        if (Boolean.getBoolean("nightly")) {
            String[] args = new String[] {"-g", "1"};

            TrainMnist test = new TrainMnist();
            ExampleTrainingResult result = test.runExample(args);
            Assert.assertTrue(result.getEvaluation("Accuracy") > 0.9f);
            Assert.assertTrue(result.getEvaluation("SoftmaxCrossEntropyLoss") < 0.35f);

            Classifications classifications = new ImageClassification().predict();
            Classifications.Classification best = classifications.best();
            Assert.assertEquals(best.getClassName(), "0");
            Assert.assertTrue(Double.compare(best.getProbability(), 0.9) > 0);
        } else {
            String[] args = new String[] {"-g", "1", "-m", "2"};

            TrainMnist test = new TrainMnist();
            test.runExample(args);
        }
    }
}
