import java.util.Stack;

/**
 * This class encapsulates a group of database commands to simulate a SQL-like transaction. The database commands that
 * belong to a transaction are stored in a FILO stack. When a transaction is rolled back, each command is popped from
 * the stack and undone one by one. Note that the transaction object contains only commands that modify the in-memory
 * database, such as Set and Unset, and does not contain any read-only commands like Get or NumEqualTo. This ensures
 * that each transaction consumes at most O(M) additional memory, where M is the number of variables updated in the transaction.
 */
public class Transaction {
    private final Database db;
    private final Stack<Command> cmdStack = new Stack<>();

    public Transaction(Database db) {
        this.db = db;
    }

    public void record(Command cmd) {
        cmdStack.push(cmd);
    }

    public void rollback() {
        while (!cmdStack.isEmpty()) {
            Command cmd = cmdStack.pop();
            cmd.undo(db);
        }
    }
}
