import java.util.Scanner;
import java.util.Arrays;
import java.security.SecureRandom;

public class XOR {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Welcome message and instructions
        
        System.out.println("  XOR Encryption/Decryption  ");
        System.out.println("E = Encrypt a message");
        System.out.println("D = Decrypt a message");
        System.out.println();

        // Ask user what they want to do
        System.out.print("Enter E or D: ");
        String choice = scanner.nextLine().trim().toUpperCase();

        if (choice.equals("E")) {
            encrypt(scanner);
        } else if (choice.equals("D")) {
            decrypt(scanner);
        } else {
            System.out.println("Invalid choice. Please enter E or D.");
        }

        scanner.close();
    }

    // ENCRYPT
    static void encrypt(Scanner scanner) {

        // Get the message from the user
        System.out.print("Enter your message: ");
        String message = scanner.nextLine();

        // Make sure it's not empty
        if (message.isEmpty()) {
            System.out.println("Error: Message cannot be empty.");
            return;
        }

        // Convert message to bytes
        byte[] messageBytes = message.getBytes();

        // Generate a random key the same length as the message
        // SecureRandom with no seed = unpredictable (safe)
        // Original code used seed=10 which is predictable (unsafe)
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[messageBytes.length];
        random.nextBytes(key);

        // XOR the message with the key to get the ciphertext
        byte[] ciphertext = xor(messageBytes, key);

        // Show the results
        System.out.println();
        System.out.println("--- Encryption Result ---");
        System.out.println("Ciphertext (hex): " + toHex(ciphertext));
        System.out.println("Key        (hex): " + toHex(key));
        System.out.println();
        System.out.println("IMPORTANT: Save the key! You need it to decrypt.");

        // Clear sensitive data from memory when done
        Arrays.fill(messageBytes, (byte) 0);
        Arrays.fill(key, (byte) 0);
        Arrays.fill(ciphertext, (byte) 0);
    }

    // DECRYPT
    static void decrypt(Scanner scanner) {

        // Get the ciphertext
        System.out.print("Enter ciphertext (hex): ");
        String ciphertextHex = scanner.nextLine().trim();

        // Get the key
        System.out.print("Enter key (hex): ");
        String keyHex = scanner.nextLine().trim();

        // Validate that both inputs look like hex
        if (!isValidHex(ciphertextHex) || !isValidHex(keyHex)) {
            System.out.println("Error: Input must be a valid hex string (e.g. 4a2f...).");
            return;
        }

        byte[] ciphertext = fromHex(ciphertextHex);
        byte[] key        = fromHex(keyHex);

        // Key must be the same length as the ciphertext
        if (key.length != ciphertext.length) {
            System.out.println("Error: Key length does not match ciphertext length.");
            Arrays.fill(key, (byte) 0);
            return;
        }

        // XOR the ciphertext with the key to recover the original message
        byte[] messageBytes = xor(ciphertext, key);
        String message = new String(messageBytes);

        System.out.println();
        System.out.println("--- Decryption Result ---");
        System.out.println("Message: " + message);

        // Clear sensitive data from memory when done
        Arrays.fill(messageBytes, (byte) 0);
        Arrays.fill(key, (byte) 0);
        Arrays.fill(ciphertext, (byte) 0);
    }

    // HELPER: XOR two byte arrays together
    // Security of XOR depends entirely on the key being random
    // and never reused. ciphertext = message XOR key
    static byte[] xor(byte[] data, byte[] key) {
        byte[] result = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            result[i] = (byte) (data[i] ^ key[i]);
        }
        return result;
    }

    // HELPER: Convert bytes to a readable hex string
    // e.g. [72, 101] -> "4865"
    static String toHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    // HELPER: Convert a hex string back to bytes
    // e.g. "4865" -> [72, 101]
    static byte[] fromHex(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hex.substring(i * 2, i * 2 + 2), 16);
        }
        return bytes;
    }

    
    // Checking if a string is valid hex
    // Must be even length and contain only 0-9, a-f, A-F
   
    static boolean isValidHex(String s) {
        if (s == null || s.isEmpty() || s.length() % 2 != 0) return false;
        return s.matches("[0-9a-fA-F]+");
    }
}
