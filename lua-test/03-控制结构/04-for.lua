for i = 1, 5 do
    print(i)
end

for i = 1, 10, 2 do
    print(i)
end

for i = 10, 1, -1 do
    print(i)
end

for i = 1, math.huge do
    if (0.3 * i ^ 3 - 20 * i ^ 2 - 500 >= 0) then
        print(i)
        break
    end
end

-- 打印数组a的所有值
local a = { "a", "b", "c", "d" }
for i, v in ipairs(a) do
    print("index:", i, " value:", v)
end

local days = {
    "Monday", "Tuesday", "Wednesday", "Thursday",
    "Friday", "Saturday", "Sunday"
}

local revDays = {}
for k, v in pairs(days) do
    revDays[v] = k
end

-- print value
for k, v in pairs(revDays) do
    print("k:", k, " v:", v)
end