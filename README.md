# Classification and regression trees (CART)

Decision Trees are excellent tools for helping you to choose between several courses of action.

They provide a highly effective structure within which you can lay out options and investigate the possible outcomes of choosing those options. 
They also help you to form a balanced picture of the risks and rewards associated with each possible course of action.

<div align="center">
    <img src="data/tree.jpeg"/>
</div>

Decision trees have a number of advantages as a practical, useful managerial tool.
- Comprehensive: A significant advantage of a decision tree is that it forces the consideration of all possible outcomes of a 
decision and traces each path to a conclusion. It creates a comprehensive analysis of the consequences along each 
branch and identifies decision nodes that need further analysis.
- Easy to Use: Decision trees are easy to use and explain with simple math, no complex formulas. They present visually 
all of the decision alternatives for quick comparisons in a format that is easy to understand with only brief explanations.
- Decision trees require relatively little effort from users for data preparation.#
- Can handle both numerical and categorical data. Can also handle multi-output problems.
- Nonlinear relationships between parameters do not affect tree performance.

Some disadvantages of CART
- Decision trees can be unstable because small variations in the data might result in a completely different tree being 
generated. This is called variance, which needs to be lowered by methods like bagging and boosting.
- Greedy algorithms cannot guarantee to return the globally optimal decision tree. This can be mitigated by training multiple trees, 
where the features and samples are randomly sampled with replacement.
- Decision tree learners create biased trees if some classes dominate. It is therefore recommended to balance the data set 
prior to fitting with the decision tree.

### Implementation details



### References
- [CART (Algorithmus)](https://de.wikipedia.org/wiki/CART_(Algorithmus))
- [Decision Tree Learning](https://en.wikipedia.org/wiki/Decision_tree_learning)
- [Predictive Analysis # Classification and regression trees (CART)](https://en.wikipedia.org/wiki/Predictive_analytics#Classification_and_regression_trees_.28CART.29)
- [Let’s Write a Decision Tree Classifier from Scratch - Machine Learning Recipes](https://www.youtube.com/watch?v=LDRbO9a6XPU&ab_channel=GoogleDevelopers)