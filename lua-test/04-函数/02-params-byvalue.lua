-- 定义函数swap,函数内部进行交换两个变量的值
local function swap(a, b)
    local temp = a
    a = b
    b = temp
    print(a, b)
end

local x = "hello"
local y = 20
print(x, y)
-- 调用swap函数，x和y的值并没有交换
print(swap(x, y))
print(x, y)

-- --------------------

local function fun1(a, b)
    --两个形参，多余的实参被忽略掉
    print(a, b)
end

local function fun2(a, b, c, d)
    --四个形参，没有被实参初始化的形参，用nil初始化
    print(a, b, c, d)
end

local x = 1
local y = 2
local z = 3

fun1(x, y, z)         -- z被函数fun1忽略掉了，参数变成 x, y
fun2(x, y, z)         -- 后面自动加上一个nil，参数变成 x, y, z, nil