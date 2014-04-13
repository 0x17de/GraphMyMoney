package de.icetruck.graphmymoney;

import java.util.List;

/**
 * Created by hans on 13.04.14.
 */
public class Transaction {
    public String id;
    public long timestamp;
    public List<Split> splitList;

    public Transaction(String id, long timestamp, List<Split> splitList) {
        this.id = id;
        this.timestamp = timestamp;
        this.splitList = splitList;
    }
}
