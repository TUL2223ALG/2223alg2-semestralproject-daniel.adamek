package cz.tul.alg2.semestral.transportation;

public class Transport {
    private TransportationType type;

    private int transferTime;

    public Transport(TransportationType type, int duration) {
        this.type = type;
        this.transferTime = duration;
    }

    public TransportationType getType() {
        return type;
    }

    public void setType(TransportationType type) {
        this.type = type;
    }

    public int getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(int transferTime) {
        this.transferTime = transferTime;
    }
}
