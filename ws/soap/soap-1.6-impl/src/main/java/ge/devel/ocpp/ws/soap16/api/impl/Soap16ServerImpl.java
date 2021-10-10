package ge.devel.ocpp.ws.soap16.api.impl;

import eu.chargetime.ocpp.*;
import eu.chargetime.ocpp.feature.profile.ServerCoreEventHandler;
import eu.chargetime.ocpp.feature.profile.ServerCoreProfile;
import eu.chargetime.ocpp.feature.profile.ServerFirmwareManagementEventHandler;
import eu.chargetime.ocpp.feature.profile.ServerFirmwareManagementProfile;
import eu.chargetime.ocpp.model.Confirmation;
import eu.chargetime.ocpp.model.core.ResetType;
import eu.chargetime.ocpp.model.firmware.DiagnosticsStatus;
import eu.chargetime.ocpp.model.firmware.FirmwareStatus;
import ge.devel.ocpp.ws.soap.api.Soap16Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.ZonedDateTime;
import java.util.UUID;
import java.util.concurrent.CompletionStage;

@Slf4j
@Service("Soap16Server")
public class Soap16ServerImpl implements Soap16Server {

    private SOAPServer server;
    private ServerCoreProfile coreProfile;
    private ServerFirmwareManagementProfile firmwareManagementProfile;


    @Value("${ge.devel.ocpp.soap16.server.host}")
    private String serverHost;

    @Value("${ge.devel.ocpp.soap16.server.port}")
    private int serverPort;

    @Qualifier("Soap16ServerCoreEventHandler")
    private final ServerCoreEventHandler serverCoreEventHandler;

    private final ServerFirmwareManagementEventHandler serverFirmwareManagementEventHandler;

    private final Soap16ServerEventHandlerImpl serverEvents;

    public Soap16ServerImpl(final ServerCoreEventHandler serverCoreEventHandler,
                            final ServerFirmwareManagementEventHandler serverFirmwareManagementEventHandler,
                            final Soap16ServerEventHandlerImpl serverEvents) {
        this.serverCoreEventHandler = serverCoreEventHandler;
        this.serverFirmwareManagementEventHandler = serverFirmwareManagementEventHandler;
        this.serverEvents = serverEvents;
    }

    @PostConstruct
    public void postConstruct() {
        if (server != null) {
            log.warn("Server already initialized!");
            return;
        }

        // The core profile is mandatory
        this.coreProfile = new ServerCoreProfile(this.serverCoreEventHandler);
        this.firmwareManagementProfile = new ServerFirmwareManagementProfile(this.serverFirmwareManagementEventHandler);

        log.info("#===> Staring SOAP-1.6 server[{}:{}]", this.serverHost, this.serverPort);
        server = new SOAPServer(coreProfile);
        server.addFeatureProfile(this.firmwareManagementProfile);
        server.open(this.serverHost, this.serverPort, this.serverEvents);
    }

    @Override
    public CompletionStage<Confirmation> clearCache(String cpName) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException {
        return this.clearCache(this.serverEvents.getSessionByIdentifier(cpName).orElse(null));
    }

    @Override
    public CompletionStage<Confirmation> clearCache(UUID sessionIndex) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException {
        return null;
    }

    @Override
    public CompletionStage<Confirmation> getConfiguration(String cpName, String[] keys) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException {
        return this.getConfiguration(this.serverEvents.getSessionByIdentifier(cpName).orElse(null), keys);
    }

    @Override
    public CompletionStage<Confirmation> getConfiguration(UUID sessionIndex, String[] keys) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException {
        return null;
    }

    @Override
    public CompletionStage<Confirmation> resetService(String cpName, ResetType type) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException {
        return this.resetService(this.serverEvents.getSessionByIdentifier(cpName).orElse(null), type);
    }

    @Override
    public CompletionStage<Confirmation> resetService(UUID sessionIndex, ResetType type) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException {
        return null;
    }

    @Override
    public CompletionStage<Confirmation> startTransaction(String cpName, Integer connectorId, String idTag) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException {
        return this.startTransaction(this.serverEvents.getSessionByIdentifier(cpName).orElse(null), connectorId, idTag);
    }

    @Override
    public CompletionStage<Confirmation> startTransaction(UUID sessionIndex, Integer connectorId, String idTag) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException {
        return null;
    }

    @Override
    public CompletionStage<Confirmation> stopTransaction(String cpName, Integer transactionId) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException {
        return this.stopTransaction(this.serverEvents.getSessionByIdentifier(cpName).orElse(null), transactionId);
    }

    @Override
    public CompletionStage<Confirmation> stopTransaction(UUID sessionIndex, Integer transactionId) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException {
        return null;
    }

    @Override
    public CompletionStage<Confirmation> unlock(String cpName, Integer connectorId) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException {
        return this.unlock(this.serverEvents.getSessionByIdentifier(cpName).orElse(null), connectorId);
    }

    @Override
    public CompletionStage<Confirmation> unlock(UUID sessionIndex, Integer connectorId) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException {
        return null;
    }

    @Override
    public CompletionStage<Confirmation> getDiagnostics(String cpName, String location) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException {
        return this.getDiagnostics(this.serverEvents.getSessionByIdentifier(cpName).orElse(null), location);
    }

    @Override
    public CompletionStage<Confirmation> getDiagnostics(UUID sessionIndex, String location) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException {
        return null;
    }

    @Override
    public CompletionStage<Confirmation> diagnosticsStatusNotification(String cpName, DiagnosticsStatus status) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException {
        return this.diagnosticsStatusNotification(this.serverEvents.getSessionByIdentifier(cpName).orElse(null), status);
    }

    @Override
    public CompletionStage<Confirmation> diagnosticsStatusNotification(UUID sessionIndex, DiagnosticsStatus status) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException {
        return null;
    }

    @Override
    public CompletionStage<Confirmation> firmwareStatusNotification(String cpName, FirmwareStatus status) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException {
        return this.firmwareStatusNotification(this.serverEvents.getSessionByIdentifier(cpName).orElse(null), status);
    }

    @Override
    public CompletionStage<Confirmation> firmwareStatusNotification(UUID sessionIndex, FirmwareStatus status) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException {
        return null;
    }

    @Override
    public CompletionStage<Confirmation> updateFirmware(String cpName, String location, ZonedDateTime retrieveDate) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException {
        return this.updateFirmware(this.serverEvents.getSessionByIdentifier(cpName).orElse(null), location, retrieveDate);
    }

    @Override
    public CompletionStage<Confirmation> updateFirmware(UUID sessionIndex, String location, ZonedDateTime retrieveDate) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException {
        return null;
    }
}
