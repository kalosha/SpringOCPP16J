package ge.devel.ocpp.ws.json16.api;

import eu.chargetime.ocpp.NotConnectedException;
import eu.chargetime.ocpp.OccurenceConstraintException;
import eu.chargetime.ocpp.UnsupportedFeatureException;
import eu.chargetime.ocpp.model.Confirmation;
import eu.chargetime.ocpp.model.core.ResetType;
import eu.chargetime.ocpp.model.firmware.DiagnosticsStatus;
import eu.chargetime.ocpp.model.firmware.FirmwareStatus;
import ge.devel.ocpp.ws.api.OcppServer;

import java.time.ZonedDateTime;
import java.util.UUID;
import java.util.concurrent.CompletionStage;

public interface Json16Server  extends OcppServer {

}
