package de.longuyen.data

import org.nd4j.linalg.api.ndarray.INDArray

interface Dataset {
    fun isContinuous(index: Int) : Boolean

    fun getX() : INDArray

    fun getY() : INDArray
}