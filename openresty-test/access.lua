ngx.log(ngx.INFO,"access")
-- 获取客户端IP
local clientIp = ngx.var.remote_addr
ngx.log(ngx.DEBUG, "clientIp: " .. clientIp)
-- 判断是否是黑名单
if clientIp == '127.0.0.1' then
    return ngx.exit(ngx.HTTP_FORBIDDEN)
else
    ngx.say(clientIp)
end

