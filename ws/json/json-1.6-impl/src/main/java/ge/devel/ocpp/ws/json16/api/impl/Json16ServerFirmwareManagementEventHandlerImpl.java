package ge.devel.ocpp.ws.json16.api.impl;


import eu.chargetime.ocpp.feature.profile.ServerFirmwareManagementEventHandler;
import eu.chargetime.ocpp.model.firmware.DiagnosticsStatusNotificationConfirmation;
import eu.chargetime.ocpp.model.firmware.DiagnosticsStatusNotificationRequest;
import eu.chargetime.ocpp.model.firmware.FirmwareStatusNotificationConfirmation;
import eu.chargetime.ocpp.model.firmware.FirmwareStatusNotificationRequest;
import ge.devel.ocpp.ws.api.ServerEventHandler;
import ge.devel.ocpp.ws.api.decorators.SessionInformationDecorator;
import ge.devel.ocpp.ws.api.impl.AbstractRequestHandlerValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component("Json16ServerFirmwareManagementEventHandler")
public class Json16ServerFirmwareManagementEventHandlerImpl extends AbstractRequestHandlerValidator implements ServerFirmwareManagementEventHandler {

    @Qualifier("Json16ServerEventHandler")
    private ServerEventHandler serverEventHandler;

    public Json16ServerFirmwareManagementEventHandlerImpl(final ServerEventHandler serverEventHandler){
        this.serverEventHandler = serverEventHandler;
    }

    @Override
    public DiagnosticsStatusNotificationConfirmation handleDiagnosticsStatusNotificationRequest(final UUID sessionIndex, final DiagnosticsStatusNotificationRequest request) {
        SessionInformationDecorator info = this.serverEventHandler.getSessionInformationBySession(sessionIndex).orElseThrow();
        log.info("DiagnosticsStatusNotificationRequest UUID = {} ChargePointIdentifier = {} request = {}", sessionIndex, info.getIdentity(), request);

        return new DiagnosticsStatusNotificationConfirmation();
    }

    @Override
    public FirmwareStatusNotificationConfirmation handleFirmwareStatusNotificationRequest(final UUID sessionIndex, final FirmwareStatusNotificationRequest request) {
        SessionInformationDecorator info = this.serverEventHandler.getSessionInformationBySession(sessionIndex).orElseThrow();
        log.info("FirmwareStatusNotificationRequest UUID = {} ChargePointIdentifier = {} request = {}", sessionIndex, info.getIdentity(), request);

        return new FirmwareStatusNotificationConfirmation();
    }
}
