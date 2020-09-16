package de.longuyen

import de.longuyen.cart.RandomForrestClassifier
import de.longuyen.data.BanknoteDataset

fun main() {
    val trainings = 1
    val trainingAccuracies = mutableListOf<Double>()
    val testingAccuracies = mutableListOf<Double>()

    for (training in 0 until trainings) {
        val dataset = BanknoteDataset()
        dataset.shuffle()
        val xTest = dataset.xTest(0.25)
        val yTest = dataset.yTest(0.25)
        val xTrain = dataset.xTrain(0.75)
        val yTrain = dataset.yTrain(0.75)
        val forrest = RandomForrestClassifier(dataset, xTrain, yTrain, 5,3, 1)

        val testPredictions = forrest.predict(xTest)
        var testAccuracy = 0.0
        for (i in yTest.indices) {
            if (yTest[i] == testPredictions[i]) {
                testAccuracy += 1.0
            }
        }
        testingAccuracies.add(testAccuracy / yTest.size)

        val trainPredictions = forrest.predict(xTrain)
        var trainingAccuracy = 0.0
        for (i in yTrain.indices) {
            if (yTrain[i] == trainPredictions[i]) {
                trainingAccuracy += 1.0
            }
        }
        trainingAccuracies.add(trainingAccuracy / yTrain.size)
    }
    println("Average training accuracy: ${trainingAccuracies.average()}")
    println("Average testing accuracy: ${testingAccuracies.average()}")
}

