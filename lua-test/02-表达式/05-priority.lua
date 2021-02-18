local a, b = 1, 2
local x, y = 3, 4
local i = 10
local res = 0
res = a + i < b / 2 + 1  -->等价于res =  (a + i) < ((b/2) + 1)
res = 5 + x ^ 2 * 8        -->等价于res =  5 + ((x^2) * 8)
res = a < y and y <= x  -->等价于res =  (a < y) and (y <= x)