package cz.tul.alg2.semestral.transportation;

import java.time.Duration;

public class Transport {
    private TypeOfTransportation type;

    private int transferTime;

    public Transport(TypeOfTransportation type, int duration) {
        this.type = type;
        this.transferTime = duration;
    }

    public TypeOfTransportation getType() {
        return type;
    }

    public void setType(TypeOfTransportation type) {
        this.type = type;
    }

    public int getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(int transferTime) {
        this.transferTime = transferTime;
    }
}
