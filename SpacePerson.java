import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
            // Compute and display the SHA-256 hash of the space person string
            String hash = calculateSHA256(spacePersonString.toString());
            System.out.println("SHA-256 Hash of Space Person String: " + hash);


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        // Separate Caesar cipher part
        System.out.print("Enter shift value (integer) for Caesar cipher: ");
        int shiftValue = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character left by nextInt()
        bruteForceDecrypt(englishString);


        // Perform Caesar cipher shift on the English alphabet
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

    private static String calculateSHA256(String data) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(data.getBytes());

        StringBuilder hashStringBuilder = new StringBuilder();
        for (byte hashByte : hashBytes) {
            hashStringBuilder.append(String.format("%02x", hashByte));
        }

        return hashStringBuilder.toString();
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

