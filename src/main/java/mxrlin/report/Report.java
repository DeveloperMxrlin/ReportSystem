package mxrlin.report;

import java.util.UUID;

public class Report {

    private String senderName;
    private String targetName;

    private UUID senderUUID;
    private UUID targetUUID;

    private int reason;
    private int reportID;

    public Report(String senderName, String targetName, UUID senderUUID, UUID targetUUID, int reason, int reportID) {
        this.senderName = senderName;
        this.targetName = targetName;
        this.senderUUID = senderUUID;
        this.targetUUID = targetUUID;
        this.reason = reason;
        this.reportID = reportID;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getTargetName() {
        return targetName;
    }

    public UUID getSenderUUID() {
        return senderUUID;
    }

    public UUID getTargetUUID() {
        return targetUUID;
    }

    public int getReason() {
        return reason;
    }

    public int getReportID() {
        return reportID;
    }
}
