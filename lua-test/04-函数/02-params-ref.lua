function change(arg)
    --change函数，改变长方形的长和宽，使其各增长一倍
    --表arg不是表rectangle的拷贝，他们是同一个表
    arg.width = arg.width * 2
    arg.height = arg.height * 2
    -- 没有return语句了
end

local rectangle = { width = 20, height = 15 }
print("before change:", "width = ", rectangle.width, "height = ", rectangle.height)
change(rectangle)
print("after change:", "width = ", rectangle.width, "height =", rectangle.height)

