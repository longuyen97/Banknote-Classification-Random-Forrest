package de.longuyen.data

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVRecord
import org.nd4j.linalg.api.ndarray.INDArray
import org.nd4j.linalg.factory.Nd4j
import java.io.FileReader


class BanknoteDataset : Dataset {
    private val x: MutableList<Array<Double>> = mutableListOf()
    private val y: MutableList<Int> = mutableListOf()

    init {
        FileReader("data/banknote.csv").use {
            val records: Iterable<CSVRecord> = CSVFormat.DEFAULT.parse(it)
            for (record in records) {
                val x = mutableListOf<Double>()
                for(i in 0 until 4) {
                    x.add(record[i].toDouble())
                }
                val y = record[4].toInt()
                this.x.add(x.toTypedArray())
                this.y.add(y)
            }
        }
    }

    override fun isContinuous(index: Int): Boolean {
        return mutableListOf(true, true, true, true)[index]
    }

    override fun features(): INDArray {
        return Nd4j.createFromArray(this.x.toTypedArray())
    }

    override fun targets(): INDArray {
        return Nd4j.createFromArray(this.y.toTypedArray())
    }
}