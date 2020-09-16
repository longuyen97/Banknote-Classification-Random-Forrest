package de.longuyen.cart

import de.longuyen.data.Dataset
import guru.nidi.graphviz.attribute.Color
import guru.nidi.graphviz.attribute.Label
import guru.nidi.graphviz.attribute.Style
import guru.nidi.graphviz.engine.Format
import guru.nidi.graphviz.engine.Graphviz
import guru.nidi.graphviz.model.Factory
import guru.nidi.graphviz.model.Factory.mutGraph
import guru.nidi.graphviz.model.Factory.mutNode
import guru.nidi.graphviz.model.MutableGraph
import guru.nidi.graphviz.model.MutableNode
import org.slf4j.LoggerFactory
import java.io.File

/**
 * Decision tree
 */
class DecisionTreeClassifier(private val dataset: Dataset, private val maxDepth: Int, private val minSize: Int) {
    private val features = dataset.features()
    private val targets = dataset.targets()
    private val classes: MutableList<Int> = classes(targets)
    private val root: Node

    companion object {
        private val log = LoggerFactory.getLogger(DecisionTreeClassifier::javaClass.name)
    }

    /**
     * Tree node
     */
    data class Node(val attributeIndex: Int, val attributeValue: Double, val depth: Int, var target: Int? = null) {
        var left: Node? = null
        var right: Node? = null
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Node

            if (attributeIndex != other.attributeIndex) return false
            if (attributeValue != other.attributeValue) return false
            if (depth != other.depth) return false
            if (target != other.target) return false
            if (left != other.left) return false
            if (right != other.right) return false

            return true
        }

        override fun hashCode(): Int {
            var result = attributeIndex
            result = 31 * result + attributeValue.hashCode()
            result = 31 * result + depth
            result = 31 * result + (target ?: 0)
            result = 31 * result + (left?.hashCode() ?: 0)
            result = 31 * result + (right?.hashCode() ?: 0)
            return result
        }
    }

    init {
        log.info("Constructing decision tree with max depth ${this.maxDepth} and minimal split size ${this.minSize}")
        val bestSplit = bestSplit(features, targets)
        this.root = Node(bestSplit.bestFeatureIndex, bestSplit.bestFeatureValue, 0)
        log.info("Constructed root with feature '${dataset.featureName(bestSplit.bestFeatureIndex)}', value '${bestSplit.bestFeatureValue}' and a gini score of '${bestSplit.bestScore}'")
        this.constructTree(root, bestSplit, features, targets, 1)
    }

    fun visualize(width: Int, height: Int) {
        val graph: MutableGraph = mutGraph("Decision tree")
            .setDirected(true)
        val treeMap = mutableMapOf<Node, MutableNode>()
        val queue = mutableListOf(this.root)
        treeMap[this.root] = mutNode("${root.depth}: ${dataset.featureName(root.attributeIndex)} > ${root.attributeValue}?")
        while (queue.isNotEmpty()) {
            val current = queue.first()
            if(current.left != null && current.right !== null){
                queue.add(current.left!!)
                queue.add(current.right!!)
                treeMap[current.left!!] = mutNode("${current.left!!.depth}: ${dataset.featureName(current.left!!.attributeIndex)} > ${current.left!!.attributeValue}?")
                treeMap[current.right!!] = mutNode("${current.right!!.depth}: ${dataset.featureName(current.right!!.attributeIndex)} > ${current.right!!.attributeValue}?")
                treeMap[current]!!.addLink(Factory.to(treeMap[current.left!!]!!).with(Style.BOLD, Label.of("Yes"), Color.GREEN))
                treeMap[current]!!.addLink(Factory.to(treeMap[current.right!!]!!).with(Style.BOLD, Label.of("No"), Color.RED))
            }else{
                treeMap[current]!!.addLink(Factory.to(mutNode("${current.depth}: ${dataset.featureName(current.attributeIndex)} > ${current.attributeValue}: ${current.target!!}")).with(Style.BOLD, Label.of("Yes"), Color.GREEN))
                treeMap[current]!!.addLink(Factory.to(mutNode("${current.depth}: ${dataset.featureName(current.attributeIndex)} > ${current.attributeValue}: ${(current.target!!).xor(1)}")).with(Style.BOLD, Label.of("No"), Color.RED))
            }
            queue.removeFirst()
        }
        graph.add(treeMap[this.root]!!)
        Graphviz.fromGraph(graph).width(width).height(height).render(Format.PNG).toFile(File("target/output.png"))
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
        } else if (depth < this.maxDepth && this.features.size > this.minSize) {
            val leftBestSplit = bestSplit(split.leftFeature, split.leftTarget)
            root.left = Node(leftBestSplit.bestFeatureIndex, leftBestSplit.bestFeatureValue, depth)
            this.constructTree(root.left!!, leftBestSplit, split.leftFeature, split.leftTarget, depth + 1)

            val rightBestSplit = bestSplit(split.rightFeature, split.rightTarget)
            root.right = Node(rightBestSplit.bestFeatureIndex, rightBestSplit.bestFeatureValue, depth)
            this.constructTree(root.right!!, rightBestSplit, split.rightFeature, split.rightTarget, depth + 1)
        }
    }

    private fun bestSplit(features: Array<Array<Double>>, targets: Array<Int>): BestSplit {
        var bestFeatureIndex = 0
        var bestFeatureValue = 0.0
        var bestScore = 0.5

        for (y in features.indices) {
            for (x in features[y].indices) {
                val split = this.split(x, features[y][x], features, targets)
                val score = this.gini(split)
                if (score < bestScore) {
                    bestScore = score
                    bestFeatureIndex = x
                    bestFeatureValue = features[y][x]
                }
            }
        }
        return BestSplit(bestFeatureIndex, bestFeatureValue, bestScore)
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

    private fun split(
        attributeIndex: Int,
        attributeValue: Double,
        features: Array<Array<Double>>,
        targets: Array<Int>
    ): Split {
        val leftTargets: MutableList<Int> = mutableListOf()
        val leftFeatures: MutableList<Array<Double>> = mutableListOf()
        val rightTargets: MutableList<Int> = mutableListOf()
        val rightFeatures: MutableList<Array<Double>> = mutableListOf()

        for (y in features.indices) {
            if (features[y][attributeIndex] > attributeValue) {
                rightTargets.add(targets[y])
                rightFeatures.add(features[y])
            } else {
                leftTargets.add(targets[y])
                leftFeatures.add(features[y])
            }
        }
        return Split(
            leftFeatures.toTypedArray(),
            leftTargets.toTypedArray(),
            rightFeatures.toTypedArray(),
            rightTargets.toTypedArray()
        )
    }

    private fun proportion(targets: Array<Int>, target: Int): Double {
        if (targets.isEmpty()) {
            throw IllegalArgumentException("Targets have no element. Very bad.")
        }
        var count = 0
        for (y in targets) {
            if (y == target) {
                count += 1
            }
        }
        return count.toDouble() / targets.size
    }

    private fun classes(targets: Array<Int>): MutableList<Int> {
        val set = mutableSetOf<Int>()
        for (y in targets) {
            set.add(y)
        }
        return set.toList().sorted().toMutableList()
    }
}