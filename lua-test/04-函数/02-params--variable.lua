local function func(...)
    -- 形参为 ... ,表示函数采用变长参数

    local temp = { ... }                     -- 访问的时候也要使用 ...
    local ans = table.concat(temp, " ")    -- 使用 table.concat 库函数对数
    -- 组内容使用 " " 拼接成字符串。
    print(ans)
end

func(1, 2)        -- 传递了两个参数
func(1, 2, 3, 4)  -- 传递了四个参数

