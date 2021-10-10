package ge.devel.ocpp.ws.json16.api.impl;

import eu.chargetime.ocpp.ServerEvents;
import eu.chargetime.ocpp.model.SessionInformation;
import eu.chargetime.ocpp.model.core.ChargePointStatus;
import ge.devel.ocpp.ws.api.ServerEventHandler;
import ge.devel.ocpp.ws.api.impl.OcppServerEventHandlerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.InetSocketAddress;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {ServerEvents.class, ServerEventHandler.class, Json16ServerEventHandlerImpl.class, OcppServerEventHandlerImpl.class},
        properties = { "ge.devel.ocpp.json16.server.endpoint=annotation", "ge.devel.ocpp.json16.server.host=host"  })
class ServerEventHandlerTest {

    @Value("${ge.devel.ocpp.json16.server.host}")
    private String hostname;

    @Value("${ge.devel.ocpp.json16.server.port}")
    private int  port;

    private final Json16ServerEventHandlerImpl underTest;

    @Autowired
    public ServerEventHandlerTest(Json16ServerEventHandlerImpl serverEventHandler) {
        this.underTest = serverEventHandler;
    }

    @BeforeEach
    public void initBefore(){
        this.underTest.getActiveSessions().forEach(this.underTest::lostSession);
    }

    @Test
    void testNewSession_whenAdd3new_thenCheckingSessionsMapSize(){
        this.underTest.newSession(UUID.randomUUID(), this.generateSessionInformation());
        assertEquals(1, this.underTest.getActiveSessions().size());

        this.underTest.newSession(UUID.randomUUID(), this.generateSessionInformation());
        assertEquals(2, this.underTest.getActiveSessions().size());

        this.underTest.newSession(UUID.randomUUID(), this.generateSessionInformation());
        assertEquals(3, this.underTest.getActiveSessions().size());

    }

    @Test
    void testNewSession_whenRemove_thenCheckingSessionsMapSize(){
        UUID testSessionId = UUID.randomUUID();
        this.underTest.newSession(UUID.randomUUID(), this.generateSessionInformation());
        this.underTest.newSession(testSessionId, this.generateSessionInformation());
        this.underTest.newSession(UUID.randomUUID(), this.generateSessionInformation());
        assertEquals(3, this.underTest.getActiveSessions().size());

        this.underTest.lostSession(testSessionId);
        assertEquals(2, this.underTest.getActiveSessions().size());
    }

    @Test
    void testNewSession_whenRemoveNotExistSession_thenCheckingSessionsMapSize(){
        this.underTest.newSession(UUID.randomUUID(), this.generateSessionInformation());
        this.underTest.newSession(UUID.randomUUID(), this.generateSessionInformation());
        this.underTest.newSession(UUID.randomUUID(), this.generateSessionInformation());
        assertEquals(3, this.underTest.getActiveSessions().size());

        this.underTest.lostSession(UUID.randomUUID());
        assertEquals(3, this.underTest.getActiveSessions().size());
    }

    @Test
    void testNewSession_whenDuplicatedIdentifier_thenCheckingSessionsMapSize(){
        String testIdentifier = "testIdentifier";
        this.underTest.newSession(UUID.randomUUID(), this.generateSessionInformation());
        this.underTest.newSession(UUID.randomUUID(), this.generateSessionInformation(testIdentifier));
        this.underTest.newSession(UUID.randomUUID(), this.generateSessionInformation());
        assertEquals(3, this.underTest.getActiveSessions().size());

        this.underTest.newSession(UUID.randomUUID(), this.generateSessionInformation(testIdentifier));
        assertEquals(3, this.underTest.getActiveSessions().size());
    }

    @Test
    void testNewSession_whenGetSessionByIdentifier_thenChecking(){
        String testIdentifier = "testIdentifier";
        UUID uuid = UUID.randomUUID();
        this.underTest.newSession(UUID.randomUUID(), this.generateSessionInformation());
        this.underTest.newSession(uuid, this.generateSessionInformation(testIdentifier));
        this.underTest.newSession(UUID.randomUUID(), this.generateSessionInformation());
        assertEquals(3, this.underTest.getActiveSessions().size());

        assertEquals(uuid, this.underTest.getSessionByIdentifier(testIdentifier).orElse(null));
    }

    @Test
    void testNewSession_whenGetChargePointIdentifierBySession_thenChecking(){
        String testIdentifier = "testIdentifier";
        UUID uuid = UUID.randomUUID();
        this.underTest.newSession(UUID.randomUUID(), this.generateSessionInformation());
        this.underTest.newSession(uuid, this.generateSessionInformation(testIdentifier));
        this.underTest.newSession(UUID.randomUUID(), this.generateSessionInformation());
        assertEquals(3, this.underTest.getActiveSessions().size());

        assertEquals(testIdentifier, this.underTest.getChargePointIdentifierBySession(uuid).orElse(null));
    }


    @Test
    void testNewSession_whenUpdateStatus_thenChecking(){
        String testIdentifier = "testIdentifier";
        UUID uuid = UUID.randomUUID();
        this.underTest.newSession(UUID.randomUUID(), this.generateSessionInformation());
        this.underTest.newSession(uuid, this.generateSessionInformation(testIdentifier));
        this.underTest.newSession(UUID.randomUUID(), this.generateSessionInformation());
        assertEquals(3, this.underTest.getActiveSessions().size());

        this.underTest.updateStatus(uuid, ChargePointStatus.Faulted);

        assertEquals(ChargePointStatus.Faulted, this.underTest.getSessionInformationBySession(uuid).orElseThrow().getStatus());
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
