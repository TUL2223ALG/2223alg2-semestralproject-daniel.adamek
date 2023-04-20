package cz.tul.alg2.semestral.transportation;

public class Transport {
    private transportationType type;

    private int transferTime;

    public Transport(transportationType type, int duration) {
        this.type = type;
        this.transferTime = duration;
    }

    public transportationType getType() {
        return type;
    }

    public void setType(transportationType type) {
        this.type = type;
    }

    public int getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(int transferTime) {
        this.transferTime = transferTime;
    }
}
