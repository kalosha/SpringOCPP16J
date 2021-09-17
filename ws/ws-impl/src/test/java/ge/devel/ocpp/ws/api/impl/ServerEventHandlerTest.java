package ge.devel.ocpp.ws.api.impl;

import eu.chargetime.ocpp.ServerEvents;
import eu.chargetime.ocpp.model.SessionInformation;
import eu.chargetime.ocpp.model.core.ChargePointStatus;
import ge.devel.ocpp.ws.api.ServerEventHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.InetSocketAddress;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {ServerEvents.class, ServerEventHandler.class, ServerEventHandlerImpl.class},
        properties = { "ge.devel.ocpp.server.endpoint=annotation", "ge.devel.ocpp.server.host=host"  })
public class ServerEventHandlerTest {

    @Value("${ge.devel.ocpp.server.host}")
    private String hostname;

    @Value("${ge.devel.ocpp.server.port}")
    private int  port;

    private final ServerEventHandler serverEventHandler;

    @Autowired
    public ServerEventHandlerTest(ServerEventHandler serverEventHandler) {
        this.serverEventHandler = serverEventHandler;
    }

    @BeforeEach
    public void initBefore(){
        final ServerEventHandlerImpl handler = (ServerEventHandlerImpl)this.serverEventHandler;
        handler.getActiveSessions().forEach(handler::lostSession);
    }

    @Test
    public void testNewSession_whenAdd3new_thenCheckingSessionsMapSize(){
        final ServerEvents handler = (ServerEvents)this.serverEventHandler;
        handler.newSession(UUID.randomUUID(), this.generateSessionInformation());
        assertEquals(1, this.serverEventHandler.getActiveSessions().size());

        handler.newSession(UUID.randomUUID(), this.generateSessionInformation());
        assertEquals(2, this.serverEventHandler.getActiveSessions().size());

        handler.newSession(UUID.randomUUID(), this.generateSessionInformation());
        assertEquals(3, this.serverEventHandler.getActiveSessions().size());

    }

    @Test
    public void testNewSession_whenRemove_thenCheckingSessionsMapSize(){
        final ServerEvents handler = (ServerEvents)this.serverEventHandler;
        UUID testSessionId = UUID.randomUUID();
        handler.newSession(UUID.randomUUID(), this.generateSessionInformation());
        handler.newSession(testSessionId, this.generateSessionInformation());
        handler.newSession(UUID.randomUUID(), this.generateSessionInformation());
        assertEquals(3, this.serverEventHandler.getActiveSessions().size());

        handler.lostSession(testSessionId);
        assertEquals(2, this.serverEventHandler.getActiveSessions().size());
    }

    @Test
    public void testNewSession_whenRemoveNotExistSession_thenCheckingSessionsMapSize(){
        final ServerEvents handler = (ServerEvents)this.serverEventHandler;
        handler.newSession(UUID.randomUUID(), this.generateSessionInformation());
        handler.newSession(UUID.randomUUID(), this.generateSessionInformation());
        handler.newSession(UUID.randomUUID(), this.generateSessionInformation());
        assertEquals(3, this.serverEventHandler.getActiveSessions().size());

        handler.lostSession(UUID.randomUUID());
        assertEquals(3, this.serverEventHandler.getActiveSessions().size());
    }

    @Test
    public void testNewSession_whenDuplicatedIdentifier_thenCheckingSessionsMapSize(){
        final ServerEvents handler = (ServerEvents)this.serverEventHandler;
        String testIdentifier = "testIdentifier";
        handler.newSession(UUID.randomUUID(), this.generateSessionInformation());
        handler.newSession(UUID.randomUUID(), this.generateSessionInformation(testIdentifier));
        handler.newSession(UUID.randomUUID(), this.generateSessionInformation());
        assertEquals(3, this.serverEventHandler.getActiveSessions().size());

        handler.newSession(UUID.randomUUID(), this.generateSessionInformation(testIdentifier));
        assertEquals(3, this.serverEventHandler.getActiveSessions().size());
    }

    @Test
    public void testNewSession_whenGetSessionByIdentifier_thenChecking(){
        final ServerEvents handler = (ServerEvents)this.serverEventHandler;
        String testIdentifier = "testIdentifier";
        UUID uuid = UUID.randomUUID();
        handler.newSession(UUID.randomUUID(), this.generateSessionInformation());
        handler.newSession(uuid, this.generateSessionInformation(testIdentifier));
        handler.newSession(UUID.randomUUID(), this.generateSessionInformation());
        assertEquals(3, this.serverEventHandler.getActiveSessions().size());

        assertEquals(uuid, this.serverEventHandler.getSessionByIdentifier(testIdentifier).orElse(null));
    }

    @Test
    public void testNewSession_whenGetChargePointIdentifierBySession_thenChecking(){
        final ServerEvents handler = (ServerEvents)this.serverEventHandler;
        String testIdentifier = "testIdentifier";
        UUID uuid = UUID.randomUUID();
        handler.newSession(UUID.randomUUID(), this.generateSessionInformation());
        handler.newSession(uuid, this.generateSessionInformation(testIdentifier));
        handler.newSession(UUID.randomUUID(), this.generateSessionInformation());
        assertEquals(3, this.serverEventHandler.getActiveSessions().size());

        assertEquals(testIdentifier, this.serverEventHandler.getChargePointIdentifierBySession(uuid).orElse(null));
    }


    @Test
    public void testNewSession_whenUpdateStatus_thenChecking(){
        final ServerEvents handler = (ServerEvents)this.serverEventHandler;
        String testIdentifier = "testIdentifier";
        UUID uuid = UUID.randomUUID();
        handler.newSession(UUID.randomUUID(), this.generateSessionInformation());
        handler.newSession(uuid, this.generateSessionInformation(testIdentifier));
        handler.newSession(UUID.randomUUID(), this.generateSessionInformation());
        assertEquals(3, this.serverEventHandler.getActiveSessions().size());

        this.serverEventHandler.updateStatus(uuid, ChargePointStatus.Faulted);

        assertEquals(ChargePointStatus.Faulted, this.serverEventHandler.getSessionInformationBySession(uuid).orElseThrow().getStatus());
    }


    private SessionInformation generateSessionInformation(){
        return this.generateSessionInformation(null);
    }

    private SessionInformation generateSessionInformation(String identifier){
        return new SessionInformation.Builder()
                .Identifier(Objects.isNull(identifier) ? UUID.randomUUID().toString() : identifier)
                .SOAPtoURL("testing")
                .InternetAddress(new InetSocketAddress(hostname, port))
                .build();
    }

}
