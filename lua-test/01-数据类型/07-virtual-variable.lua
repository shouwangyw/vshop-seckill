-- string.find (s,p) 从string 变量s的开头向后匹配 string
-- p，若匹配不成功，返回nil，若匹配成功，返回第一次匹配成功
-- 的起止下标。

--start 值为起始下标，finish值为结束下标
local start, finish = string.find("hello", "he")
print(start, finish)                          --输出 1   2

local start = string.find("hello", "he")      -- start值为起始下标
print(start)                               -- 输出 1

-- 采用虚变量（即下划线），接收起始下标值，然后丢弃，finish接收结束下标值
local _, finish = string.find("hello", "he")
print(finish)                              --输出 2
print(_)                                   --输出 1, `_` 只是一个普通变量,我们习惯上不会读取它的值


local t = {1, 3, 5}

print("all data:")
for i, v in ipairs(t) do
    print(i, v)
end

print("")
print("part data:")
for _,v in ipairs(t) do
    print(v)
end

function foo()
    return 1, 2, 3, 4
end

local _, _, bar = foo();    -- 我们只需要第三个
print(bar)   -- output: 3