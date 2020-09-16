package de.longuyen.cart

/**
 * Represents the best split a subset data can give.
 * @param bestFeatureIndex the position of the split point
 * @param bestFeatureValue the value of the split point
 * @param bestScore in this case the gini index of the split
 */
data class BestSplit (val bestFeatureIndex: Int, val bestFeatureValue: Double, val bestScore: Double){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BestSplit

        if (bestFeatureIndex != other.bestFeatureIndex) return false
        if (bestFeatureValue != other.bestFeatureValue) return false
        if (bestScore != other.bestScore) return false

        return true
    }

    override fun hashCode(): Int {
        var result = bestFeatureIndex
        result = 31 * result + bestFeatureValue.hashCode()
        result = 31 * result + bestScore.hashCode()
        return result
    }
}