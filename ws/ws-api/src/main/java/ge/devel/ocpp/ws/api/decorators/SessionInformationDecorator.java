package ge.devel.ocpp.ws.api.decorators;

import eu.chargetime.ocpp.model.SessionInformation;
import eu.chargetime.ocpp.model.core.ChargePointStatus;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.UUID;

@Getter
public class SessionInformationDecorator {
    private final UUID sessionIndex;
    private final String endpoint;
    private final SessionInformation sessionInformation;

    @Setter
    private ChargePointStatus status;

    public SessionInformationDecorator(@NonNull final UUID sessionIndex,
                                       @NonNull final String endpoint,
                                       @NonNull final SessionInformation sessionInformation,
                                       @NonNull final ChargePointStatus status) {
        this.sessionIndex = sessionIndex;
        this.endpoint = endpoint;
        this.sessionInformation = sessionInformation;
        this.status = status;
    }

    public String getIdentity() {
        return this.getSessionInformation().getIdentifier();
    }

    @Override
    public String toString() {
        return "SessionInformationDecorator{" +
                "sessionIndex=" + sessionIndex +
                ", endpoint='" + endpoint + '\'' +
                ", sessionInformation=" + OcppToStringDecorator.toString(sessionInformation) +
                ", status=" + status.name() +
                '}';
    }
}
