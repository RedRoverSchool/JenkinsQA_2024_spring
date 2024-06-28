package school.redrover.domain;

import java.util.List;

public class Hudson extends BaseHudson {
    private List<AssignedLabel> assignedLabels;
    private String mode;
    private String nodeDescription;
    private String nodeName;
    private int numExecutors;
    private String description;
    private List<Job> jobs;
    private OverallLoad overallLoad;
    private PrimaryView primaryView;
    private String quietDownReason;
    private boolean quietingDown;
    private int slaveAgentPort;
    private UnlabeledLoad unlabeledLoad;
    private List<View> views;

    public List<AssignedLabel> getAssignedLabels() {
        return assignedLabels;
    }

    public String getMode() {
        return mode;
    }

    public String getNodeDescription() {
        return nodeDescription;
    }

    public String getNodeName() {
        return nodeName;
    }

    public int getNumExecutors() {
        return numExecutors;
    }

    public String getDescription() {
        return description;
    }

    public OverallLoad getOverallLoad() {
        return overallLoad;
    }

    public PrimaryView getPrimaryView() {
        return primaryView;
    }

    public String getQuietDownReason() {
        return quietDownReason;
    }

    public boolean isQuietingDown() {
        return quietingDown;
    }

    public int getSlaveAgentPort() {
        return slaveAgentPort;
    }

    public UnlabeledLoad getUnlabeledLoad() {
        return unlabeledLoad;
    }

    public List<View> getViews() {
        return views;
    }
}
