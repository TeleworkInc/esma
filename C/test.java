// import java.System.nanoTime;

class Simple {
    public static void main(String args[]) {
        long startTime = System.nanoTime();
        for (int i = 0; i < 5000; i++) continue;
        long duration = System.nanoTime() - startTime;
        System.out.println(duration/10e3 + "us");
    }
}