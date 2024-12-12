import java.util.HashMap;
import java.util.Map;

public class InMemoryDBImpl implements InMemoryDB {
    
    private Map<String, Integer> committedData;
    private Map<String, Integer> transactionData;
    private boolean inTransaction;

    public InMemoryDBImpl() {
        this.committedData = new HashMap<>();
        this.transactionData = null;
        this.inTransaction = false;
    }

    @Override
    public Integer get(String key) {
        return committedData.get(key);
    }

    @Override
    public void put(String key, int val) {
        if (!inTransaction) {
            throw new IllegalStateException("You must start a transaction before performing operations.");
        }
        transactionData.put(key, val);
    }

    @Override
    public void begin_transaction() {
        if (inTransaction) {
            throw new IllegalStateException("A transaction is already in progress. Please commit or rollback the current transaction.");
        }
        inTransaction = true;
        transactionData = new HashMap<>();
    }

    @Override
    public void commit() {
        if (!inTransaction) {
            throw new IllegalStateException("There is no active transaction to commit. Please start a transaction first.");
        }
        // Apply changes to committedData
        for (Map.Entry<String, Integer> entry : transactionData.entrySet()) {
            committedData.put(entry.getKey(), entry.getValue());
        }
        // End transaction
        transactionData = null;
        inTransaction = false;
    }

    @Override
    public void rollback() {
        if (!inTransaction) {
            throw new IllegalStateException("There is no active transaction to rollback. Please start a transaction first.");
        }
        // Discard changes
        transactionData = null;
        inTransaction = false;
    }
}

