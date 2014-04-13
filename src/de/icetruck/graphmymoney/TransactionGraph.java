package de.icetruck.graphmymoney;

import javax.swing.text.DateFormatter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;

/**
 * Created by hans on 13.04.14.
 */
public class TransactionGraph {
    public static void write(Main main, String fileName) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

            Iterator<Transaction> it = main.transactionList.iterator();
            while(it.hasNext()) {
                Transaction transaction = it.next();
                Iterator<Split> it2 = transaction.splitList.iterator();
                while(it2.hasNext()) {
                    Split split = it2.next();
                    if (split.bankid == "") continue;

                    StringBuilder sb = new StringBuilder();
                    sb.append(transaction.postdate);
                    sb.append(" ");
                    sb.append(Util.shareToString(split.shares));
                    sb.append(System.getProperty("line.separator"));

                    writer.write(sb.toString());
                }
            }

            writer.close();
        } catch (IOException e) {
            System.out.println("Error: Try creating a folder 'output' if it does not exist."); // @TODO: Create folder
            e.printStackTrace();
        }
    }
}
