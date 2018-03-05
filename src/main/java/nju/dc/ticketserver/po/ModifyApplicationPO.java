package nju.dc.ticketserver.po;

//场馆信息修改
public class ModifyApplicationPO {

    private VenuePO preVenuePO; //修改前
    private VenuePO postVenuePO; //修改后
    private String applicationTime;
    private String state;

    public ModifyApplicationPO() {
    }

    public VenuePO getPreVenuePO() {
        return preVenuePO;
    }

    public void setPreVenuePO(VenuePO preVenuePO) {
        this.preVenuePO = preVenuePO;
    }

    public VenuePO getPostVenuePO() {
        return postVenuePO;
    }

    public void setPostVenuePO(VenuePO postVenuePO) {
        this.postVenuePO = postVenuePO;
    }

    public String getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(String applicationTime) {
        this.applicationTime = applicationTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "ModifyApplicationPO{" +
                "preVenuePO=" + preVenuePO +
                ", postVenuePO=" + postVenuePO +
                ", applicationTime='" + applicationTime + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
