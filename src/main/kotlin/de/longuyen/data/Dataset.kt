package de.longuyen.data

interface Dataset {
    fun featureName(index: Int) : String

    fun isContinuous() : Boolean

    fun features() : Array<Array<Double>>

    fun targets() : Array<Int>

    fun shuffle()
}