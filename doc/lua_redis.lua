-- 引入redis库
local redis = require("resty.redis")
-- 调用方法，获取redis对象
local red = redis:new()

-- 基于lua+redis实现缓存
local connect_redis = function()
    red:set_timeout(100000)
    local ok, err = red:connect("192.168.254.128", 6379)
    if not ok then
        ngx.say("failed to connect: ", err)
        return false
    end
    return true
end
-- 添加缓存实现
local set_to_redis = function (key, value)
    if not connect_redis() then
        return
    end

    local ok, err = red:set(key, value)
    if not ok then
        ngx.say("failed set to redis: ", err)
        return
    end
    return ok
end
-- 获取缓存实现
local get_from_redis = function(key)
    if not connect_redis() then
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


-- 基于内存字典实现缓存
-- 添加缓存实现
local set_to_cache = function(key, value, expire)
    if not expire then
        expire = 0
    end
    -- 获取本地内存字典对象
    local ngx_cache = ngx.shared.ngx_cache
    -- 向本地内存字典添加缓存数据
    local succ, err, forcible = ngx_cache:set(key, value, expire)
    return succ
end
-- 获取缓存实现
local get_from_cache = function(key)
    -- 获取本地内存字典对象
    local ngx_cache = ngx.shared.ngx_cache
    -- 从本地内存字典对象中获取数据
    local value = ngx_cache:get(key)
    -- 如果内存字典中没有缓存数据，则从redis中获取数据
    if not value then
        local rev, err = get_from_redis(key)
        if not rev then
            ngx.say("redis cache not exists")
            return
        end
        -- 把redis缓存数据放入本地内存字典
        set_to_cache(key, rev, 60)
    end
    return value
end

-- 利用 Lua 脚本实现一些简单的业务
-- 获取请求参数对象
local params = ngx.req.get_uri_args()
-- 获取参数
local id = params.id
local cache_key = "seckill_goods_" .. id
-- 先从内存字典获取缓存数据
local goods = get_from_cache(cache_key)
-- 若内存字典中没有数据，则从后端服务（缓存，数据库）查询数据，完毕在放入内存字典缓存即可
if goods == nil then
    local res = ngx.location.capture("/proxy/http/192.168.254.128/9000/seckill/goods/detail/" .. id)
    -- 获取查询结果
    goods = res.body
    -- 向本地内存字典添加缓存数据
    set_to_cache(cache_key, goods, 60)
end
-- 返回结果
ngx.say(goods)