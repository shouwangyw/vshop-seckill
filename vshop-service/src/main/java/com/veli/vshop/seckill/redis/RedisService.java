package com.veli.vshop.seckill.redis;

import com.veli.vshop.seckill.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author yangwei
 * @date 2020-10-11 23:18
 */
@Slf4j
@Service
public class RedisService {
    @Resource
    private ValueOperations<String, Integer> opsForValueInt;
    @Resource
    private ValueOperations<String, Long> opsForValueLong;
    @Resource
    private ValueOperations<String, String> opsForValue;
    @Resource
    private ValueOperations<String, Object> opsForValueObj;

    //-- region -- String 类型

    /********************************************** String 类型 *******************************************/
    public void setIntValue(final String key, final Integer val) {
        opsForValueInt.set(key, val);
    }

    public Integer getIntValue(final String key) {
        return opsForValueInt.get(key);
    }

    public void setLongValue(final String key, final Long val) {
        opsForValueLong.set(key, val);
    }

    public Long getLongValue(final String key) {
        return opsForValueLong.get(key);
    }

    public void setJsonObj(final String key, final Object obj) {
        opsForValue.set(key, JsonUtils.toStr(obj));
    }

    public <T> T getJsonObj(final String key, Class<T> clazz) {
        return JsonUtils.toObj(opsForValue.get(key), clazz);
    }

    public void setObjValue(final String key, final Object obj, final long timeout, final TimeUnit unit) {
        opsForValueObj.set(key, obj, timeout, unit);
    }

    public <T> T getObjValue(final String key) {
        return (T) opsForValueObj.get(key);
    }
    //-- endregion
}
