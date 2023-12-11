import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.security.InvalidKeyException;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class SpacePerson {

    public static void main(String[] args) {

        Map<Character, Character> spacePersonAlphabet = createSpacePersonAlphabet();


        Scanner scanner = new Scanner(System.in);
        System.out.print("English string: ");
        String englishString = scanner.nextLine().toUpperCase();

        StringBuilder spacePersonString = new StringBuilder();
        for (char letter : englishString.toCharArray()) {
            char spacePersonSymbol = spacePersonAlphabet.getOrDefault(letter, letter);
            spacePersonString.append(spacePersonSymbol);
        }

        System.out.println("Space Person: " + spacePersonString.toString());

        try {
            String hmacKey = "PSU";
            String hmac = calculateHMAC(spacePersonString.toString(), hmacKey);
            System.out.println("HMAC-SHA256: " + hmac);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }

        System.out.print("Shift value for Caesar cipher: ");
        int shiftValue = scanner.nextInt();
        scanner.nextLine(); 
        bruteForceDecrypt(englishString);



        String encryptedCaesarCipher = encrypt(englishString, shiftValue);
        System.out.println("Caesar Cipher: " + encryptedCaesarCipher);


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

    private static String encrypt(String plaintext, int shift) {
        StringBuilder encryptedText = new StringBuilder();

        for (char character : plaintext.toCharArray()) {
            if (Character.isLetter(character)) {
                char base = Character.isLowerCase(character) ? 'a' : 'A';
                int originalAlphabetPosition = character - base;
                int newAlphabetPosition = (originalAlphabetPosition + shift + 26) % 26;
                char newCharacter = (char) (base + newAlphabetPosition);
                encryptedText.append(newCharacter);
            } else {
                encryptedText.append(character);
            }
        }

        return encryptedText.toString();
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
    private static void bruteForceDecrypt(String encryptedText) {
        for (int shift = 0; shift < 26; shift++) {
            StringBuilder decryptedText = new StringBuilder();

            for (char character : encryptedText.toCharArray()) {
                if (Character.isLetter(character)) {
                    char base = Character.isLowerCase(character) ? 'a' : 'A';
                    int originalAlphabetPosition = character - base;
                    int newPosition = (originalAlphabetPosition - shift + 26) % 26;
                    char newCharacter = (char) (base + newPosition);
                    decryptedText.append(newCharacter);
                } else {
                    decryptedText.append(character);
                }
            }

            System.out.println("Shift " + shift + ": " + decryptedText);
        }
    }
}

