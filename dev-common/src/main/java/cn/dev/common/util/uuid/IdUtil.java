package cn.dev.common.util.uuid;

import java.util.UUID;

/**
 * ID生成器工具类
 * 
 * @author ruoyi
 */
public class IdUtil
{
    /**
     * 获取随机UUID
     * 
     * @return 随机UUID
     */
    public static String fastUUID()
    {
        return UUID.randomUUID().toString();
    }

    /**
     * 简化的UUID，去掉了横线
     * 
     * @return 简化的UUID，去掉了横线
     */
    public static String fastSimpleUUID()
    {
        return UUID.randomUUID().toString();
    }
}
