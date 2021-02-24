local redis = require "resty.redis"
local red = redis:new()

function set_to_cache(key, value, expire)
    if not expire then
        expire = 0
    end
    local ngx_cache = ngx.shared.ngx_cache
    local succ, err, forcible = ngx_cache:set(key, value, expire)
    return succ
end

function get_from_cache(key)
    local ngx_cache = ngx.shared.ngx_cache
    local value = ngx_cache:get(key)
    if not value then
        -- 注意此处新建的redv,不能使用上面的value,会冲突
        local redv = get_from_redis(key)
        if not redv then
            ngx.say("redis cache not exists")
            return
        end
        set_to_cache(key, value, 60)
        return redv
    end
    ngx.say("get from cache")
    return value
end

function set_to_redis(key, value)
    red:set_timeout(100000)
    local ok, err = red:connect("192.168.254.128", 6379)
    if not ok then
        ngx.say("failed to connect: ", err)
        return
    end
    local ok, err = red:set(key, value)
    if not ok then
        ngx.say("failed set to redis: ", err)
        return
    end
    return ok
end

function get_from_redis(key)
    red:set_timeout(1000)
    local ok, err = red:connect("192.168.254.128", 6379)
    if not ok then
        ngx.say("failed to connect: ", err)
        return
    end

    local res, err = red:get(key)
    if not res then
        ngx.say("failed get redis cache: ", err)
        return ngx.null
    end
    ngx.say("get cache from redis.")
    return res
end

set_to_redis('dog', 'Tom')
local rs = get_from_cache('dog')
ngx.say(rs)