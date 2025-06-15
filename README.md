# Algorithms for the Frequency Allocation Problem
### Abstract
Frequency allocation of cell towers, known as the Frequency Allocation Problem (FAP),
can be solved using Graph Theory (Nurmalika et al., 2024)[^1], or more specifically radio k-coloring 
(Saha & Panigrahi, 2012)[^2] and is a NP-complete Problem (Fotakis et al., 2000)[^3]. Introduced is 3 types of 
algorithms to solve the FAP problem, one using BruteForce with a complexity of O(n!), 
one with using a Greedy Heuristic Algorithm with complexity of O(n^4), and finally a 
Priority Queue Algorithm with complexity of ~O(n^2), where N is the number of vertices. 

## Literature Review
There are different techniques to solving the coloring problem with solutions using Heuristic-Based
methods, examples Greedy, Random Greedy, Largest Degree First, Smallest Degree First, Saturation Degree
(Pasham, 2023 [^4]; Michail et al., 2020 [^5]); AI Driven, examples like Genetic Algorithms, Neural Networks,
and Reinforcement Learning (Pasham, 2023 [^4]); Distributed Algorithms (Pasham, 2023 [^4]); 
and others like Brown Graph Coloring (Michail et al.,2020 [^5]).

Pasham (2023 [^4]) has shown that distributed algorithms have the least time complexity, followed by
Heuristic-Based and AI being the worst.

## Introduction
### Brute Force
To get the optimal solution we can use a BruteForce method by working out all the permutations and taking the 
best of those permutations. This is an easy and straight forward method to always provided you with the most 
optimal solution, however is of O(n!) complexity due to the looking through all the permutations for 
the best result.

### Greedy Heuristic Algorithm
The BruteForce method can be reduced in complexity by only taking the best solution at the given time,
which is a greedy heuristic approach, however you still need to evaluate against all other vertices using
their edges and each vertex is connected to the other bounding this implementation to at least O(n^2) but the 
calculation to determine the quality is also O(n^2) making this a O(n^4) complexity algorithm. This will not always
lead the most optimal solution but will be a lot faster and close to the most optimal solution.

### Priority Queue Algorithm
To further improve the [Greedy Heuristic Algorithm](#greedy-heuristic-algorithm) the use of priority queues
can be employed by to start with the edge with the smallest distances first, optimized by O(logn) 
speed of a priority queue, and start filling in frequencies that have not been used by the closest neighbours -
prioritizing that close neighbours have different frequencies, and if all frequencies are taken it will 
take the frequency of the furthest neighbour from the set of closest neighbours - prioritizing same frequencies 
should be as far apart as possible. This will also be limited to only look at the k closest neighbours with 
frequencies and without frequencies to avoid looking at the entire graph and only look at the k nearest which can 
mak conflicts. The complexity is reduced by the use of priority queues to O(n^2logn + n^2\*2k) 
but is still bounded by O(n^2logn) to calculate the edges of the graph and add them 
to a queue (preprocessing), but the coloring only takes O(n^2\*2k) which is to go through 
each edge (O(n^2)) and at most look at k neighbours with frequencies or k neighbours without frequencies. 
This will not always lead the most optimal solution but will be even faster and close to 
the most optimal solution.

This algorithm is similar to the DSatur algorithm (Michail et al.,2020 [^5]) which uses saturation 
instead of priority queues and kth nearest neighbour to optimize to the time complexity.

## Test Methodology
To test the different algorithms a set of unit test where implemented to ensure that given a set of small 
problems the graphs will give optimal or close to optimal solutions. The optimal solution will be determined
by taking a quality measure of the graph which is determined by a sum of each vertex quality determined 
by the distance between each neighbour with the same frequency divided by the furthest away neighbour with 
the same frequency. This optimizes frequencies to be as far away as possible and a 100% indicates that
each vertex has a frequency assign such that the closets neighbour with the same frequency is as far away as possible.

The time that each algorithm takes to compute along with the quality of its output will be taken to measure
the quality of each algorithm.

## Results
The following results where gathered:
- The BruteForce algorithm took too long to process and no time was taken.
- The Greedy algorithm took on average 18ms to process and a quality of 98.05%.
- The Priority Queue algorithm took on average 12ms to process and a quality of 98.14%.

## Discussion
The BruteForce algorithm with its complexity of O(n!) took too long to process showing that it is largely more
expensive to compute. However, the greedy algorithm shown to be quick and accurate enough even though
it will not give the most optimal answer. The Priority Queue algorithm is shown to be even quicker and 
provides even slightly better quality.

## Conclusion
The BruteForce algorithm shown to be impractical and not viable to get the most optimal solution. However,
the Priority Queue algorithm is shown to be a better choice over the Greedy algorithm. Ultimately the 
problem is still bounded by calculating all the edges of each vertex bounding the complexity to O(n^2).

## Future Work
1. The Priority Queue can be optimized to give better results by using Backtracking, as done by Bhowmick & Hovland (2008
[^6]), to swap assignments around to give more optimal results, but this might lead to more time complexity. 
It can also be investigated to not use priority queues but to only start at the smallest edge and grow walk from 
their using the smallest edge from the last colored vertex to reduce to only O(n^2) complexity.

2. Add a visualization of the algorithm at work to show how it makes choices.
3. Conduct a systematic literature review of latest work on k-coloring to see if there has been further 
   advances like work done by Crites (2024[^7])


## References
[^1]: [Nurmalika, K., Prihandini, R., Jannah, D., Makhfurdloh, I., Agatha, A. & Wulandari, Y. 2024. Graph Coloring 
Application Using Welch Powell Algorithm On Radio Frequency In Australia.](https://www.researchgate.net/publication/381511913_Graph_Coloring_Application_Using_Welch_Powell_Algorithm_On_Radio_Frequency_In_Australia)

[^2]: [Saha, L. & Panigrahi, P. 2012. A Graph Radio k-Coloring Algorithm. In Combinatorial Algorithms. S. Arumugam & 
W.F. Smyth, Eds. Berlin, Heidelberg: Springer. 125–129. DOI: 10.1007/978-3-642-35926-2_15.](https://link.springer.com/chapter/10.1007/978-3-642-35926-2_15)

[^3]: [Fotakis, D.A., Nikoletseas, S.E., Papadopoulou, V.G. & Spirakis, P.G. 2000. NP-Completeness Results and 
Efficient Approximations for Radiocoloring in Planar Graphs. In Mathematical Foundations of Computer Science 2000. M.
Nielsen & B. Rovan, Eds. Berlin, Heidelberg: Springer. 363–372. DOI: 10.1007/3-540-44612-5_32.](https://link.springer.com/chapter/10.1007/3-540-44612-5_32)

[^4]: [Pasham, S.D. 2023. Network Topology Optimization in Cloud Systems Using Advanced Graph Coloring Algorithms. 
The Metascience. 1(1):122–148.](https://yuktabpublisher.com/index.php/TMS/article/view/127)

[^5]: [Michail, D., Kinable, J., Naveh, B. & Sichi, J.V. 2020. DOI: 10.48550/arXiv.1904.08355.](http://arxiv.org/abs/1904.08355)

[^6]: [Bhowmick, S. & Hovland, P.D. 2008. Improving the Performance of Graph Coloring Algorithms through Backtracking. 
In Computational Science – ICCS 2008. M. Bubak, G.D. van Albada, J. Dongarra, & P.M.A. Sloot, Eds. Berlin, Heidelberg: Springer. 873–882. DOI: 10.1007/978-3-540-69384-0_92.](https://link.springer.com/chapter/10.1007/978-3-540-69384-0_92)

[^7]: [Crites, B. 2024. C++ Graph Coloring Package. Available: https://github.com/brrcrites/graph-coloring](https://github.com/brrcrites/graph-coloring)