local function change(arg)
    -- change 函数，改变长方形的长和宽，使其各增长一倍
    arg.width = arg.width * 2
    arg.height = arg.height * 2
    return arg
end

local rectangle = { width = 20, height = 15 }
print("before change:", "width =", rectangle.width, "height = ", rectangle.height)
rectangle = change(rectangle)
print("after change:", "width =", rectangle.width, "height = ", rectangle.height)
