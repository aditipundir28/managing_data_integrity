import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class keyGeneration {
    /**
     * @param <Pairing>
     * @param <ElementPowPreProcessing>
     * @param args
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        // Read input file
        String inputFilePath = "Testing.txt";
        String text = readFile(inputFilePath);
        // Preprocess text
        text = Preprocessor.removeSpecialSymbols(text);
        List<String> tokens = Preprocessor.tokenize(text);
        tokens = Preprocessor.removeStopWords(tokens);
        // Create matrix
        List<String> matrix = bucketCreation.matrixTranslation(String.join(" ", tokens));

        // Generate MD5 hash
        String tokeString = String.join(" ", matrix);
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(tokeString.getBytes());
        byte[] digest = md5.digest();
        String md5Hash = bytesToHex(digest);

        // Reverse cycle Cipher encryption
        String encryptedMD5 = reverseCycleCipher(md5Hash);

        // Perform avalanche effect on encrypted MD5 hash
        StringBuilder sb = new StringBuilder(encryptedMD5);
        for (int i = 0; i < sb.length(); i++) {
            char c = sb.charAt(i);
            if (Character.isLetter(c)) {
                if (Character.isLowerCase(c)) {
                    sb.setCharAt(i, Character.toUpperCase(c));
                } else {
                    sb.setCharAt(i, Character.toLowerCase(c));
                }
            } else if (Character.isDigit(c)) {
                // Extract the digit
                int digit = Character.getNumericValue(c);
                // Perform the avalanche effect on the digit
                digit = 9 - digit;
                // Replace the digit in the StringBuilder
                sb.setCharAt(i, Character.forDigit(digit, 10));
            }
        }
        String avalancheMD5 = sb.toString();

        // Print the encrypted MD5 hash with avalanche effect
        System.out.println("Encrypted MD5 hash with avalanche effect: " + avalancheMD5);

        //      // Store the encrypted MD5 hash in MySQL database
    //      try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/data_Keys", "aditipundir", "123456@DI")) {
    //         String insertQuery = "INSERT INTO md5_keys (hash) VALUES (?)";
    //         try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
    //             statement.setString(1, avalancheMD5);
    //             statement.executeUpdate();
    //         }
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }
    // }
    
        // Write the encrypted MD5 hash with avalanche effect to a file
        String outputFilePath = "encrypted_keys.txt";
        Path outputPath = Paths.get(outputFilePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath.toFile()))) {
            writer.write(avalancheMD5);
        }
        // Move the output file to Dropbox
        Path dropboxPath = Paths.get("C:/Users/aditi/Dropbox/Apps/majoryearproject/encrypted_keys.txt");
        Files.move(outputPath, dropboxPath, StandardCopyOption.REPLACE_EXISTING);
    }
    /**
     * Reads a file and returns its contents as a string.
     * @param filePath
     * @return
     * @throws IOException
     */
    private static String readFile(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line = null;
        StringBuilder stringBuilder = new StringBuilder();
        String ls = System.getProperty("line.separator");
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
            stringBuilder.append(ls);
        }
        return stringBuilder.toString();
    }
    /**
     * Converts a byte array to a hexadecimal string.
     * @param bytes
     * @return
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xFF & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
    /**
    * Applies reverse cycle cipher encryption on the given string.
    * @param str
    * @return
     */
    private static String reverseCycleCipher(String str) {
        int length = str.length();
        StringBuilder sb = new StringBuilder(length);
        for (int i = length - 1; i >= 0; i--) {
            sb.append(str.charAt(i));
        }
        for (int i = length - 1; i >= 0; i--) {
            sb.append(str.charAt(i));
        }
        return sb.toString();
    }
}


