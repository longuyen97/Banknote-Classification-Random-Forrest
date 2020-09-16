package de.longuyen.data

interface Dataset {
    fun featureName(index: Int) : String

    fun isContinuous() : Boolean

    fun features() : Array<Array<Double>>

    fun targets() : Array<Int>

    fun xTrain(percentage: Double) : Array<Array<Double>>

    fun yTrain(percentage: Double) : Array<Int>

    fun xTest(percentage: Double) : Array<Array<Double>>

    fun yTest(percentage: Double) : Array<Int>

    fun shuffle()
}