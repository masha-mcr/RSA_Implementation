package ti.lab2;


public class RSA {
    private int r;
    private int e;
    private int d;
    private int offset;

    public void mainRSA(final String key){

        int p = getPrime();
        int q = getPrime();

        keyGenerator(p, q);
        int[] encr = encrypt(key);
        System.out.println("Шифрование: " + encryptToString(encr));
        System.out.println("Дешифрование: " + decrypt(encr));

    }

    private int getPrime(){
        int tmp = (int) (Math.random() * 31 + 2);
        while (!isPrime(tmp))
            tmp++;
        return tmp;
    }

    private boolean isPrime(final int n){
        for (int i = 2; i <= Math.sqrt(n); i++){
            if (n % i == 0) return false;
        }
        return true;
    }

    private void keyGenerator (final int p, final int q){
        int phi = (p - 1) * (q - 1);
        r = p * q;
        e = getMutuallyPrime(phi);
        d = euclidex(phi, e);
    }

    private int getMutuallyPrime (final int n) {
        boolean first = true;
        for (int i = 2; i <= n; i++){
            if (GCD(n, i) == 1) {
                if (!first)
                    return i;
                else
                    first = false;
            }
        }
        return n - 1;
    }

    private int GCD(final int a, final int b) {
        if (b == 0 || a == 0) return a + b;
        return GCD(b,a % b);
    }

    private int euclidex(final int a, final int b){
        int d0 = a;
        int d1 = b;
        int x0 = 1;
        int x1 = 0;
        int y0 = 0;
        int y1 = 1;

        while (d1 > 1){
            int q = d0 / d1;
            int d2 = d0 % d1;
            int x2 = x0 - q * x1;
            int y2 = y0 - q * y1;

            d0 = d1;
            d1 = d2;
            x0 = x1;
            x1 = x2;
            y0 = y1;
            y1 = y2;
        }

        if (y1 > 0)
            return y1;
        else
            return y1 + a;
    }

    private int fastExp(final int a, final int z, final int n){
        int a1 = a;
        int z1 = z;
        int x = 1;

        while(z1 != 0){
            while (z1 % 2 == 0){
                z1 /= 2;
                a1 = (a1 * a1) % n;
            }
            z1--;
            x = (x * a1) % n;
        }
        return x;
    }

    private int[] encrypt(String key){
        key = key.replace(" ","");
        int[] encrypted = new int[key.length()];

        if (key.charAt(0) < 'А'){
            if (key.charAt(0) >= 'a') offset = 'a';
            else offset = 'A';
        }
        else {
            if (key.charAt(0) >= 'а') offset = 'а';
            else offset = 'А';
        }


        for (int i = 0; i < key.length(); i++){
            int tmp =  key.charAt(i) - offset + 1;
            encrypted[i] = fastExp(tmp, e, r);
        }

        return encrypted;
    }

    private String encryptToString(int[] encrypted){
        StringBuilder result = new StringBuilder();
        for (int j : encrypted) {
            result.append(j);
        }
        return result.toString();
    }

    private String decrypt(int[] encrypted){
        int[] decrypted = new int[encrypted.length];

        for (int i = 0; i < encrypted.length; i++){
            decrypted[i] = fastExp(encrypted[i], d, r) + offset - 1;
        }

        StringBuilder result = new StringBuilder();
        for (int j : decrypted) {
            result.append((char) j);
        }
        return result.toString();

    }

}
