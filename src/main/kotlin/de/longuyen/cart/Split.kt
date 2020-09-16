package de.longuyen.cart

data class Split(val leftFeature: Array<Array<Double>>, val leftTarget: Array<Int>, val rightFeature: Array<Array<Double>>, val rightTarget: Array<Int>) {
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Split

        if (!leftFeature.contentDeepEquals(other.leftFeature)) return false
        if (!leftTarget.contentEquals(other.leftTarget)) return false
        if (!rightFeature.contentDeepEquals(other.rightFeature)) return false
        if (!rightTarget.contentEquals(other.rightTarget)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = leftFeature.contentDeepHashCode()
        result = 31 * result + leftTarget.contentHashCode()
        result = 31 * result + rightFeature.contentDeepHashCode()
        result = 31 * result + rightTarget.contentHashCode()
        return result
    }
}