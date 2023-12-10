package cn.forbearance.mysql.binlog.packet;

import cn.forbearance.mysql.binlog.DecoderFactory;
import cn.forbearance.mysql.protocol.packets.Message;
import cn.forbearance.utils.ByteUtil;
import cn.forbearance.utils.DecodeUtil;

import java.util.BitSet;

/**
 * @author cristina
 */
public class TableMapEventPacket implements EventPacket {

    private long tableId;
    private String database;
    private String table;
    private byte[] columnTypes;
    private int[] columnMetadata;
    private BitSet columnNullability;

    @Override
    public void read(byte[] data) {
        Message msg = new Message(data);
        tableId = ByteUtil.byte2Long(msg.readBytes(6));
        msg.move(3);
        database = ByteUtil.byte2String(msg.readBytesWithNull());
        msg.move(1);
        table = ByteUtil.byte2String(msg.readBytesWithNull());
        int numberOfColumns = (int) msg.readLength();
        columnTypes = msg.readBytes(numberOfColumns);
        msg.readLength();
        columnMetadata = readMetadata(msg, columnTypes);
        columnNullability = DecodeUtil.readBitSet(msg);
        // 自身注册进tableMap
        DecoderFactory.putTableMap(tableId, this);
    }

    private int[] readMetadata(Message msg, byte[] columnTypes) {
        int[] metadata = new int[columnTypes.length];
        for (int i = 0; i < columnTypes.length; i++) {
            switch (ColumnType.code(columnTypes[i] & 0xFF)) {
                case FLOAT:
                case DOUBLE:
                case BLOB:
                case GEOMETRY:
                case TIME_V2:
                case DATETIME_V2:
                case TIMESTAMP_V2:
                    metadata[i] = msg.read();
                    break;
                case BIT:
                case VARCHAR:
                case NEWDECIMAL:
                    metadata[i] = msg.readUb2();
                    break;
                case SET:
                case ENUM:
                case STRING:
                    metadata[i] = DecodeUtil.bigEndianInteger(msg.readBytes(2), 0, 2);
                    break;
                default:
                    metadata[i] = 0;
            }
        }
        return metadata;
    }
}
