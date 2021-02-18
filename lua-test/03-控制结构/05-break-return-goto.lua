-- 计算最小的x,使从1到x的所有数相加和大于100
sum = 0
i = 1
while true do
    sum = sum + i
    if sum > 100 then
        break
    end
    i = i + 1
end
print("The result is " .. i)  -->output:The result is 14


local function add(x, y)
    return x + y
    --print("add: I will return the result " .. (x + y))
    --因为前面有个return，若不注释该语句，则会报错
end

local function is_positive(x)
    if x > 0 then
        return x .. " is positive"
    else
        return x .. " is non-positive"
    end

    --由于return只出现在前面显式的语句块，所以此语句不注释也不会报错
    --，但是不会被执行，此处不会产生输出
    print("function end!")
end

local sum = add(10, 20)
print("The sum is " .. sum)  -->output:The sum is 30
local answer = is_positive(-10)
print(answer)                -->output:-10 is non-positive

for i = 1, 3 do
    if i <= 2 then
        print(i, "yes continue")
        goto continue
    end

    print(i, " no continue")

    :: continue ::
    print([[i'm end]])
end

local function process(input)
    print("the input is", input)
    if input < 2 then
        goto failed
    end
    -- 更多处理流程和 goto err

    print("processing...")
    do
        return
    end
    :: failed ::
    print("handle error with input", input)
end

process(1)
process(3)