package cn.chengzhiya.mhdfscheduler.scheduler;

import cn.chengzhiya.mhdfscheduler.task.MHDFTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class EntityScheduler {
    private BukkitScheduler bukkitScheduler;

    public EntityScheduler() {
        if (!MHDFScheduler.isFolia()) {
            bukkitScheduler = Bukkit.getScheduler();
        }
    }

    public MHDFTask runTask(@NotNull Plugin plugin, @NotNull Entity entity, @NotNull Runnable task, @Nullable Runnable retired) {
        if (!MHDFScheduler.isFolia()) return new MHDFTask(bukkitScheduler.runTask(plugin, task));
        return new MHDFTask(entity.getScheduler().run(plugin, (o) -> task.run(), retired));
    }

    public MHDFTask runTaskLater(@NotNull Plugin plugin, @NotNull Entity entity, @NotNull Runnable task, @Nullable Runnable retired, long delayTicks) {
        if (delayTicks < 1) delayTicks = 1;

        if (!MHDFScheduler.isFolia()) return new MHDFTask(bukkitScheduler.runTaskLater(plugin, task, delayTicks));
        return new MHDFTask(entity.getScheduler().runDelayed(plugin, (o) -> task.run(), retired, delayTicks));
    }

    public MHDFTask runTaskTimer(@NotNull Plugin plugin, @NotNull Entity entity, @NotNull Runnable task, @Nullable Runnable retired, long initialDelayTicks, long periodTicks) {
        if (initialDelayTicks < 1) initialDelayTicks = 1;
        if (periodTicks < 1) periodTicks = 1;

        if (!MHDFScheduler.isFolia()) return new MHDFTask(bukkitScheduler.runTaskTimer(plugin, task, initialDelayTicks, periodTicks));
        return new MHDFTask(entity.getScheduler().runAtFixedRate(plugin, (o) -> task.run(), retired, initialDelayTicks, periodTicks));
    }
}