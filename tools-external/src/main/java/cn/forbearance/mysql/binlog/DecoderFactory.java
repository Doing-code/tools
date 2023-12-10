package cn.forbearance.mysql.binlog;

import cn.forbearance.mysql.binlog.codec.EventDecoder;
import cn.forbearance.mysql.binlog.codec.FormatDescEventDecoder;
import cn.forbearance.mysql.binlog.codec.QueryEventDecoder;
import cn.forbearance.mysql.binlog.codec.RowsQueryEventDecoder;
import cn.forbearance.mysql.binlog.packet.TableMapEventPacket;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author cristina
 */
public class DecoderFactory {

    public static Map<Long, TableMapEventPacket> tableMapEventDataByTableId = new HashMap<>();

    public static Map<Byte, EventDecoder<?>> decoderMap;

    static {
        decoderMap = new ConcurrentHashMap<>();
        /*decoderMap.put(EventType.FORMAT_DESCRIPTION_EVENT, new FormatDescEventDecoder());
        decoderMap.put(EventType.ROTATE_EVENT, new RotateEventDecoder());
        decoderMap.put(EventType.INTVAR_EVENT, new IntVarEventDecoder());
        decoderMap.put(EventType.QUERY_EVENT, new QueryEventDecoder());
        decoderMap.put(EventType.TABLE_MAP_EVENT, new TableMapEventDecoder());
        decoderMap.put(EventType.XID_EVENT, new XidEventDecoder());
        decoderMap.put(EventType.WRITE_ROWS_EVENT_V1, new WriteRowsEventDecoder());
        decoderMap.put(EventType.UPDATE_ROWS_EVENT_V1, new UpdateRowsEventDecoder());
        decoderMap.put(EventType.DELETE_ROWS_EVENT_V1, new DeleteRowsEventDecoder());
        decoderMap.put(EventType.ROWS_QUERY_EVENT, new RowsQueryEventDecoder());
        decoderMap.put(EventType.ANONYMOUS_GTID_EVENT, new GtidEventDecoder());
        decoderMap.put(EventType.GTID_EVENT, new GtidEventDecoder());
        decoderMap.put(EventType.HEARTBEAT_EVENT, new HeartBeatEventDecoder());*/
    }

    public static EventDecoder<?> getDecoder(byte eventType) {
        return decoderMap.get(eventType);
    }

    public static void putTableMap(Long tableId, TableMapEventPacket tableMapEventData) {
        tableMapEventDataByTableId.put(tableId, tableMapEventData);
    }

    public static TableMapEventPacket getTableMapEventData(Long tableId) {
        TableMapEventPacket tableMapEventData = tableMapEventDataByTableId.get(tableId);
        if (tableMapEventData == null) {
            throw new RuntimeException("not match table map tableId = " + tableId);
        }
        return tableMapEventData;
    }
}
