package ge.devel.ocpp.ws.api;

import eu.chargetime.ocpp.model.core.ChargePointStatus;
import ge.devel.ocpp.ws.api.decorators.SessionInformationDecorator;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ServerEventHandler {

    Optional<String> getChargePointIdentifierBySession(final UUID sessionIndex);

    Optional<UUID> getSessionByIdentifier(final String identifier);

    void updateStatus(final UUID sessionIndex, final ChargePointStatus status );

    List<SessionInformationDecorator> getActiveSessionsInfo();

    List<UUID> getActiveSessions() ;

    Optional<SessionInformationDecorator> getSessionInformationBySession(final UUID sessionIndex);

    Optional<SessionInformationDecorator> getSessionInformationByIdentifier(final String identifier);

}
