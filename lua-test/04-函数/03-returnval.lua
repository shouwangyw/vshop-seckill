local s, e = string.find("hello, world", "llo")

print(s, e)

local function swap(a, b)
    -- 定义函数 swap，实现两个变量交换值
    return b, a              -- 按相反顺序返回变量的值
end

local x = 1
local y = 20
x, y = swap(x, y)           -- 调用 swap 函数
print(x, y)                 --> output   20     1

function init()
    --init 函数 返回两个值 1 和 "lua"
    return 1, "lua"
end

x = init()
print(x)

x, y, z = init()
print(x, y, z)
-- -----------------------
local x, y, z = init(), 2   -- init 函数的位置不在最后，此时只返回 1
print(x, y, z)              -->output  1  2  nil

local a, b, c = 2, init()   -- init 函数的位置在最后，此时返回 1 和 "lua"
print(a, b, c)              -->output  2  1  lua

-- ---------------------
print(init(), 2)   -->output  1  2
print(2, init())   -->output  2  1  lua

-- ---------------------
print((init()), 2)   -->output  1  2
print(2, (init()))   -->output  2  1