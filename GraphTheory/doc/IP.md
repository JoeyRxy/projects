# VRP
## Vehicle flow formulations

The formulation of the TSP by Dantzig, Fulkerson and Johnson  was extended to create the two index vehicle flow formulations for the VRP
$$
\text{min} \sum_{i\in V}\sum_{j \in V}c_{ij}x_{ij}
$$

subject to

$$
\begin{array}{l}
{\displaystyle \sum _{i\in V}x_{ij}=1\quad \forall j\in V\backslash \left\{0\right\}} &(1)\\
{\displaystyle \sum _{i\in V}x_{ij}=1\quad \forall j\in V\backslash \left\{0\right\}}&(2)\\
{\displaystyle \sum _{i\in V}x_{i0}=K}&(3)\\	
{\displaystyle \sum _{j\in V}x_{0j}=K}&(4)\\
{\displaystyle \sum _{i\notin S}\sum _{j\in S}x_{ij}\geq r(S),~~\forall S\subseteq V\setminus \{0\},S\neq \emptyset }	&(5)\\
{\displaystyle x_{ij}\in \{0,1\}\quad \forall i,j\in V}&(6)
\end{array} 

$$
In this formulation $c_{ij}$ represents the cost of going from node $i$ to node $j$, $x_{ij}$ is a binary variable that has value $1$ if the arc going from $i$ to $j$ is considered as part of the solution and $0$ otherwise, $K$ is the number of available vehicles and $r(S)$ corresponds to the minimum number of vehicles needed to serve set $S$. We are also assuming that $0$ is the depot node.

Constraints $(1)$ and $(2)$ state that exactly one arc enters and exactly one leaves each vertex associated with a customer, respectively. Constraints $(3)$ and $(4)$  say that the number of vehicles leaving the depot is the same as the number entering. Constraints $(5)$  are the capacity cut constraints, which impose that the routes must be connected and that the demand on each route must not exceed the vehicle capacity. Finally, constraints $(6)$ are the integrality constraints.

One arbitrary constraint among the $2|V|$ constraints is actually implied by the remaining $2|V|-1$ ones so it can be removed. Each cut defined by a customer set ''S'' is crossed by a number of arcs not smaller than {{tmath|r(s)}}(minimum number of vehicles needed to serve set ''S'').

An alternative formulation may be obtained by transforming the capacity cut constraints into generalised subtour elimination constraints (GSECs).
$$
\sum_{i\in S}\sum_{j\in S}x_{ij} \leq |S|-r(s)
$$
which imposes that at least {{tmath|r(s)}}arcs leave each customer set ''S''.

GCECs and CCCs have an exponential number of constraints so it is practically impossible to solve the linear relaxation. A possible way to solve this is to consider a limited subset of these constraints and add the rest if needed.

A different method again is to use a family of constraints which have a polynomial cardinality which are known as the MTZ constraints, they were first proposed for the TSP
$$
u_j-u_i\geq d_j-C(1-x_{ij}) ~~~~~~\forall i,j \in V\backslash\{0\}, i\neq j~~~~\text{s.t. } d_i +d_j \leq C
$$

$$
0 \leq u_i \leq C-d_i ~~~~~~\forall i \in V\backslash \{0\}
$$

where $ u_i,~i \in V \backslash \{0\}$ is an additional continuous variable which represents the load left in the vehicle '''after''' visiting customer ''i'' and ''d_i'' is the demand of customer ''i''. These impose both the connectivity and the capacity requirements. When $x_{ij}=0$ constraint then ''i'' 'is not binding' since $u_i\leq C$ and $u_j\geq d_j$ whereas $x_{ij} = 1$ they impose that $u_j \geq u_i +d_j$.

These have been used extensively to model the basic VRP (CVRP) and the VRPB. However, their power is limited to these simple problems. They can only be used when the cost of the solution can be expressed as the sum of the costs of the arc costs. We cannot also know which vehicle traverses each arc. Hence we cannot use this for more complex models where the cost and or feasibility is dependent on the order of the customers or the vehicles used.

# TSP
## formation

Label the cities with the numbers $1, …, n$ and define:
$$
{\displaystyle x_{ij}={\begin{cases}1&{\text{the path goes from city }}i{\text{ to city }}j\\0&{\text{otherwise}}\end{cases}}}
$$
For $i = 1, …, n$, let ${\displaystyle u_{i}}$ be a dummy variable, and finally take ${\displaystyle c_{ij}}$ to be the distance from city $i$ to city $j$. Then TSP can be written as the following integer linear programming problem:
$$
{\displaystyle 
\begin{aligned}
    \min &\sum _{i=1}^{n}\sum _{j\neq i,j=1}^{n}c_{ij}x_{ij}\colon &&\\
    &x_{ij}\in \{0,1\}&&i,j=1,\ldots ,n;\\
    &u_{i}\in \mathbf {Z} &&i=2,\ldots ,n;\\
    &\sum _{i=1,i\neq j}^{n}x_{ij}=1&&j=1,\ldots ,n;\\
    &\sum _{j=1,j\neq i}^{n}x_{ij}=1&&i=1,\ldots ,n;\\
    &u_{i}-u_{j}+nx_{ij}\leq n-1&&2\leq i\neq j\leq n;\\
    &0\leq u_{i}\leq n-1&&2\leq i\leq n.
\end{aligned}
}
$$

The first set of equalities requires that each city is arrived at from exactly one other city, and the second set of equalities requires that from each city there is a departure to exactly one other city. The last constraints enforce that there is only a single tour covering all cities, and not two or more disjointed tours that only collectively cover all cities. To prove this, it is shown below (1) that every feasible solution contains only one closed sequence of cities, and (2) that for every single tour covering all cities, there are values for the dummy variables ${\displaystyle u_{i}}$ that satisfy the constraints.

To prove that every feasible solution contains only one closed sequence of cities, it suffices to show that every subtour in a feasible solution passes through city 1 (noting that the equalities ensure there can only be one such tour). For if we sum all the inequalities corresponding to ${\displaystyle x_{ij}=1}$ for any subtour of $k$ steps not passing through city $1$, we obtain:

${\displaystyle nk\leq (n-1)k,}$
which is a contradiction.

It now must be shown that for every single tour covering all cities, there are values for the dummy variables $u_{i}$ that satisfy the constraints.

Without loss of generality, define the tour as originating (and ending) at city 1. Choose $u_{i}$=t if city $i$ is visited in step $t$ ($i, t = 1, 2, ..., n$). Then

${\displaystyle u_{i}-u_{j}\leq n-1,}$
since ${\displaystyle u_{i}}$ can be no greater than $n$ and ${\displaystyle u_{j}}$ can be no less than $1$; hence the constraints are satisfied whenever ${\displaystyle x_{ij}=0.}$ For $x_{ij}=1$, we have:

${\displaystyle u_{i}-u_{j}+nx_{ij}=(t)-(t+1)+n=n-1,}$
satisfying the constraint.