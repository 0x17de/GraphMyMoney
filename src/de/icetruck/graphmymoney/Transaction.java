package de.icetruck.graphmymoney;

import java.util.List;

/**
 * Created by hans on 13.04.14.
 */
public class Transaction {
    public String id;
    public String postdate;
    public List<Split> splitList;

    public Transaction(String id, String postdate, List<Split> splitList) {
        this.id = id;
        this.postdate = postdate;
        this.splitList = splitList;
    }
}
