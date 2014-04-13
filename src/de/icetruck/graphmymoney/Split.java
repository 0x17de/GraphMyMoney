package de.icetruck.graphmymoney;

/**
 * Created by hans on 13.04.14.
 */
public class Split {
    public String id;
    public String payee;
    public String shares;

    public Split(String id, String payee, String shares) {
        this.id = id;
        this.payee = payee;
        this.shares = shares;
    }
}
