package de.longuyen.cart

import org.nd4j.linalg.api.ndarray.INDArray

class Split(val leftFeature: INDArray, val leftTarget: INDArray, val rightFeature: INDArray, val rightTarget: INDArray) {
    fun getFeature(group: Group) : INDArray {
        if(group == Group.LEFT){
            return leftFeature
        }
        return rightFeature
    }

    fun getTarget(group: Group) : INDArray {
        if(group == Group.LEFT){
            return leftTarget
        }
        return rightTarget
    }

    fun totalSample() : Long{
        return leftTarget.size(0) + rightTarget.size(0)
    }

    fun groupSize(group: Group) : Long{
        return if(group == Group.LEFT){
            leftTarget.size(0)
        }else{
            rightTarget.size(0)
        }
    }
}