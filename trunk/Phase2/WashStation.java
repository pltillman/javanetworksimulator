

public class WashStation {

    private int stationSize;
    private Boolean busy;

    public WashStation(int s) {
        this.stationSize = s;
    }

    public void setStationSize(int s) {
        this.stationSize = s;
    }
    public int getStationSize() {
        return this.stationSize;
    }
    public void setBusy(Boolean b) {
        this.busy = b;
    }
    public Boolean getBusy() {
        return this.busy;
    }

}
