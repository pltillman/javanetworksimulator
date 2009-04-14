import java.io.Serializable;

public class Car implements Serializable {

    private int type, interval;
    private Boolean wash, wax, rinse;
    private static final long serialVersionUID = 234234;


    public Car(int t, int i) {
        this.type = t;
        this.interval = i;
    }

    public void setInterval(int i) {
        this.interval = i;
    }
    public void setWash(Boolean f) {
        this.wash = f;
    }

    public void setWax(Boolean f) {
        this.wax = f;
    }

    public void setType(int t) {
        this.type = t;
    }

    public Boolean getWash() {
        return this.wash;
    }
    public int getInterval() {
        return this.interval;
    }
    public Boolean getWax() {
        return this.wax;
    }

    public int getType() {
        return this.type;
    }
}
