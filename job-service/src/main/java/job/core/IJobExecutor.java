package job.core;

import job.task.ITask;

/**
 * Created by zhou on 2016/1/21.
 * job执行器，绑定/解绑job，为job绑定监控
 */
public interface IJobExecutor {

    /**
     * 增加任务
     */
    void attachTask(ITask task);

    /**
     * 移除任务
     */
    void detachTask(ITask task);

    /**
     *发出通知，通知job启动
     */
    void notiy();
}
