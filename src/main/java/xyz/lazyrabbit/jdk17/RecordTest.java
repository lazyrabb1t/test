package xyz.lazyrabbit.jdk17;

public class RecordTest {
    record UserRecord(String name, Integer age) {
        UserRecord {

        }
    }

    public static void main(String[] args) {
        UserRecord userRecord = new UserRecord("rabb", 18);
    }
}
