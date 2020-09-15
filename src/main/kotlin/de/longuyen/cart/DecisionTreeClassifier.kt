package de.longuyen.cart

import de.longuyen.data.Dataset
import org.slf4j.LoggerFactory
import java.lang.Exception
import java.lang.IllegalArgumentException

class DecisionTreeClassifier(dataset: Dataset, private val maxDepth: Int, private val minSize: Int) {
    private val features = dataset.features()
    private val targets = dataset.targets()
    private val classes: MutableList<Int> = classes(targets)
    private val root: Node

    companion object{
        private val logger = LoggerFactory.getLogger(DecisionTreeClassifier::javaClass.name)
    }

    class Node(val attributeIndex: Int, val attributeValue: Double, var target: Int? = null) {
        var left: Node? = null
        var right: Node? = null
    }

    init {
        val bestSplit = bestSplit(features, targets)
        this.root = Node(bestSplit.bestFeatureIndex, bestSplit.bestFeatureValue)
        this.constructTree(root, bestSplit, features, targets, 1)
    }

    private fun constructTree(
        root: Node,
        bestSplit: BestSplit,
        features: Array<Array<Double>>,
        targets: Array<Int>,
        depth: Int
    ) {
        val split = this.split(bestSplit.bestFeatureIndex, bestSplit.bestFeatureValue, features, targets)
        if (depth >= this.maxDepth) {
            root.target = split.target()
        } else if(depth < this.maxDepth && this.features.size(0) > this.minSize) {
            val leftBestSplit = bestSplit(split.leftFeature, split.leftTarget)
            root.left = Node(leftBestSplit.bestFeatureIndex, leftBestSplit.bestFeatureValue)
            this.constructTree(root.left!!, leftBestSplit, split.leftFeature, split.leftTarget, depth + 1)

            val rightBestSplit = bestSplit(split.rightFeature, split.rightTarget)
            root.right = Node(rightBestSplit.bestFeatureIndex, rightBestSplit.bestFeatureValue)
            this.constructTree(root.right!!, rightBestSplit, split.rightFeature, split.rightTarget, depth + 1)
        }
    }

    private fun bestSplit(features: INDArray, targets: INDArray): BestSplit {
        var bestFeatureIndex = 0
        var bestFeatureValue = 0.0
        var bestScore = Double.MIN_VALUE

        val featuresMatrix = features.toDoubleMatrix()

        for (y in featuresMatrix.indices) {
            for (x in featuresMatrix[y].indices) {
                val split = this.split(x, featuresMatrix[y][x], features, targets)
                val score = this.gini(split)
                if (score > bestScore) {
                    bestScore = score
                    bestFeatureIndex = x
                    bestFeatureValue = featuresMatrix[y][x]
                }
            }
        }
        return BestSplit(bestFeatureIndex, bestFeatureValue)
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

    private fun split(attributeIndex: Int, attributeValue: Double, features: INDArray, targets: INDArray): Split {
        val parentTargets = targets.toIntVector()
        val parentFeatures = features.toDoubleMatrix()

        val leftTargets: MutableList<Int> = mutableListOf()
        val leftFeatures: MutableList<Array<Double>> = mutableListOf()
        val rightTargets: MutableList<Int> = mutableListOf()
        val rightFeatures: MutableList<Array<Double>> = mutableListOf()

        for (y in parentFeatures.indices) {
            if (parentFeatures[y][attributeIndex] > attributeValue) {
                rightTargets.add(parentTargets[y])
                rightFeatures.add(parentFeatures[y].toTypedArray())
            } else {
                leftTargets.add(parentTargets[y])
                leftFeatures.add(parentFeatures[y].toTypedArray())
            }
        }
        return Split(leftFeatures.toTypedArray(),
            leftTargets.toTypedArray(),
            rightFeatures.toTypedArray(),
            rightTargets.toTypedArray()
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