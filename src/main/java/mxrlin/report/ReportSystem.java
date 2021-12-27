package mxrlin.report;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class ReportSystem extends JavaPlugin {

    public String prefix = "§4Report§cSystem §8| §7";
    public List<Report> reports;

    public static ReportSystem reportSystem;

    @Override
    public void onEnable() {
        reportSystem = this;
        getCommand("report").setExecutor(new ReportCommand());
    }

    @Override
    public void onDisable() {

    }

}
