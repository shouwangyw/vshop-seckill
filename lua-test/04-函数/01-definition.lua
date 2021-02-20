-- 定义函数 max，用来求两个数的最大值，并返回
local function max(a, b)
    -- 使用局部变量 temp，保存最大值
    local temp = nil
    if (a > b) then
        temp = a
    else
        temp = b
    end
    -- 返回最大值
    return temp
end

-- 调用函数 max，找去 -12 和 20 中的最大值
local m = max(-12, 20)
print(m)  -- output: 20

local function func()
    --形参为空
    print("no parameter")
end

func()                  --函数调用，圆扩号不能省

