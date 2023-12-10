package cn.forbearance.mysql.protocol.packets;

import java.nio.charset.StandardCharsets;

/**
 * @author cristina
 */
public class FieldPacket extends Packet {

    public String catalog;
    public String db;
    public String table;
    public String originalTable;
    public String name;
    public String originalName;
    public int    character;
    public long   length;
    public byte   type;
    public int    flags;
    public byte   decimals;
    public String definition;

    public void read(BinaryPacket bin) {
        Message mm = new Message(bin.getBody());
        this.catalog = new String(mm.readBytesWithLength(), StandardCharsets.UTF_8);
        this.db = new String(mm.readBytesWithLength());
        this.table = new String(mm.readBytesWithLength());
        this.originalTable = new String(mm.readBytesWithLength(), StandardCharsets.UTF_8);
        this.name = new String(mm.readBytesWithLength(), StandardCharsets.UTF_8);
        this.originalName = new String(mm.readBytesWithLength(), StandardCharsets.UTF_8);
        this.character = mm.readUb2();
        this.length = mm.readUb4();
        this.type = mm.read();
        this.flags = mm.readUb2();
        this.decimals = mm.read();
        mm.move(2);
        this.definition = new String(mm.readBytesWithLength(), StandardCharsets.UTF_8);
    }
}
