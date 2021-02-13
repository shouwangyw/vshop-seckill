package com.veli.vshop.seckill.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.util.RamUsageEstimator;
import org.springframework.util.DigestUtils;

import java.util.UUID;

/**
 * @author yangwei
 * @date 2021-02-10 20:22
 */
public class CommonUtils {
    private CommonUtils() {}

    public static String guid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String md5(String source) {
        if (StringUtils.isBlank(source)) {
            return null;
        }
        return DigestUtils.md5DigestAsHex(source.getBytes());
    }

    public static String size(Object obj) {

        return RamUsageEstimator.humanSizeOf(obj);
    }
}
