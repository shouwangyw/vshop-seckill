local _M = {}

local function get_name()
    return "Lucy"
end

function _M.greeting()
    print("hello " .. get_name())
end

return _M