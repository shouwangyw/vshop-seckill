x = 1
sum = 0

while x <= 5 do
    sum = sum + x
    x = x + 1
end
print(sum)  -->output 15

local t = {1, 3, 5, 8, 11, 18, 21}

local i
for i, v in ipairs(t) do
    if 11 == v then
        print("index[" .. i .. "] have right value[11]")
        break
    end
end