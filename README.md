# Classification and regression trees for banknote authentication (CART)

### Result
The task was to classify banknote's authentication. Dataset can be found at: [www.uci.edu](https://archive.ics.uci.edu/ml/datasets/banknote+authentication):
- **Dataset size**: 1372 samples
- **Data shuffle**: Yes
- **Data balanced**: Yes
- **Training / Testing ratio**: 75/25

Single decision tree provides a significant accuracy over random dummy model:
- **Max depth**: 3
- **Min leaf's size**: 1
- **Average training accuracy of 100 random trees**: 76%
- **Average testing accuracy of 100 random trees**: 73% 

Random Forrest (Wisdom of the crowd) provides a significant boost of accuracy toward single decision tree:
- **Forrest size**: 3
- **Max depth**: 3
- **Min leaf's size**: 1
- **Average training accuracy of 100 random trees**: 87%
- **Average testing accuracy of 100 random trees**: 88% 

Visualization of a single decision tree:

![](data/output.png)

### References
- [How decision trees work](https://www.youtube.com/watch?v=9w16p4QmkAI)
- [CART (Algorithmus)](https://de.wikipedia.org/wiki/CART_(Algorithmus))
- [Decision Tree Learning](https://en.wikipedia.org/wiki/Decision_tree_learning)
- [Predictive Analysis # Classification and regression trees (CART)](https://en.wikipedia.org/wiki/Predictive_analytics#Classification_and_regression_trees_.28CART.29)
- [Decision Tree Classification - Avinash Navlani](https://www.datacamp.com/community/tutorials/decision-tree-classification-python)
- [Decision Tree Classification - Afroz Chakure](https://towardsdatascience.com/decision-tree-classification-de64fc4d5aac)
