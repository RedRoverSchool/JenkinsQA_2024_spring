package school.redrover.domain;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Hudson {
    @SerializedName("_class")
    private String clazz;
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
    private String url;
    private boolean useCrumbs;
    private boolean useSecurity;
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

    public String getUrl() {
        return url;
    }

    public boolean isUseCrumbs() {
        return useCrumbs;
    }

    public boolean isUseSecurity() {
        return useSecurity;
    }

    public List<View> getViews() {
        return views;
    }

    @Override
    public String toString() {
        return "Hudson{" +
                ", assignedLabels=" + assignedLabels + '\n' +
                ", mode='" + mode + '\n' +
                ", nodeDescription='" + nodeDescription + '\n' +
                ", nodeName='" + nodeName + '\n' +
                ", numExecutors=" + numExecutors + '\n' +
                ", description='" + description + '\n' +
                ", jobs=" + jobs + '\n' +
                ", overallLoad=" + overallLoad + '\n' +
                ", primaryView=" + primaryView + '\n' +
                ", quietDownReason='" + quietDownReason + '\n' +
                ", quietingDown=" + quietingDown + '\n' +
                ", slaveAgentPort=" + slaveAgentPort + '\n' +
                ", unlabeledLoad=" + unlabeledLoad + '\n' +
                ", url='" + url + '\n' +
                ", useCrumbs=" + useCrumbs + '\n' +
                ", useSecurity=" + useSecurity + '\n' +
                ", views=" + views +
                '}';
    }
}
