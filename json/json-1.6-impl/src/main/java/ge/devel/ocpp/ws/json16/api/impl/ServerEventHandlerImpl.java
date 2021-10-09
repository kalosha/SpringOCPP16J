package ge.devel.ocpp.ws.json16.api.impl;

import eu.chargetime.ocpp.ServerEvents;
import eu.chargetime.ocpp.model.SessionInformation;
import eu.chargetime.ocpp.model.core.ChargePointStatus;
import ge.devel.ocpp.ws.json16.api.ServerEventHandler;
import ge.devel.ocpp.ws.json16.api.decorators.SessionInformationDecorator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Component("ServerEventHandler")
public class ServerEventHandlerImpl extends AbstractRequestHandlerValidator implements ServerEventHandler, ServerEvents {

    @Value("${ge.devel.ocpp.json16.server.endpoint}")
    private String endpoint;

    private final Map<UUID, SessionInformationDecorator> sessions = new ConcurrentHashMap<>();

    public ServerEventHandlerImpl() {
        log.info("ServerEvents initialized");
    }

    @Override
    public void newSession(final UUID sessionIndex, final SessionInformation information) {
        // sessionIndex is used to send messages.
        SessionInformationDecorator info = new SessionInformationDecorator(sessionIndex, this.endpoint, information, ChargePointStatus.Unavailable);
        log.info("New Session [{}] ChargePointIdent = {} information = {}", sessionIndex, info.getIdentity(), info);

        this.sessions.values().stream()
                .filter(f -> f.getIdentity().equalsIgnoreCase(information.getIdentifier()))
                .findAny()
                .ifPresent(p -> {
                    log.info("Session is Duplicated [{}] identifier = {} , removing old session", sessionIndex, p.getIdentity());
                    this.lostSession(p.getSessionIndex());
                });

        this.sessions.putIfAbsent(sessionIndex, info);
    }

    @Override
    public void lostSession(final UUID sessionIndex) {
        log.info("Session [{}] ChargePointIdent = {} disconnected", sessionIndex, this.getChargePointIdentifierBySession(sessionIndex));
        log.info("Session Removed [{}] ChargePointIdent = {} information = {} ", sessionIndex, this.getChargePointIdentifierBySession(sessionIndex), this.sessions.remove(sessionIndex));
    }

    @Override
    public Optional<String> getChargePointIdentifierBySession(final UUID sessionIndex){
        return Optional.ofNullable(this.sessions.get(sessionIndex))
                .map(SessionInformationDecorator::getIdentity);
    }

    @Override
    public Optional<UUID> getSessionByIdentifier(final String identifier){
        return this.sessions.values().stream()
                .filter(f -> f.getIdentity().equalsIgnoreCase(identifier))
                .findFirst()
                .map(SessionInformationDecorator::getSessionIndex);
    }

    @Override
    public void updateStatus(final UUID sessionIndex, final ChargePointStatus status ){
        this.sessions.get(sessionIndex).setStatus(status);
    }

    @Override
    public List<SessionInformationDecorator> getActiveSessionsInfo() {
        return new ArrayList<>(this.sessions.values());
    }

    @Override
    public List<UUID> getActiveSessions() {
        return this.sessions.values().stream()
                .map(SessionInformationDecorator::getSessionIndex)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<SessionInformationDecorator> getSessionInformationBySession(final UUID sessionIndex){
        return this.sessions.values().stream()
                .filter(f -> f.getSessionIndex().equals(sessionIndex))
                .findFirst();
    }

    @Override
    public Optional<SessionInformationDecorator> getSessionInformationByIdentifier(final String identifier){
        return this.sessions.values().stream()
                .filter(f -> f.getIdentity().equalsIgnoreCase(identifier))
                .findFirst();
    }

}


