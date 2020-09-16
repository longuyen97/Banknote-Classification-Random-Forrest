package de.longuyen

import de.longuyen.cart.DecisionTreeClassifier
import de.longuyen.data.BanknoteDataset

fun main() {
    val trainings = 100
    val trainingAccuracies = mutableListOf<Double>()
    val testingAccuracies = mutableListOf<Double>()

    for(i in 0 until trainings) {
        val dataset = BanknoteDataset()
        dataset.shuffle()
        val tree = DecisionTreeClassifier(dataset, 3, 1)
        val xTest = dataset.xTest(0.25)
        val yTest = dataset.yTest(0.25)
        val xTrain = dataset.xTrain(0.75)
        val yTrain = dataset.yTrain(0.75)

        val testPredictions = tree.predict(xTest)
        var testAccuracy = 0.0
        for (i in yTest.indices) {
            if (yTest[i] == testPredictions[i]) {
                testAccuracy += 1.0
            }
        }
        testingAccuracies.add(testAccuracy / yTest.size)

        val trainPredictions = tree.predict(xTrain)
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

