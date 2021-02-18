local s = "hello world"
local i, j = string.find(s, "hello")
print(i, j)

local s = "hello world from Lua"
for w in string.gmatch(s, "%a+") do
    print(w)
end