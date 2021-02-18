print("Hello " .. "World")    -->打印 Hello World
print(0 .. 1)                 -->打印 01

str1 = string.format("%s-%s", "hello", "world")
print(str1)              -->打印 hello-world

str2 = string.format("%d-%s-%.2f", 123, "world", 1.21)
print(str2)              -->打印 123-world-1.21

local pieces = {}
for i, elem in ipairs(my_list) do
    pieces[i] = my_process(elem)
end
local res = table.concat(pieces)