package cn.forbearance.mysql.binlog.codec;

import cn.forbearance.mysql.binlog.packet.data.RowsQueryEventPacket;

/**
 * @author cristina
 */
public class RowsQueryEventDecoder implements EventDecoder<RowsQueryEventPacket> {

    @Override
    public RowsQueryEventPacket decode(byte[] data) {
        RowsQueryEventPacket decodeData = new RowsQueryEventPacket();
        decodeData.read(data);
        return decodeData;
    }
}
