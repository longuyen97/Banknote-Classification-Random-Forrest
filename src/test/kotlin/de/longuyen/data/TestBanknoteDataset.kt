package de.longuyen.data

import org.junit.Test

class TestBanknoteDataset {
    @Test
    fun `Test the dataset`() {
        val dataset = BanknoteDataset()
        val features = dataset.features()
        val targets = dataset.targets()
    }
}