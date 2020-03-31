import numpy as np
import scipy.special as sp
import matplotlib.pyplot as plt
x = np.arange(12)
y1 = sp.factorial(x)
y2 = (2**x)*(x**2)
plt.plot(x, y1, x, y2)
plt.show()
