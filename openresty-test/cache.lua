local ngx_cache = ngx.shared.ngx_cache
local value = ngx_cache.get(key)
local succ, err, forcible = ngx_cache:set(key, value, expire)