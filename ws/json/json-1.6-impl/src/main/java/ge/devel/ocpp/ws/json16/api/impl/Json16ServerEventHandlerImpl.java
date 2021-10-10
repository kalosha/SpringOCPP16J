package ge.devel.ocpp.ws.json16.api.impl;

import eu.chargetime.ocpp.ServerEvents;
import eu.chargetime.ocpp.model.SessionInformation;
import eu.chargetime.ocpp.model.core.ChargePointStatus;
import ge.devel.ocpp.ws.api.ServerEventHandler;
import ge.devel.ocpp.ws.api.decorators.SessionInformationDecorator;
import ge.devel.ocpp.ws.api.impl.AbstractRequestHandlerValidator;
import ge.devel.ocpp.ws.api.impl.OcppServerEventHandlerImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component("Json16ServerEventHandler")
public class Json16ServerEventHandlerImpl extends AbstractRequestHandlerValidator implements ServerEventHandler, ServerEvents {

    @Value("${ge.devel.ocpp.json16.server.endpoint}")
    private String endpoint;


    private final OcppServerEventHandlerImpl ocppServer;

    public Json16ServerEventHandlerImpl(@Qualifier("OcppServerEventHandler") final OcppServerEventHandlerImpl ocppServer) {
        this.ocppServer = ocppServer;
        log.info("JSONServerEvents initialized");
    }

    @Override
    public void newSession(final UUID sessionIndex, final SessionInformation information) {
        this.ocppServer.newSession(this.endpoint, sessionIndex, information);
    }

    @Override
    public void lostSession(final UUID sessionIndex) {
        this.ocppServer.lostSession(sessionIndex);
    }

    @Override
    public Optional<String> getChargePointIdentifierBySession(final UUID sessionIndex){
        return this.ocppServer.getChargePointIdentifierBySession(sessionIndex);
    }

    @Override
    public Optional<UUID> getSessionByIdentifier(final String identifier){
        return this.ocppServer.getSessionByIdentifier(identifier);
    }

    @Override
    public void updateStatus(final UUID sessionIndex, final ChargePointStatus status ){
        this.ocppServer.updateStatus(sessionIndex, status);
    }

    @Override
    public List<SessionInformationDecorator> getActiveSessionsInfo() {
        return this.ocppServer.getActiveSessionsInfo();
    }

    @Override
    public List<UUID> getActiveSessions() {
        return this.ocppServer.getActiveSessions();
    }

    @Override
    public Optional<SessionInformationDecorator> getSessionInformationBySession(final UUID sessionIndex){
        return this.ocppServer.getSessionInformationBySession(sessionIndex);

    }

    @Override
    public Optional<SessionInformationDecorator> getSessionInformationByIdentifier(final String identifier){
        return this.ocppServer.getSessionInformationByIdentifier(identifier);

    }

}


