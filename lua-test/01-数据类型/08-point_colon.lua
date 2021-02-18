local str = "abcde"
print("case1:", str:sub(1, 2))
print("case2:", str.sub(str, 1, 2))

obj = { x = 20 }
function obj:fun1()
    print(self.x)
end
-- 等价于
obj = {x = 20}
function obj.fun1(self)
    print(self.x)
end
