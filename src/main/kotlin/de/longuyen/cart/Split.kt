package de.longuyen.cart

class Split(val leftFeature: Array<Array<Double>>, val leftTarget: Array<Int>, val rightFeature: Array<Array<Double>>, val rightTarget: Array<Int>) {
    fun getTarget(group: Group): Array<Int> {
        if (group == Group.LEFT) {
            return leftTarget
        }
        return rightTarget
    }

    fun totalSample(): Int {
        return leftTarget.size + rightTarget.size
    }

    fun groupSize(group: Group): Int {
        return if (group == Group.LEFT) {
            leftTarget.size
        } else {
            rightTarget.size
        }
    }

    fun target(): Int? {
        return intArrayOf(*leftTarget.toIntArray(), *rightTarget.toIntArray())
            .toList()
            .groupingBy { it }
            .eachCount()
            .maxByOrNull { it.value }?.key
    }
}