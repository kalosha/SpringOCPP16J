package ge.devel.ocpp.ws.json16.api.impl;

import eu.chargetime.ocpp.JSONServer;
import eu.chargetime.ocpp.NotConnectedException;
import eu.chargetime.ocpp.OccurenceConstraintException;
import eu.chargetime.ocpp.UnsupportedFeatureException;
import eu.chargetime.ocpp.feature.profile.ServerCoreEventHandler;
import eu.chargetime.ocpp.feature.profile.ServerCoreProfile;
import eu.chargetime.ocpp.feature.profile.ServerFirmwareManagementEventHandler;
import eu.chargetime.ocpp.feature.profile.ServerFirmwareManagementProfile;
import eu.chargetime.ocpp.model.Confirmation;
import eu.chargetime.ocpp.model.core.GetConfigurationRequest;
import eu.chargetime.ocpp.model.core.RemoteStartTransactionRequest;
import eu.chargetime.ocpp.model.core.ResetType;
import eu.chargetime.ocpp.model.firmware.DiagnosticsStatus;
import eu.chargetime.ocpp.model.firmware.FirmwareStatus;
import ge.devel.ocpp.ws.json16.api.Json16Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.ZonedDateTime;
import java.util.UUID;
import java.util.concurrent.CompletionStage;

@Slf4j
@Service("Json16Server")
public class Json16ServerImpl implements Json16Server {

    private JSONServer server;
    private ServerCoreProfile coreProfile;
    private ServerFirmwareManagementProfile firmwareManagementProfile;

    @Value("${ge.devel.ocpp.json16.server.host}")
    private String serverHost;

    @Value("${ge.devel.ocpp.json16.server.port}")
    private int serverPort;


    private final ServerCoreEventHandler serverCoreEventHandler;

    private final ServerFirmwareManagementEventHandler serverFirmwareManagementEventHandler;


    private final Json16ServerEventHandlerImpl serverEvents;

    public Json16ServerImpl(@Qualifier("Json16ServerCoreEventHandler") final ServerCoreEventHandler serverCoreEventHandler,
                            @Qualifier("Json16ServerFirmwareManagementEventHandler") final ServerFirmwareManagementEventHandler serverFirmwareManagementEventHandler,
                            @Qualifier("Json16ServerEventHandler") final Json16ServerEventHandlerImpl serverEvents) {
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

        log.info("#===> Staring JSON-1.6 server[{}:{}]", this.serverHost, this.serverPort);
        server = new JSONServer(coreProfile);
        server.addFeatureProfile(this.firmwareManagementProfile);
        server.open(this.serverHost, this.serverPort, this.serverEvents);
    }


    @Override
    public CompletionStage<Confirmation> clearCache(final String cpName) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException {
        return this.clearCache(this.serverEvents.getSessionByIdentifier(cpName).orElse(null));
    }

    @Override
    public CompletionStage<Confirmation> clearCache(final UUID sessionIndex) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException {
        return this.server.send(sessionIndex,
                this.coreProfile.createClearCacheRequest());
    }

    @Override
    public CompletionStage<Confirmation> getConfiguration(final String cpName, final String[] keys) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException {
        return this.getConfiguration(this.serverEvents.getSessionByIdentifier(cpName).orElse(null), keys);
    }

    @Override
    public CompletionStage<Confirmation> getConfiguration(final UUID sessionIndex, final String[] keys) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException {
        GetConfigurationRequest request = this.coreProfile.createGetConfigurationRequest();
        request.setKey(keys);
        return this.server.send(sessionIndex, request);
    }

    @Override
    public CompletionStage<Confirmation> startTransaction(final String cpName, final Integer connectorId, final String idTag) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException {
        return this.startTransaction(this.serverEvents.getSessionByIdentifier(cpName).orElse(null), connectorId, idTag);
    }

    @Override
    public CompletionStage<Confirmation> startTransaction(final UUID sessionIndex, final Integer connectorId, final String idTag) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException {
        RemoteStartTransactionRequest request = this.coreProfile.createRemoteStartTransactionRequest(idTag);
        request.setConnectorId(connectorId);

        return this.server.send(sessionIndex, request);
    }

    @Override
    public CompletionStage<Confirmation> stopTransaction(final String cpName, final Integer transactionId) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException {
        return this.stopTransaction(this.serverEvents.getSessionByIdentifier(cpName).orElse(null), transactionId);
    }

    @Override
    public CompletionStage<Confirmation> stopTransaction(final UUID sessionIndex, final Integer transactionId) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException {
        return this.server.send(sessionIndex,
                this.coreProfile.createRemoteStopTransactionRequest(transactionId));
    }

    @Override
    public CompletionStage<Confirmation> unlock(final String cpName, final Integer connectorId) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException {
        return this.unlock(this.serverEvents.getSessionByIdentifier(cpName).orElse(null), connectorId);
    }

    @Override
    public CompletionStage<Confirmation> unlock(final UUID sessionIndex, final Integer connectorId) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException {
        return this.server.send(sessionIndex,
                this.coreProfile.createUnlockConnectorRequest(connectorId));
    }

    @Override
    public CompletionStage<Confirmation> getDiagnostics(final String cpName, final String location) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException {
        return this.getDiagnostics(this.serverEvents.getSessionByIdentifier(cpName).orElse(null), location);
    }

    @Override
    public CompletionStage<Confirmation> getDiagnostics(final UUID sessionIndex, final String location) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException {
        return this.server.send(sessionIndex,
                this.firmwareManagementProfile.createGetDiagnosticsRequest(location));
    }

    @Override
    public CompletionStage<Confirmation> updateFirmware(final String cpName, final String location, final ZonedDateTime retrieveDate) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException {
        return this.updateFirmware(this.serverEvents.getSessionByIdentifier(cpName).orElse(null), location, retrieveDate);
    }

    @Override
    public CompletionStage<Confirmation> updateFirmware(final UUID sessionIndex, final String location, final ZonedDateTime retrieveDate) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException {
        return this.server.send(sessionIndex,
                this.firmwareManagementProfile.createUpdateFirmwareRequest(location, retrieveDate));
    }

    @Override
    public CompletionStage<Confirmation> firmwareStatusNotification(final UUID sessionIndex, final FirmwareStatus status) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException {
        return null;
    }

    @Override
    public CompletionStage<Confirmation> firmwareStatusNotification(final String cpName, final FirmwareStatus status) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException {
        return this.firmwareStatusNotification(this.serverEvents.getSessionByIdentifier(cpName).orElse(null), status);
    }

    @Override
    public CompletionStage<Confirmation> diagnosticsStatusNotification(final UUID sessionIndex, final DiagnosticsStatus status) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException {
        return null;
    }

    @Override
    public CompletionStage<Confirmation> diagnosticsStatusNotification(final String cpName, final DiagnosticsStatus status) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException {
        return this.diagnosticsStatusNotification(this.serverEvents.getSessionByIdentifier(cpName).orElse(null), status);
    }

    @Override
    public CompletionStage<Confirmation> resetService(final UUID sessionIndex, final ResetType type) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException {
        return this.server.send(sessionIndex,
                this.coreProfile.createResetRequest(type));
    }

    @Override
    public CompletionStage<Confirmation> resetService(final String cpName, final ResetType type) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException {
        return this.resetService(this.serverEvents.getSessionByIdentifier(cpName).orElse(null), type);
    }
}
