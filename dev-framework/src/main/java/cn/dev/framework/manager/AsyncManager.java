package cn.dev.framework.manager;

import cn.dev.common.util.spring.SpringUtil;

import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author YangXw
 * @date 2023/01/27 14:11
 * @description 异步任务管理器
 */
public class AsyncManager {

    private static final int DELAY_TIME = 10;
    private ScheduledExecutorService executor = SpringUtil.getBean("scheduledExecutorService");

    public AsyncManager() {
    }

    private static AsyncManager instance = new AsyncManager();

    /**
     * 单例模式
     */

    public static AsyncManager instance() {
        return instance;
    }

    /**
     * 执行任务
     * @param task
     */
    public void execute(TimerTask task) {
        executor.schedule(task, DELAY_TIME, TimeUnit.MILLISECONDS);
    }
}
