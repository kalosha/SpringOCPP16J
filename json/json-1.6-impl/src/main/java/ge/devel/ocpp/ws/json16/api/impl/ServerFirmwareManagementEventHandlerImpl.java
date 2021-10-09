package ge.devel.ocpp.ws.json16.api.impl;


import eu.chargetime.ocpp.feature.profile.ServerFirmwareManagementEventHandler;
import eu.chargetime.ocpp.model.firmware.DiagnosticsStatusNotificationConfirmation;
import eu.chargetime.ocpp.model.firmware.DiagnosticsStatusNotificationRequest;
import eu.chargetime.ocpp.model.firmware.FirmwareStatusNotificationConfirmation;
import eu.chargetime.ocpp.model.firmware.FirmwareStatusNotificationRequest;
import ge.devel.ocpp.ws.json16.api.ServerEventHandler;
import ge.devel.ocpp.ws.json16.api.decorators.SessionInformationDecorator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component("ServerFirmwareManagementEventHandler")
public class ServerFirmwareManagementEventHandlerImpl extends AbstractRequestHandlerValidator implements ServerFirmwareManagementEventHandler {

    private ServerEventHandler serverEventHandler;

    public ServerFirmwareManagementEventHandlerImpl(ServerEventHandler serverEventHandler){
        this.serverEventHandler = serverEventHandler;
    }

    @Override
    public DiagnosticsStatusNotificationConfirmation handleDiagnosticsStatusNotificationRequest(UUID sessionIndex, DiagnosticsStatusNotificationRequest request) {
        SessionInformationDecorator info = this.serverEventHandler.getSessionInformationBySession(sessionIndex).orElseThrow();
        log.info("DiagnosticsStatusNotificationRequest UUID = {} ChargePointIdentifier = {} request = {}", sessionIndex, info.getIdentity(), request);

        return new DiagnosticsStatusNotificationConfirmation();
    }

    @Override
    public FirmwareStatusNotificationConfirmation handleFirmwareStatusNotificationRequest(UUID sessionIndex, FirmwareStatusNotificationRequest request) {
        SessionInformationDecorator info = this.serverEventHandler.getSessionInformationBySession(sessionIndex).orElseThrow();
        log.info("FirmwareStatusNotificationRequest UUID = {} ChargePointIdentifier = {} request = {}", sessionIndex, info.getIdentity(), request);

        return new FirmwareStatusNotificationConfirmation();
    }
}
