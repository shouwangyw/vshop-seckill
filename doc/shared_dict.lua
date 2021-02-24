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