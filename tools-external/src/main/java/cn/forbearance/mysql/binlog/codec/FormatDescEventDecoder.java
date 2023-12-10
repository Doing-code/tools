package cn.forbearance.mysql.binlog.codec;

import cn.forbearance.mysql.binlog.packet.data.FormatDescEventPacket;

/**
 * @author cristina
 */
public class FormatDescEventDecoder implements EventDecoder<FormatDescEventPacket> {

    @Override
    public FormatDescEventPacket decode(byte[] data) {
        FormatDescEventPacket decodeData = new FormatDescEventPacket();
        decodeData.read(data);
        return decodeData;
    }
}
