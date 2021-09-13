package ge.devel.ocpp.ws.api.impl;

import eu.chargetime.ocpp.NotConnectedException;
import eu.chargetime.ocpp.OccurenceConstraintException;
import eu.chargetime.ocpp.UnsupportedFeatureException;
import eu.chargetime.ocpp.model.Confirmation;
import ge.devel.ocpp.ws.api.JsonServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.UUID;
import java.util.concurrent.CompletionStage;


@Slf4j
@Service("JsonServer")
public class JsonServerImpl implements JsonServer {
    @Override
    public CompletionStage<Confirmation> clearCache(String cpName) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException {
        return null;
    }

    @Override
    public CompletionStage<Confirmation> clearCache(UUID sessionIndex) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException {
        return null;
    }

    @Override
    public CompletionStage<Confirmation> getConfiguration(String cpName, String[] keys) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException {
        return null;
    }

    @Override
    public CompletionStage<Confirmation> getConfiguration(UUID sessionIndex, String[] keys) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException {
        return null;
    }

    @Override
    public CompletionStage<Confirmation> startTransaction(String cpName, Integer connectorId, String idTag) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException {
        return null;
    }

    @Override
    public CompletionStage<Confirmation> startTransaction(UUID sessionIndex, Integer connectorId, String idTag) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException {
        return null;
    }

    @Override
    public CompletionStage<Confirmation> stopTransaction(String cpName, Integer transactionId) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException {
        return null;
    }

    @Override
    public CompletionStage<Confirmation> stopTransaction(UUID sessionIndex, Integer transactionId) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException {
        return null;
    }

    @Override
    public CompletionStage<Confirmation> unlock(String cpName, Integer connectorId) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException {
        return null;
    }

    @Override
    public CompletionStage<Confirmation> unlock(UUID sessionIndex, Integer connectorId) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException {
        return null;
    }

    @Override
    public CompletionStage<Confirmation> getDiagnostics(String cpName, String location) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException {
        return null;
    }

    @Override
    public CompletionStage<Confirmation> getDiagnostics(UUID sessionIndex, String location) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException {
        return null;
    }

    @Override
    public CompletionStage<Confirmation> updateFirmware(String cpName, String location, ZonedDateTime retrieveDate) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException {
        return null;
    }

    @Override
    public CompletionStage<Confirmation> updateFirmware(UUID sessionIndex, String location, ZonedDateTime retrieveDate) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException {
        return null;
    }

    @Override
    public CompletionStage<Confirmation> firmwareStatusNotification(UUID sessionIndex, eu.chargetime.ocpp.model.firmware.FirmwareStatus status) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException {
        return null;
    }

    @Override
    public CompletionStage<Confirmation> firmwareStatusNotification(String cpName, eu.chargetime.ocpp.model.firmware.FirmwareStatus status) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException {
        return null;
    }

    @Override
    public CompletionStage<Confirmation> diagnosticsStatusNotification(UUID sessionIndex, eu.chargetime.ocpp.model.firmware.DiagnosticsStatus status) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException {
        return null;
    }

    @Override
    public CompletionStage<Confirmation> diagnosticsStatusNotification(String cpName, eu.chargetime.ocpp.model.firmware.DiagnosticsStatus status) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException {
        return null;
    }

    @Override
    public CompletionStage<Confirmation> resetService(UUID sessionIndex, eu.chargetime.ocpp.model.core.ResetType type) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException {
        return null;
    }

    @Override
    public CompletionStage<Confirmation> resetService(String cpName, eu.chargetime.ocpp.model.core.ResetType type) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException {
        return null;
    }
}
