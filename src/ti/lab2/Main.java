package ti.lab2;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        RSA RSAcipher = new RSA();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите строку для шифрования (Q для выхода):\n");
        String key = scanner.nextLine();
        while (!key.equals("Q")) {
            RSAcipher.mainRSA(key);
            System.out.println("\nВведите строку для шифрования (Q для выхода):\n");
            key = scanner.nextLine();
        }
    }
}
