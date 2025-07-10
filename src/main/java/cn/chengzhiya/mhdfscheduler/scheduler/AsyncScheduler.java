package cn.chengzhiya.mhdfscheduler.scheduler;

import cn.chengzhiya.mhdfscheduler.task.MHDFTask;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public final class AsyncScheduler {

    private final Object schedulerHandle;

    public AsyncScheduler() {
        if (MHDFScheduler.isFolia()) {
            schedulerHandle = Bukkit.getAsyncScheduler();
        } else {
            schedulerHandle = Bukkit.getScheduler();
        }
    }

    public MHDFTask runTask(@NotNull Plugin plugin, @NotNull Runnable task) {
        if (!MHDFScheduler.isFolia()) {
            BukkitScheduler scheduler = (BukkitScheduler) schedulerHandle;
            return new MHDFTask(scheduler.runTaskAsynchronously(plugin, task));
        } else {
            io.papermc.paper.threadedregions.scheduler.AsyncScheduler scheduler = (io.papermc.paper.threadedregions.scheduler.AsyncScheduler) schedulerHandle;
            return new MHDFTask(scheduler.runNow(plugin, (o) -> task.run()));
        }
    }

    public MHDFTask runTaskLater(@NotNull Plugin plugin, @NotNull Runnable task, long delay) {
        if (!MHDFScheduler.isFolia()) {
            BukkitScheduler scheduler = (BukkitScheduler) schedulerHandle;
            return new MHDFTask(scheduler.runTaskLaterAsynchronously(plugin, task, delay));
        } else {
            io.papermc.paper.threadedregions.scheduler.AsyncScheduler scheduler = (io.papermc.paper.threadedregions.scheduler.AsyncScheduler) schedulerHandle;
            return new MHDFTask(scheduler.runDelayed(plugin, (o) -> task.run(), delay * 50, TimeUnit.MILLISECONDS));
        }
    }

    public MHDFTask runTaskTimer(@NotNull Plugin plugin, @NotNull Runnable task, long delay, long period) {
        if (period < 1) period = 1;

        if (!MHDFScheduler.isFolia()) {
            BukkitScheduler scheduler = (BukkitScheduler) schedulerHandle;
            return new MHDFTask(scheduler.runTaskTimerAsynchronously(plugin, task, delay, period));
        } else {
            io.papermc.paper.threadedregions.scheduler.AsyncScheduler scheduler = (io.papermc.paper.threadedregions.scheduler.AsyncScheduler) schedulerHandle;
            return new MHDFTask(scheduler.runAtFixedRate(plugin, (o) -> task.run(), delay * 50, period * 50, TimeUnit.MILLISECONDS));
        }
    }

    public void cancel(@NotNull Plugin plugin) {
        if (!MHDFScheduler.isFolia()) {
            BukkitScheduler scheduler = (BukkitScheduler) schedulerHandle;
            scheduler.cancelTasks(plugin);
            return;
        } else {
            io.papermc.paper.threadedregions.scheduler.AsyncScheduler scheduler = (io.papermc.paper.threadedregions.scheduler.AsyncScheduler) schedulerHandle;
            scheduler.cancelTasks(plugin);
        }
    }
}