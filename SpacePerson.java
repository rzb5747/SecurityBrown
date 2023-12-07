import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SpacePerson {

    public static void main(String[] args) {
        // Get the space person alphabet
        Map<Character, Character> spacePersonAlphabet = createSpacePersonAlphabet();

        // Prompt the user for input
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter an English string: ");
        String englishString = scanner.nextLine().toUpperCase(); // Convert to uppercase for consistency

        // Convert the English string to a space person string
        StringBuilder spacePersonString = new StringBuilder();
        for (char letter : englishString.toCharArray()) {
            char spacePersonSymbol = spacePersonAlphabet.getOrDefault(letter, letter);
            spacePersonString.append(spacePersonSymbol);
        }

        // Display the space person string
        System.out.println("Space Person String: " + spacePersonString.toString());

        try {
            // Get the secret key
            System.out.print("Enter a secret key for HMAC: ");
            String key = scanner.nextLine();

            // Compute and display the HMAC-SHA-256 hash of the space person string
            String hmac = calculateHMAC(spacePersonString.toString(), key);
            System.out.println("HMAC-SHA-256 Hash: " + hmac);

        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    private static Map<Character, Character> createSpacePersonAlphabet() {
        Map<Character, Character> spacePersonAlphabet = new HashMap<>();
        char[] englishAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        char[] spacePersonSymbols = "~!@#$%^&*".toCharArray();

        for (int i = 0; i < englishAlphabet.length; i++) {
            spacePersonAlphabet.put(englishAlphabet[i], spacePersonSymbols[i % spacePersonSymbols.length]);
        }

        return spacePersonAlphabet;
    }

    private static String calculateHMAC(String data, String key)
            throws NoSuchAlgorithmException, InvalidKeyException {
        String algorithm = "HmacSHA256";
        Mac mac = Mac.getInstance(algorithm);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), algorithm);
        mac.init(secretKeySpec);
        byte[] hmacBytes = mac.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(hmacBytes);
    }
}