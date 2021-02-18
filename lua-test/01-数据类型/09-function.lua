local function foo()
    print("in the function")
    -- do something
    local x = 10
    local y = 20
    return x + y;
end
-- 把函数赋给变量
local a = foo

print(a())

function foo()
end
-- 等价于
foo = function() end

local function foo()
end
-- 等价于
local foo = function()
end