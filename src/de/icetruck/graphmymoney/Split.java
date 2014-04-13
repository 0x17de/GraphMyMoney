package de.icetruck.graphmymoney;

/**
 * Created by hans on 13.04.14.
 */
public class Split {
    public String id;
    public String payee;
    public String bankid;
    public String account;
    public String shares;

    public Split(String id, String payee, String bankid, String account, String shares) {
        this.id = id;
        this.payee = payee;
        this.bankid = bankid;
        this.account = account;
        this.shares = shares;
    }
}
