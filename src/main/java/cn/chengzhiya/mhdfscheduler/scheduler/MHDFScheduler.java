package cn.chengzhiya.mhdfscheduler.scheduler;

import cn.chengzhiya.mhdfscheduler.task.MHDFTask;
import lombok.Getter;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public final class MHDFScheduler {
    @Getter
    private static boolean folia;

    private static AsyncScheduler asyncScheduler;
    private static EntityScheduler entityScheduler;
    private static GlobalRegionScheduler globalRegionScheduler;
    private static RegionScheduler regionScheduler;

    static {
        try {
            Class.forName("io.papermc.paper.threadedregions.RegionizedServer");
            folia = true;
        } catch (ClassNotFoundException e) {
            folia = false;
        }
    }

    public static AsyncScheduler getAsyncScheduler() {
        if (asyncScheduler == null) {
            asyncScheduler = new AsyncScheduler();
        }
        return asyncScheduler;
    }

    public static EntityScheduler getEntityScheduler() {
        if (entityScheduler == null) {
            entityScheduler = new EntityScheduler();
        }
        return entityScheduler;
    }

    public static GlobalRegionScheduler getGlobalRegionScheduler() {
        if (globalRegionScheduler == null) {
            globalRegionScheduler = new GlobalRegionScheduler();
        }
        return globalRegionScheduler;
    }

    public static RegionScheduler getRegionScheduler() {
        if (regionScheduler == null) {
            regionScheduler = new RegionScheduler();
        }
        return regionScheduler;
    }

    public static MHDFTask runAsyncTaskWithCallback(@NotNull Plugin plugin, @NotNull Runnable asyncTask, @NotNull Runnable callback) {
        return getAsyncScheduler().runTask(plugin, () -> {
            asyncTask.run();
            getGlobalRegionScheduler().runTask(plugin, callback);
        });
    }

    public static MHDFTask runAsyncTaskLaterWithCallback(@NotNull Plugin plugin, @NotNull Runnable asyncTask, @NotNull Runnable callback, long delayTicks) {
        return getAsyncScheduler().runTaskLater(plugin, () -> {
            asyncTask.run();
            getGlobalRegionScheduler().runTaskLater(plugin, callback, delayTicks);
        }, delayTicks);
    }

    public static MHDFTask runAsyncTasksWithCallback(@NotNull Plugin plugin, @NotNull List<Runnable> asyncTasks, @NotNull Runnable callback) {
        List<Runnable> tasks = new ArrayList<>(asyncTasks);
        tasks.add(callback);
        return getAsyncScheduler().runTask(plugin, () -> {
            for (Runnable task : tasks) {
                task.run();
            }
            getGlobalRegionScheduler().runTask(plugin, callback);
        });
    }

    public static MHDFTask runAsyncTasksWithCallback(@NotNull Plugin plugin, @NotNull List<Runnable> asyncTasks, @NotNull Runnable callback, long delayTicks) {
        List<Runnable> tasks = new ArrayList<>(asyncTasks);
        tasks.add(callback);
        return getAsyncScheduler().runTaskLater(plugin, () -> {
            for (Runnable task : tasks) {
                task.run();
            }
            getGlobalRegionScheduler().runTask(plugin, callback);
        }, delayTicks);
    }

    public static void cancel(@NotNull Plugin plugin) {
        getGlobalRegionScheduler().cancel(plugin);
        getAsyncScheduler().cancel(plugin);
    }
}