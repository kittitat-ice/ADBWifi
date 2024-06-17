import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class ADBWifi {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n\n");
        System.out.println("~*~~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~");
        System.out.println("~*    ADB OVER WIFI BY iceDEV v1.0  *~");
        System.out.println("~*~~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~");
        System.out.println("");
        System.out.println(" * INSTRUCTION * ");
        System.out.println("1. Connect your Android device via USB cable to the computer.");
        System.out.println("2. Check the IP Address from Settings -> General or Settings -> About.");
        System.out.println("3. Enter the IP Address.");
        System.out.println("4. Disconnect the USB cable and select the device in Android Studio to check if the logcat appears.");
        System.out.println("5. If unsuccessful, close Android Studio and try again.");
        System.out.println("");
        System.out.print("Enter the IP Address of the Android device: ");
        String ipAddress = scanner.nextLine();

        System.out.println("");
        System.out.println("Running 'adb devices'...");
        runCommand("adb devices");

        System.out.println("Running 'adb tcpip 5555'...");
        runCommand("adb tcpip 5555");

        String connectCommand = "adb connect " + ipAddress + ":5555";
        System.out.println("Running '" + connectCommand + "'...");
        runCommand(connectCommand);

        scanner.close();
        System.out.println("");
    }

    private static void runCommand(String command) {
        ProcessBuilder processBuilder = new ProcessBuilder();
        String os = System.getProperty("os.name").toLowerCase();

        // Check the operating system and set the appropriate shell
        if (os.contains("win")) {
            processBuilder.command("cmd.exe", "/c", command);
        } else {
            processBuilder.command("sh", "-c", command);
        }

        try {
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("- " + line);
            }
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}