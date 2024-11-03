package com.online.books.util;

public class SnowflakeIdGenerator {

    // 常量定义
    private static final long START_TIMESTAMP = 1698207890000L; // 起始时间戳 (2024-10-25)
    private static final long DATA_CENTER_ID_BITS = 5L;
    private static final long MACHINE_ID_BITS = 5L;
    private static final long SEQUENCE_BITS = 12L;

    private static final long MAX_DATA_CENTER_ID = ~(-1L << DATA_CENTER_ID_BITS); // 支持的最大数据中心 ID 值
    private static final long MAX_MACHINE_ID = ~(-1L << MACHINE_ID_BITS); // 支持的最大机器 ID 值
    private static final long MAX_SEQUENCE = ~(-1L << SEQUENCE_BITS); // 支持的最大序列号

    private static final long MACHINE_ID_SHIFT = SEQUENCE_BITS;
    private static final long DATA_CENTER_ID_SHIFT = SEQUENCE_BITS + MACHINE_ID_BITS;
    private static final long TIMESTAMP_SHIFT = SEQUENCE_BITS + MACHINE_ID_BITS + DATA_CENTER_ID_BITS;

    private long dataCenterId;
    private long machineId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    public SnowflakeIdGenerator(long dataCenterId, long machineId) {
        if (dataCenterId > MAX_DATA_CENTER_ID || dataCenterId < 0) {
            throw new IllegalArgumentException("Data Center ID can't be greater than " + MAX_DATA_CENTER_ID + " or less than 0");
        }
        if (machineId > MAX_MACHINE_ID || machineId < 0) {
            throw new IllegalArgumentException("Machine ID can't be greater than " + MAX_MACHINE_ID + " or less than 0");
        }
        this.dataCenterId = dataCenterId;
        this.machineId = machineId;
    }

    // 生成下一个 ID
    public synchronized long nextId() {
        long timestamp = currentTimestamp();

        if (timestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards. Refusing to generate ID for " + (lastTimestamp - timestamp) + " milliseconds");
        }

        if (timestamp == lastTimestamp) {
            // 同一毫秒内递增序列号
            sequence = (sequence + 1) & MAX_SEQUENCE;
            if (sequence == 0L) {
                // 如果序列号溢出，则等待下一毫秒
                timestamp = waitNextMillis(lastTimestamp);
            }
        } else {
            // 不同毫秒内，序列号重置
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        // 生成 ID
        return ((timestamp - START_TIMESTAMP) << TIMESTAMP_SHIFT)
                | (dataCenterId << DATA_CENTER_ID_SHIFT)
                | (machineId << MACHINE_ID_SHIFT)
                | sequence;
    }

    // 获取当前时间戳
    private long currentTimestamp() {
        return System.currentTimeMillis();
    }

    // 等待下一毫秒
    private long waitNextMillis(long lastTimestamp) {
        long timestamp = currentTimestamp();
        while (timestamp <= lastTimestamp) {
            timestamp = currentTimestamp();
        }
        return timestamp;
    }

    public static void main(String[] args) {
        SnowflakeIdGenerator generator = new SnowflakeIdGenerator(1, 1);
        for (int i = 0; i < 10; i++) {
            System.out.println(generator.nextId());
        }
    }
}
