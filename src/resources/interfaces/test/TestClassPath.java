package resources.interfaces.test;

import java.nio.file.Path;
import java.nio.file.Paths;

public class TestClassPath {
    public static void main(String[] args) {
        // Specify the file path
        Path filePath = Paths.get("src/resources/images/wine.png");

        // Print the absolute path
        System.out.println("Absolute Path: " + filePath.toAbsolutePath());

        // Print the normalized path
        System.out.println("Normalized Path: " + filePath.normalize());

        // Print the file name
        System.out.println("File Name: " + filePath.getFileName());
    }

}

