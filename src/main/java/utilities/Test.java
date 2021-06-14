package utilities;

public class Test {
    public static void main(String[] args) {
        String USD = "$13.53 USD";
        System.out.println(USD.split(" ")[0].substring(1));
    }
}
