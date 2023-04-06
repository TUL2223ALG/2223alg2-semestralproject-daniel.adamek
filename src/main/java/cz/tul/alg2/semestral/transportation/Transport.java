package cz.tul.alg2.semestral.transportation;

import java.time.Duration;

public class Transport {
    private TypeOfTransportation type;

    private Duration transferTime;

    Transport(TypeOfTransportation type, Duration time) {
        this.type = type;
        this.transferTime = time;
    }

    public TypeOfTransportation getType() {
        return type;
    }

    public void setType(TypeOfTransportation type) {
        this.type = type;
    }

    public Duration getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(Duration transferTime) {
        this.transferTime = transferTime;
    }
}
