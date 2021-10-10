package ge.devel.ocpp.ws.soap.api;

import eu.chargetime.ocpp.NotConnectedException;
import eu.chargetime.ocpp.OccurenceConstraintException;
import eu.chargetime.ocpp.UnsupportedFeatureException;
import eu.chargetime.ocpp.model.Confirmation;
import eu.chargetime.ocpp.model.core.ResetType;
import eu.chargetime.ocpp.model.firmware.DiagnosticsStatus;
import eu.chargetime.ocpp.model.firmware.FirmwareStatus;

import java.time.ZonedDateTime;
import java.util.UUID;
import java.util.concurrent.CompletionStage;

public interface Soap16Server {

    CompletionStage<Confirmation> clearCache(String cpName) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException;
    CompletionStage<Confirmation> clearCache(UUID sessionIndex) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException;

    CompletionStage<Confirmation> getConfiguration(String cpName, String[] keys) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException;
    CompletionStage<Confirmation> getConfiguration(UUID sessionIndex, String[] keys) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException;

    CompletionStage<Confirmation> resetService(String cpName, ResetType type) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException;
    CompletionStage<Confirmation> resetService(UUID sessionIndex, ResetType type) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException;

    CompletionStage<Confirmation> startTransaction(String cpName, Integer connectorId, String idTag) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException;
    CompletionStage<Confirmation> startTransaction(UUID sessionIndex, Integer connectorId, String idTag) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException;

    CompletionStage<Confirmation> stopTransaction(String cpName, Integer transactionId) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException;
    CompletionStage<Confirmation> stopTransaction(UUID sessionIndex, Integer transactionId) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException;

    CompletionStage<Confirmation> unlock(String cpName, Integer connectorId) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException;
    CompletionStage<Confirmation> unlock(UUID sessionIndex, Integer connectorId) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException;

    CompletionStage<Confirmation> getDiagnostics(String cpName, String location) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException;
    CompletionStage<Confirmation> getDiagnostics(UUID sessionIndex, String location) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException;

    CompletionStage<Confirmation> diagnosticsStatusNotification(String cpName, DiagnosticsStatus status) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException;
    CompletionStage<Confirmation> diagnosticsStatusNotification(UUID sessionIndex, DiagnosticsStatus status) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException;

    CompletionStage<Confirmation> firmwareStatusNotification(String cpName, FirmwareStatus status) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException;
    CompletionStage<Confirmation> firmwareStatusNotification(UUID sessionIndex, FirmwareStatus status) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException;

    CompletionStage<Confirmation> updateFirmware(String cpName, String location, ZonedDateTime retrieveDate) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException;
    CompletionStage<Confirmation> updateFirmware(UUID sessionIndex, String location, ZonedDateTime retrieveDate) throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException;
}
