package ge.devel.ocpp.ws.soap16.api.impl;

import eu.chargetime.ocpp.feature.profile.ServerFirmwareManagementEventHandler;
import eu.chargetime.ocpp.model.firmware.DiagnosticsStatusNotificationConfirmation;
import eu.chargetime.ocpp.model.firmware.DiagnosticsStatusNotificationRequest;
import eu.chargetime.ocpp.model.firmware.FirmwareStatusNotificationConfirmation;
import eu.chargetime.ocpp.model.firmware.FirmwareStatusNotificationRequest;
import ge.devel.ocpp.ws.api.ServerEventHandler;
import ge.devel.ocpp.ws.api.impl.AbstractRequestHandlerValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component("Soap16ServerFirmwareManagementEventHandler")
public class Soap16ServerFirmwareManagementEventHandlerImpl extends AbstractRequestHandlerValidator implements ServerFirmwareManagementEventHandler {


    private final ServerEventHandler serverEventHandler;

    public Soap16ServerFirmwareManagementEventHandlerImpl(@Qualifier("Soap16ServerEventHandler") final ServerEventHandler serverEventHandler){
        this.serverEventHandler = serverEventHandler;
    }

    @Override
    public DiagnosticsStatusNotificationConfirmation handleDiagnosticsStatusNotificationRequest(UUID sessionIndex, DiagnosticsStatusNotificationRequest request) {
        return null;
    }

    @Override
    public FirmwareStatusNotificationConfirmation handleFirmwareStatusNotificationRequest(UUID sessionIndex, FirmwareStatusNotificationRequest request) {
        return null;
    }
}
