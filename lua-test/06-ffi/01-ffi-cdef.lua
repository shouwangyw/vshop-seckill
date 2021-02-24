local ffi = require "ffi"
ffi.load('myffi', true)

ffi.cdef [[
int add(int x, int y);   /* don't forget to declare */
]]

local res = ffi.C.add(1, 2)
print(res)  -- output: 3   Note: please use luajit to run this script.