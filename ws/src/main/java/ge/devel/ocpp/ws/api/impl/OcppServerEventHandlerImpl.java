package ge.devel.ocpp.ws.api.impl;

import eu.chargetime.ocpp.model.SessionInformation;
import eu.chargetime.ocpp.model.core.ChargePointStatus;
import ge.devel.ocpp.ws.api.decorators.SessionInformationDecorator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Component("OcppServerEventHandler")
public class OcppServerEventHandlerImpl extends AbstractRequestHandlerValidator  {


    private final Map<UUID, SessionInformationDecorator> sessions = new ConcurrentHashMap<>();

    public OcppServerEventHandlerImpl() {
        log.info("OCPPServerEvents initialized");
    }

    public void newSession(final String endpoint, final UUID sessionIndex, final SessionInformation information) {
        // sessionIndex is used to send messages.
        SessionInformationDecorator info = new SessionInformationDecorator(sessionIndex, endpoint, information, ChargePointStatus.Unavailable);
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

    public void lostSession(final UUID sessionIndex) {
        log.info("Session [{}] ChargePointIdent = {} disconnected", sessionIndex, this.getChargePointIdentifierBySession(sessionIndex));
        log.info("Session Removed [{}] ChargePointIdent = {} information = {} ", sessionIndex, this.getChargePointIdentifierBySession(sessionIndex), this.sessions.remove(sessionIndex));
    }

    public Optional<String> getChargePointIdentifierBySession(final UUID sessionIndex){
        return Optional.ofNullable(this.sessions.get(sessionIndex))
                .map(SessionInformationDecorator::getIdentity);
    }

    public Optional<UUID> getSessionByIdentifier(final String identifier){
        return this.sessions.values().stream()
                .filter(f -> f.getIdentity().equalsIgnoreCase(identifier))
                .findFirst()
                .map(SessionInformationDecorator::getSessionIndex);
    }

    public void updateStatus(final UUID sessionIndex, final ChargePointStatus status ){
        this.sessions.get(sessionIndex).setStatus(status);
    }

    public List<SessionInformationDecorator> getActiveSessionsInfo() {
        return new ArrayList<>(this.sessions.values());
    }

    public List<UUID> getActiveSessions() {
        return this.sessions.values().stream()
                .map(SessionInformationDecorator::getSessionIndex)
                .collect(Collectors.toList());
    }

    public Optional<SessionInformationDecorator> getSessionInformationBySession(final UUID sessionIndex){
        return this.sessions.values().stream()
                .filter(f -> f.getSessionIndex().equals(sessionIndex))
                .findFirst();
    }

    public Optional<SessionInformationDecorator> getSessionInformationByIdentifier(final String identifier){
        return this.sessions.values().stream()
                .filter(f -> f.getIdentity().equalsIgnoreCase(identifier))
                .findFirst();
    }

}


