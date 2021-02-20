--local args = {...} or {}
--method_name(unpack(args, 1, table.maxn(args)))

local function run(x, y)
    print('run', x, y)
end

local function attack(targetId)
    print('targetId', targetId)
end


local function do_action(method, ...)
    local args = {...} or {}
    method(unpack(args, 1, table.maxn(args)))
end

do_action(run, 1, 2)        -- output: run 1 2
do_action(attack, 1111)     -- output: targetId    1111