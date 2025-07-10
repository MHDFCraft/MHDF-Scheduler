package cn.chengzhiya.mhdfscheduler.task;

import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public final class MHDFTask implements Task {

    private final Object taskHandle;
    private boolean isFolia;

    public MHDFTask(BukkitTask bukkitTask) {
        this.taskHandle = bukkitTask;
        this.isFolia = false;
    }

    public MHDFTask(ScheduledTask scheduledTask) {
        this.taskHandle = scheduledTask;
        this.isFolia = true;
    }


    @Override
    public Plugin getOwner() {
        if (isFolia) {
            return ((ScheduledTask) taskHandle).getOwningPlugin();
        } else {
            return ((BukkitTask) taskHandle).getOwner();
        }
    }

    @Override
    public boolean isCancelled() {
        if (isFolia) {
            return ((ScheduledTask) taskHandle).isCancelled();
        } else {
            return ((BukkitTask) taskHandle).isCancelled();
        }
    }

    @Override
    public void cancel() {
        if (isFolia) {
            ((ScheduledTask) taskHandle).cancel();
        } else {
            ((BukkitTask) taskHandle).cancel();
        }
    }

    @Override
    public int getTaskId() {
        if (isFolia) {
            return -1;  // Folia没有ID
        } else {
            return ((BukkitTask) taskHandle).getTaskId();
        }
    }

    @Override
    public boolean isFolia(){
        return isFolia;
    }

}