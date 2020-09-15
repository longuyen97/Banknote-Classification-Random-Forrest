package de.longuyen.cart

import de.longuyen.data.Dataset
import org.nd4j.linalg.api.ndarray.INDArray
import org.nd4j.linalg.factory.Nd4j
import java.lang.Exception
import java.lang.IllegalArgumentException

abstract class DecisionTreeClassifier(dataset: Dataset, private val maxDepth: Int, private val minSize: Int) {
    private val features = dataset.features()
    private val targets = dataset.targets()
    private val classes: MutableList<Int> = classes(targets)

    init {
    }

    private fun bestSplit(features: INDArray, targets: INDArray): Split {

    }

    private fun gini(split: Split): Double {
        var ret = 0.0
        for (group in Group.values()) {
            var score = 0.0
            for (clazz in classes) {
                try {
                    val proportion = this.proportion(split.getTarget(group), clazz)
                    score += proportion * proportion
                } catch (e: Exception) {
                }
            }
            ret += (1.0 - score) * (split.groupSize(group) / split.totalSample())
        }
        return ret
    }

    private fun split(attributeIndex: Int, value: Double, targets: INDArray, features: INDArray): Split {
        val parentTargets = targets.toIntVector()
        val parentFeatures = features.toDoubleMatrix()

        val leftTargets: MutableList<Int> = mutableListOf()
        val leftFeatures: MutableList<Array<Double>> = mutableListOf()
        val rightTargets: MutableList<Int> = mutableListOf()
        val rightFeatures: MutableList<Array<Double>> = mutableListOf()

        for (y in parentFeatures.indices) {
            if (parentFeatures[y][attributeIndex] > value) {
                rightTargets.add(parentTargets[y])
                rightFeatures.add(parentFeatures[y].toTypedArray())
            } else {
                leftTargets.add(parentTargets[y])
                leftFeatures.add(parentFeatures[y].toTypedArray())
            }
        }
        return Split(
            Nd4j.createFromArray(leftFeatures.toTypedArray()),
            Nd4j.createFromArray(leftTargets.toTypedArray()),
            Nd4j.createFromArray(rightFeatures.toTypedArray()),
            Nd4j.createFromArray(rightTargets.toTypedArray())
        )
    }

    private fun proportion(targets: INDArray, target: Int): Double {
        val vector = targets.toIntVector()
        if (vector.isEmpty()) {
            throw IllegalArgumentException("Targets have no element. Very bad.")
        }
        var count = 0
        for (y in vector) {
            if (y == target) {
                count += 1
            }
        }
        return count.toDouble() / vector.size
    }

    private fun classes(targets: INDArray): MutableList<Int> {
        val vector = targets.toIntVector()
        val set = mutableSetOf<Int>()
        for (y in vector) {
            set.add(y)
        }
        return set.toList().sorted().toMutableList()
    }
}