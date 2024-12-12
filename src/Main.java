public class Main {
    public static void main(String[] args) {
        InMemoryDB db = new InMemoryDBImpl();

        System.out.println("get(A): " + db.get("A")); 

      
        try {
            db.put("A", 5);
        } catch (Exception e) {
            System.out.println("Expected error: " + e.getMessage());
        }

        db.begin_transaction();
        db.put("A", 5);
        System.out.println("get(A): " + db.get("A")); // null
        db.put("A", 6);
        db.commit();
        System.out.println("get(A): " + db.get("A")); // 6

        try {
            db.commit();
        } catch (Exception e) {
            System.out.println("Expected error: " + e.getMessage());
        }

        try {
            db.rollback();
        } catch (Exception e) {
            System.out.println("Expected error: " + e.getMessage());
        }

        System.out.println("get(B): " + db.get("B")); // null

        db.begin_transaction();
        db.put("B", 10);
        db.rollback();

        System.out.println("get(B): " + db.get("B")); // null
    }
}

