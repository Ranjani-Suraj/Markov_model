import matplotlib.pyplot as plt
import numpy as np


output = "for p = 5.0E-4 , avg time 3888544.4 avg size 6.2 \
for p = 6.000000000000001E-4 , avg time 4149081.0 avg size 6.666666666666667 \
for p = 7.000000000000001E-4 , avg time 4933335.75 avg size 8.0 \
for p = 8.000000000000001E-4 , avg time 3763650.3333333335 avg size 7.333333333333333 \
for p = 9.000000000000002E-4 , avg time 4051069.5 avg size 7.75 \
for p = 0.0010000000000000002 , avg time 3988261.8571428573 avg size 10.571428571428571 \
for p = 0.0011000000000000003 , avg time 3918790.5 avg size 13.5 \
for p = 0.0012000000000000003 , avg time 4484114.2 avg size 15.4 \
for p = 0.0013000000000000004 , avg time 5026848.8 avg size 18.6 \
for p = 0.0014000000000000004 , avg time 4252129.0 avg size 36.0 \
for p = 0.0015000000000000005 , avg time 4843915.333333333 avg size 28.0 \
for p = 0.0016000000000000005 , avg time 4863100.6 avg size 31.6 \
for p = 0.0017000000000000006 , avg time 5577544.666666667 avg size 58.333333333333336 \
for p = 0.0018000000000000006 , avg time 4915375.6 avg size 46.2 \
for p = 0.0019000000000000006 , avg time 6314568.0 avg size 108.28571428571429 \
for p = 0.0020000000000000005 , avg time 7590658.75 avg size 147.25 \
for p = 0.0021000000000000003 , avg time 9826048.0 avg size 336.6666666666667 \
for p = 0.0022 , avg time 9405961.5 avg size 475.0 \
for p = 0.0023 , avg time 8624292.0 avg size 591.0 \
for p = 0.0024 , avg time 8134819.0 avg size 657.0 \
for p = 0.0024999999999999996 , avg time 8318605.333333333 avg size 725.6666666666666 \
for p = 0.0025999999999999994 , avg time 8398226.3 avg size 732.7 \
for p = 0.0026999999999999993 , avg time 8223784.333333333 avg size 777.5 \
for p = 0.002799999999999999 , avg time 7543528.0 avg size 809.0"

output = "for p = 5.0E-4 , avg time 3929285.8 avg size 5.6 \
for p = 6.000000000000001E-4 , avg time 4056544.0 avg size 6.142857142857143 \
for p = 7.000000000000001E-4 , avg time 5171339.0 avg size 8.75 \
for p = 8.000000000000001E-4 , avg time 3920416.0 avg size 6.0 \
for p = 9.000000000000002E-4 , avg time 5880117.6 avg size 10.0 \
for p = 0.0010000000000000002 , avg time 4282987.833333333 avg size 12.0 \
for p = 0.0011000000000000003 , avg time 4118774.5714285714 avg size 13.428571428571429 \
for p = 0.0012000000000000003 , avg time 5271505.5 avg size 19.5 \
for p = 0.0013000000000000004 , avg time 5082492.333333333 avg size 16.0 \
for p = 0.0014000000000000004 , avg time 5437573.25 avg size 17.0 \
for p = 0.0015000000000000005 , avg time 6278405.0 avg size 26.0 \
for p = 0.0016000000000000005 , avg time 5522386.0 avg size 28.0 \
for p = 0.0017000000000000006 , avg time 4223880.0 avg size 77.0 \
for p = 0.0018000000000000006 , avg time 5120816.111111111 avg size 76.11111111111111 \
for p = 0.0019000000000000006 , avg time 6941404.0 avg size 52.0 \
for p = 0.0020000000000000005 , avg time 7143958.5 avg size 145.5 \
for p = 0.0021000000000000003 , avg time 1.01571408E7 avg size 318.0 \
for p = 0.0022 , avg time 8685321.5 avg size 465.75 \
for p = 0.0023 , avg time 8216494.333333333 avg size 567.6666666666666 \
for p = 0.0024 , avg time 7574513.666666667 avg size 633.3333333333334 \
for p = 0.0024999999999999996 , avg time 8692586.333333334 avg size 708.0 \
for p = 0.0025999999999999994 , avg time 8555431.5 avg size 737.5 \
for p = 0.0026999999999999993 , avg time 8001658.8 avg size 782.0 \
for p = 0.002799999999999999 , avg time 7457726.2 avg size 819.2 \
for p = 0.002899999999999999 , avg time 7209584.0 avg size 825.6666666666666"

p_to_size = dict()
words = output.split(" ")
for i in range(len(words)):
    word = words[i]
    if word == '=':
        p_to_size[(float)(words[i+1])*100] = (float)(words[i+8])
        i+=8

print(p_to_size)


plt.scatter(p_to_size.keys(), p_to_size.values())
plt.show()






