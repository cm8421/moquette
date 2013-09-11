package org.dna.mqtt.moquette.parser.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import org.dna.mqtt.moquette.proto.messages.ConnAckMessage;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author andrea
 */
public class ConnAckEncoderTest {
    
    ConnAckEncoder m_encoder = new ConnAckEncoder();
    ChannelHandlerContext m_mockedContext;
    
    @Before
    public void setUp() {
        //mock the ChannelHandlerContext to return an UnpooledAllocator
        m_mockedContext = mock(ChannelHandlerContext.class);
        ByteBufAllocator allocator = UnpooledByteBufAllocator.DEFAULT;
        when(m_mockedContext.alloc()).thenReturn(allocator);
    }
    
    @Test
    public void testHeaderEncode() throws Exception {
        ConnAckMessage msg = new ConnAckMessage();
        
        //Exercise
        ByteBuf out = Unpooled.buffer();
        
        //Exercise
        m_encoder.encode(m_mockedContext, msg, out);
        
        //Verify
        assertEquals(0x20, out.readByte()); //1 byte
        assertEquals(0x02, out.readByte()); //2 byte, length
        assertEquals(ConnAckMessage.CONNECTION_ACCEPTED, out.skipBytes(1).readByte());
    }
    
}
