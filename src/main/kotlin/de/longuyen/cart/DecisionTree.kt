package de.longuyen.cart

import de.longuyen.data.Dataset

abstract class DecisionTree(private val dataset: Dataset, private val maxDepth: Int, private val minSize: Int) {
    fun testSplit(attributeIndex: Int, splitValue: Int){
        TODO()
    }

    fun getSplit(){
        TODO()
    }

    fun split(){
        TODO()
    }
}