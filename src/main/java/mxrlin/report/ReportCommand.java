package mxrlin.report;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class ReportCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        
        if(!(sender instanceof Player)){
            
            // NO PLAYER

            if(!sender.hasPermission("reportsystem.list") && !sender.hasPermission("reportsystem.*")){
                sender.sendMessage(ReportSystem.reportSystem.prefix + "You dont have the required permissions to execute that command.");
            }

            List<Report> reports = ReportSystem.reportSystem.reports;
            sender.sendMessage("§8§m-----§c ReportSystem §8§m-----");
            sender.sendMessage("");
            sender.sendMessage("§cPlayer is Offline §8/ §aPlayer is Online");
            sender.sendMessage("");
            for(Report report : reports){
                sender.sendMessage("§7#" + report.getReportID() + " §8| §" + (playerIsOnline(report.getSenderUUID()) ? "a" : "c") + report.getSenderName() +
                        " reported " + (playerIsOnline(report.getTargetUUID()) ? "a" : "c") + report.getTargetName() + " §7for \"" + Reasons.getReasonByID(report.getReason()).getFormatted() + "\"");
            }
            sender.sendMessage("");
            sender.sendMessage("§8§m-----§c ReportSystem §8§m-----");
            
            return false;
        }
        
        /*
        Syntax:
        
        /report <Name> <Reason>
        /report list
        /report remove <ID>
         */
        
        Player player = (Player) sender;
        
        if(args.length == 0){
            player.sendMessage(ReportSystem.reportSystem.prefix + "Unknown Command. Type §c/report help§7 for help.");
            return false;
        }

        List<Report> reports = ReportSystem.reportSystem.reports;
        
        switch (args[0].toLowerCase()){
            case "list":
                
                if(!player.hasPermission("reportsystem.list") && !player.hasPermission("reportsystem.*")){
                    player.sendMessage(ReportSystem.reportSystem.prefix + "You dont have the required permissions to execute that command."); 
                }

                player.sendMessage("§8§m-----§c ReportSystem §8§m-----");
                player.sendMessage("");
                player.sendMessage("§cPlayer is Offline §8/ §aPlayer is Online");
                player.sendMessage("");
                for(Report report : reports){
                    player.sendMessage("§7#" + report.getReportID() + " §8| §" + (playerIsOnline(report.getSenderUUID()) ? "a" : "c") + report.getSenderName() +
                            " reported " + (playerIsOnline(report.getTargetUUID()) ? "a" : "c") + report.getTargetName() + " §7for \"" + Reasons.getReasonByID(report.getReason()).getFormatted() + "\"");
                }
                player.sendMessage("");
                player.sendMessage("§8§m-----§c ReportSystem §8§m-----");
                break;
            case "remove":

                if(!player.hasPermission("reportsystem.remove") && !player.hasPermission("reportsystem.*")){
                    player.sendMessage(ReportSystem.reportSystem.prefix + "You dont have the required permissions to execute that command.");
                }

                if(args.length < 2){
                    player.sendMessage(ReportSystem.reportSystem.prefix + "To remove a report, please type §c/report remove <ID>");
                    return false;
                }

                try{
                    int id = Integer.parseInt(args[1]);
                    Report targetReport = null;
                    for(Report report : reports){
                        if(report.getReportID() == id) {
                            targetReport = report;
                            break;
                        }
                    }

                    if(targetReport == null){
                        player.sendMessage(ReportSystem.reportSystem.prefix + "There is no Report with the ID §c" + id);
                    }

                    ReportSystem.reportSystem.reports.remove(targetReport);
                    player.sendMessage(ReportSystem.reportSystem.prefix + "You removed the Report with the ID §c" + id);

                }catch (NumberFormatException exception){
                    player.sendMessage(ReportSystem.reportSystem.prefix + "Please use a number as the ID of the Report.");
                }

                break;
            case "reasons":
                player.sendMessage("§8§m-----§c ReportSystem §8§m-----");
                player.sendMessage("");
                for(Reasons reason : Reasons.values()){
                    player.sendMessage("§7#" + reason.getId() + "§8 = §7" + reason.getFormatted());
                }
                player.sendMessage(" ");
                player.sendMessage("§8§m-----§c ReportSystem §8§m-----");
                break;
            case "help":
                player.sendMessage("§8§m-----§c ReportSystem §8§m-----");
                player.sendMessage(" ");
                player.sendMessage("§7/report help §8| §7See help.");
                player.sendMessage("§7/report <Name> <Reason> §8| Report a player.");
                player.sendMessage("§7/report reasons §8| See all the existing reasons.");
                player.sendMessage(" ");
                player.sendMessage("§8§m-----§c ReportSystem §8§m-----");
                break;
            default:
                if(args.length < 2){
                    player.sendMessage(ReportSystem.reportSystem.prefix + "If you want to report someone, please use §c/report <Name> <Reason>§7. All the reasons and their ID you can see under §c/report reasons");
                }

                try{

                    String target = args[0];
                    int id = Integer.parseInt(args[1]);

                    Player targetPlayer = Bukkit.getPlayer(target);
                    if(targetPlayer == null){
                        player.sendMessage(ReportSystem.reportSystem.prefix + "The player §c" + target + "§7 isn't online.");
                        return false;
                    }

                    Report newReport = new Report(player.getName(), target, player.getUniqueId(), targetPlayer.getUniqueId(), id, new Random().nextInt(8999) + 1000);
                    ReportSystem.reportSystem.reports.add(newReport);
                    player.sendMessage(ReportSystem.reportSystem.prefix + "You created a report against §c" + targetPlayer.getName() + "§7 with the Reason ID " + id + " (" + Reasons.getReasonByID(id).getFormatted() + ")");

                }catch (NumberFormatException exception){
                    player.sendMessage(ReportSystem.reportSystem.prefix + "Please use the ID of the reason.");
                }

                break;
        }
        
        return false;
    }
    
    private boolean playerIsOnline(UUID uuid){
        return Bukkit.getPlayer(uuid) != null;
    } 
    
}
