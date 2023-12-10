package cn.forbearance.mysql.binlog.codec;

import cn.forbearance.mysql.binlog.packet.data.QueryEventPacket;

/**
 * @author cristina
 */
public class QueryEventDecoder implements EventDecoder<QueryEventPacket> {

    @Override
    public QueryEventPacket decode(byte[] data) {
        QueryEventPacket decodeData = new QueryEventPacket();
        decodeData.read(data);
        return decodeData;
    }
}
