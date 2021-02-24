local ffi = require "ffi"

local c_str_t = ffi.typeof("const char*")
local c_str = ffi.cast(c_str_t, str)

local uintptr_t = ffi.typeof("uintptr_t")
tonumber(ffi.cast(uintptr_t, c_str))
