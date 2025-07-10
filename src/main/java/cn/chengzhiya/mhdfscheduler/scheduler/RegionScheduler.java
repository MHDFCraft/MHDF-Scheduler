package cn.chengzhiya.mhdfscheduler.scheduler;

import cn.chengzhiya.mhdfscheduler.task.MHDFTask;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.NotNull;

public final class RegionScheduler {
    private final Object schedulerHandle;

    public RegionScheduler() {
        if (MHDFScheduler.isFolia()) {
            schedulerHandle = Bukkit.getRegionScheduler();
        } else {
            schedulerHandle = Bukkit.getScheduler();
        }
    }

    public MHDFTask runTask(@NotNull Plugin plugin, @NotNull World world, int chunkX, int chunkZ, @NotNull Runnable task) {
        if (!MHDFScheduler.isFolia()) {
            BukkitScheduler scheduler = (BukkitScheduler) schedulerHandle;
            return new MHDFTask(scheduler.runTask(plugin, task));
        } else {
            io.papermc.paper.threadedregions.scheduler.RegionScheduler scheduler =
                    (io.papermc.paper.threadedregions.scheduler.RegionScheduler) schedulerHandle;
            return new MHDFTask(scheduler.run(plugin, world, chunkX, chunkZ, (o) -> task.run()));
        }
    }

    public MHDFTask runTask(@NotNull Plugin plugin, @NotNull Location location, @NotNull Runnable task) {
        if (!MHDFScheduler.isFolia()) {
            BukkitScheduler scheduler = (BukkitScheduler) schedulerHandle;
            return new MHDFTask(scheduler.runTask(plugin, task));
        } else {
            io.papermc.paper.threadedregions.scheduler.RegionScheduler scheduler =
                    (io.papermc.paper.threadedregions.scheduler.RegionScheduler) schedulerHandle;
            return new MHDFTask(scheduler.run(plugin, location, (o) -> task.run()));
        }
    }

    public MHDFTask runTaskLater(@NotNull Plugin plugin, @NotNull World world, int chunkX, int chunkZ,
                                 @NotNull Runnable task, long delayTicks) {
        if (delayTicks < 1) {
            delayTicks = 1;
        }

        if (!MHDFScheduler.isFolia()) {
            BukkitScheduler scheduler = (BukkitScheduler) schedulerHandle;
            return new MHDFTask(scheduler.runTaskLater(plugin, task, delayTicks));
        } else {
            io.papermc.paper.threadedregions.scheduler.RegionScheduler scheduler =
                    (io.papermc.paper.threadedregions.scheduler.RegionScheduler) schedulerHandle;
            return new MHDFTask(scheduler.runDelayed(plugin, world, chunkX, chunkZ, (o) -> task.run(), delayTicks));
        }
    }

    public MHDFTask runTaskLater(@NotNull Plugin plugin, @NotNull Location location,
                                 @NotNull Runnable task, long delayTicks) {
        if (delayTicks < 1) {
            delayTicks = 1;
        }

        if (!MHDFScheduler.isFolia()) {
            BukkitScheduler scheduler = (BukkitScheduler) schedulerHandle;
            return new MHDFTask(scheduler.runTaskLater(plugin, task, delayTicks));
        } else {
            io.papermc.paper.threadedregions.scheduler.RegionScheduler scheduler =
                    (io.papermc.paper.threadedregions.scheduler.RegionScheduler) schedulerHandle;
            return new MHDFTask(scheduler.runDelayed(plugin, location, (o) -> task.run(), delayTicks));
        }
    }

    public MHDFTask runTaskTimer(@NotNull Plugin plugin, @NotNull World world, int chunkX, int chunkZ,
                                 @NotNull Runnable task, long initialDelayTicks, long periodTicks) {
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
            io.papermc.paper.threadedregions.scheduler.RegionScheduler scheduler =
                    (io.papermc.paper.threadedregions.scheduler.RegionScheduler) schedulerHandle;
            return new MHDFTask(scheduler.runAtFixedRate(plugin, world, chunkX, chunkZ,
                    (o) -> task.run(), initialDelayTicks, periodTicks));
        }
    }

    public MHDFTask runTaskTimer(@NotNull Plugin plugin, @NotNull Location location,
                                 @NotNull Runnable task, long initialDelayTicks, long periodTicks) {
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
            io.papermc.paper.threadedregions.scheduler.RegionScheduler scheduler =
                    (io.papermc.paper.threadedregions.scheduler.RegionScheduler) schedulerHandle;
            return new MHDFTask(scheduler.runAtFixedRate(plugin, location,
                    (o) -> task.run(), initialDelayTicks, periodTicks));
        }
    }
}