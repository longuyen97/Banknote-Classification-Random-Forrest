package de.longuyen.data

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVRecord
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

    override fun featureName(index: Int): String {
        return mutableListOf(
            "Variance",
            "Skewness",
            "Curtosis",
            "Entropy"
        )[index]
    }

    override fun isContinuous(): Boolean {
        return true
    }

    override fun features(): Array<Array<Double>> {
        return this.x.toTypedArray()
    }

    override fun targets(): Array<Int> {
        return this.y.toTypedArray()
    }

    override fun shuffle() {
        val indices = this.x.indices.toMutableList()
        indices.shuffle()
        for(i in 0 until indices.size){
            val index = indices[i]

            val xTemp = this.x[i]
            this.x[i] = this.x[index]
            this.x[index] = xTemp

            val yTemp = this.y[i]
            this.y[i] = this.y[index]
            this.y[index] = yTemp
        }
    }
}