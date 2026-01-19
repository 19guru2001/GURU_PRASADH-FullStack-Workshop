import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;

public class AppConfig implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String appName;
    private String version;
    private int maxConnections;
    private int timeout;
    private transient LocalDateTime lastLoaded;
    
    public AppConfig(String appName, String version, int maxConnections, int timeout) {
        this.appName = appName;
        this.version = version;
        this.maxConnections = maxConnections;
        this.timeout = timeout;
        this.lastLoaded = LocalDateTime.now();
    }
    
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        this.lastLoaded = LocalDateTime.now();
    }
    
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
    }
    
    public String getAppName() {
        return appName;
    }
    
    public void setAppName(String appName) {
        this.appName = appName;
    }
    
    public String getVersion() {
        return version;
    }
    
    public void setVersion(String version) {
        this.version = version;
    }
    
    public int getMaxConnections() {
        return maxConnections;
    }
    
    public void setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
    }
    
    public int getTimeout() {
        return timeout;
    }
    
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
    
    public LocalDateTime getLastLoaded() {
        return lastLoaded;
    }
    
    @Override
    public String toString() {
        return "AppConfig{" +
                "appName='" + appName + '\'' +
                ", version='" + version + '\'' +
                ", maxConnections=" + maxConnections +
                ", timeout=" + timeout +
                ", lastLoaded=" + lastLoaded +
                '}';
    }
}