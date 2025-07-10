package cn.chengzhiya.mhdfscheduler.task;

import org.bukkit.plugin.Plugin;

public interface Task {
    Plugin getOwner();

    boolean isCancelled();

    void cancel();

    int getTaskId();

    boolean isFolia();

}
