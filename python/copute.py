import numpy as np
from numpy.random import Generator, PCG64
import matplotlib.pyplot as plt
import time
a = np.arange(100000)
r = Generator(PCG64())

start = time.time()
for x in a:
    np.exp(r.standard_normal()*10000)
end = time.time()
print("duration :")
print((end - start)*1000)
print(" ms")
