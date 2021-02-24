

import java.math.BigInteger;

import java.util.Scanner;
//-------------------------   Alireza Rashidi  96243096
public class AESRun3 {

    public static void main(String[] args) {
        BigInteger p, g, a, b, key;
        Scanner scanner = new Scanner(System.in);
        p = new BigInteger(scanner.next());
        g = new BigInteger(scanner.next());
        a = new BigInteger(scanner.next());
        b = new BigInteger(scanner.next());

        key = g.modPow(a.multiply(b), p);
        System.out.print(g.modPow(a, p) + " " + g.modPow(b, p) + " " + key);
    }
}
