package de.longuyen.data

import org.junit.Test
import kotlin.test.assertTrue

class TestBanknoteDataset {
    @Test
    fun `Test the dataset`() {
        val dataset = BanknoteDataset()
        val features = dataset.features()
        val targets = dataset.targets()
        assertTrue(features.shape().toTypedArray().contentEquals(mutableListOf(1372L, 4L).toTypedArray()))
        assertTrue(targets.shape().toTypedArray().contentEquals(mutableListOf(1372L).toTypedArray()))
    }
}