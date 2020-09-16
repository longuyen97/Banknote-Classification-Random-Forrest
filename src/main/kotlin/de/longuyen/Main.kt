package de.longuyen

import de.longuyen.cart.DecisionTreeClassifier
import de.longuyen.data.BanknoteDataset

fun main() {
    val dataset = BanknoteDataset()
    val tree = DecisionTreeClassifier(dataset, 3, 1)
    tree.visualize(1500, 750)
}

