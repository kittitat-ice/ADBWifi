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
        System.out.println("1. เสียบสาย USB android กับ computer");
        System.out.println("2. เช็ค IP Address จาก Setting -> General หรือ Setting -> About");
        System.out.println("3. กรอก IP Address");
        System.out.println("4. ทดลองถอดสาย USB แล้วเลือก Device ใน Android Studio เพื่อเช็ค Logcat ว่า log ขึ้นหรือไม่");
        System.out.println("5. ถ้าไม่สำเร็จ ปิด Android studio แล้วทำใหม่อีกครั้ง");
        System.out.println("");
        System.out.print("กรอก IP Address ของ WIFI จากเครื่อง Android: ");
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
            while (!isNullOrEmpty(line = reader.readLine())) {
                System.out.println("- " + line);
            }
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static boolean isNullOrEmpty(String line) {
        return line == null || line.isBlank();
    }
}