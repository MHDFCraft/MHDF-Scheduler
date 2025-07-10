package cn.chengzhiya.mhdfscheduler.scheduler;

import cn.chengzhiya.mhdfscheduler.task.MHDFTask;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.NotNull;

public final class GlobalRegionScheduler {
    private final Object schedulerHandle;

    public GlobalRegionScheduler() {
        if (MHDFScheduler.isFolia()) {
            schedulerHandle = Bukkit.getGlobalRegionScheduler();
        } else {
            schedulerHandle = Bukkit.getScheduler();
        }
    }

    public MHDFTask runTask(@NotNull Plugin plugin, @NotNull Runnable task) {
        if (!MHDFScheduler.isFolia()) {
            BukkitScheduler scheduler = (BukkitScheduler) schedulerHandle;
            return new MHDFTask(scheduler.runTask(plugin, task));
        } else {
            io.papermc.paper.threadedregions.scheduler.GlobalRegionScheduler scheduler = (io.papermc.paper.threadedregions.scheduler.GlobalRegionScheduler) schedulerHandle;
            return new MHDFTask(scheduler.run(plugin, (o) -> task.run()));
        }
    }

    public MHDFTask runTaskLater(@NotNull Plugin plugin, @NotNull Runnable task, long delay) {
        if (delay < 1) {
            delay = 1;
        }

        if (!MHDFScheduler.isFolia()) {
            BukkitScheduler scheduler = (BukkitScheduler) schedulerHandle;
            return new MHDFTask(scheduler.runTaskLater(plugin, task, delay));
        } else {
            io.papermc.paper.threadedregions.scheduler.GlobalRegionScheduler scheduler = (io.papermc.paper.threadedregions.scheduler.GlobalRegionScheduler) schedulerHandle;
            return new MHDFTask(scheduler.runDelayed(plugin, (o) -> task.run(), delay));
        }
    }

    public MHDFTask runTaskTimer(@NotNull Plugin plugin, @NotNull Runnable task, long initialDelayTicks, long periodTicks) {
        if (initialDelayTicks < 1) {
            initialDelayTicks = 1;
        }
        if (periodTicks < 1) {
            periodTicks = 1;
        }

        if (!MHDFScheduler.isFolia()) {
            BukkitScheduler scheduler = (BukkitScheduler) schedulerHandle;
            return new MHDFTask(scheduler.runTaskTimer(plugin, task, initialDelayTicks, periodTicks));
        } else {
            io.papermc.paper.threadedregions.scheduler.GlobalRegionScheduler scheduler = (io.papermc.paper.threadedregions.scheduler.GlobalRegionScheduler) schedulerHandle;
            return new MHDFTask(scheduler.runAtFixedRate(plugin, (o) -> task.run(), initialDelayTicks, periodTicks));
        }
    }

    public void cancel(@NotNull Plugin plugin) {
        if (!MHDFScheduler.isFolia()) {
            BukkitScheduler scheduler = (BukkitScheduler) schedulerHandle;
            scheduler.cancelTasks(plugin);
        } else {
            io.papermc.paper.threadedregions.scheduler.GlobalRegionScheduler scheduler = (io.papermc.paper.threadedregions.scheduler.GlobalRegionScheduler) schedulerHandle;
            scheduler.cancelTasks(plugin);
        }
    }
}