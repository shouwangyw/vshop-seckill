print(1 < 2)    -->打印 true
print(1 == 2)   -->打印 false
print(1 ~= 2)   -->打印 true
local a, b = true, false
print(a == b)  -->打印 false

local a = { x = 1, y = 0}
local b = { x = 1, y = 0}
if a == b then
    print("a==b")
else
    print("a~=b")
end