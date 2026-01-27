import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;

public class ConfigManager {
    
    public static void saveConfig(AppConfig config, Path file) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(file.toFile());
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(config);
        }
    }
    
    public static AppConfig loadConfig(Path file) throws IOException, ClassNotFoundException {
        try (FileInputStream fis = new FileInputStream(file.toFile());
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (AppConfig) ois.readObject();
        }
    }
    
    public static void saveDatabaseConfig(DatabaseConfig config, Path file) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(file.toFile());
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(config);
        }
    }
    
    public static DatabaseConfig loadDatabaseConfig(Path file) throws IOException, ClassNotFoundException {
        try (FileInputStream fis = new FileInputStream(file.toFile());
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (DatabaseConfig) ois.readObject();
        }
    }
}