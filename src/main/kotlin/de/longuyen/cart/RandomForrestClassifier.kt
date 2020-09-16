package de.longuyen.cart

import de.longuyen.data.Dataset

class RandomForrestClassifier (
    dataset: Dataset,
    features: Array<Array<Double>>,
    targets: Array<Int>,
    forrestSize: Int,
    maxDepth: Int,
    minSize: Int){
    private val forrest = mutableListOf<DecisionTreeClassifier>()

    init {
        val portionSize = features.size / forrestSize
        for(i in 0 until forrestSize){
            forrest.add(DecisionTreeClassifier(dataset,
                features.copyOfRange(i * portionSize, i * portionSize + portionSize),
                targets.copyOfRange(i * portionSize, i * portionSize + portionSize),
                maxDepth,
                minSize
            ))
        }
    }

    fun predict(features: Array<Array<Double>>) : Array<Int> {
        val predictions = mutableListOf<Int>()
        for(feature in features){
            predictions.add(this.predict(feature))
        }
        return predictions.toTypedArray()
    }

    private fun predict(feature: Array<Double>) : Int {
        val predictions = mutableListOf<Int>()
        for(tree in forrest){
            predictions.add(tree.predict(feature))
        }
        return predictions.groupingBy { it }
            .eachCount()
            .maxByOrNull { it.value }!!.key
    }
}

