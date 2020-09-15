package de.longuyen.cart

import org.nd4j.linalg.api.ndarray.INDArray
import org.nd4j.linalg.factory.Nd4j

class Split(val leftFeature: Array<Array<Double>>, val leftTarget: Array<Int>, val rightFeature: Array<Array<Double>>, val rightTarget: Array<Int>) {
    fun getTarget(group: Group): INDArray {
        if (group == Group.LEFT) {
            return leftTarget
        }
        return rightTarget
    }

    fun totalSample(): Long {
        return leftTarget.size(0) + rightTarget.size(0)
    }

    fun groupSize(group: Group): Long {
        return if (group == Group.LEFT) {
            leftTarget.size(0)
        } else {
            rightTarget.size(0)
        }
    }

    fun target(): Int? {
        return Nd4j.concat(0, leftTarget, rightTarget)
            .toIntVector().toList().groupingBy { it }.eachCount().maxByOrNull { it.value }?.key
    }
}