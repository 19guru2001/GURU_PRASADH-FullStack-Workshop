import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Base64;

public class DatabaseConfig implements Externalizable {
    private static final long serialVersionUID = 1L;
    private static final int XOR_KEY = 0x5A;
    
    private String host;
    private int port;
    private String username;
    private String password;
    
    public DatabaseConfig() {
    }
    
    public DatabaseConfig(String host, int port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }
    
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(host);
        out.writeInt(port);
        out.writeUTF(username);
        out.writeUTF(encryptPassword(password));
    }
    
    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.host = in.readUTF();
        this.port = in.readInt();
        this.username = in.readUTF();
        this.password = decryptPassword(in.readUTF());
    }
    
    private String encryptPassword(String password) {
        if (password == null) {
            return null;
        }
        byte[] bytes = password.getBytes();
        byte[] encrypted = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            encrypted[i] = (byte) (bytes[i] ^ XOR_KEY);
        }
        return Base64.getEncoder().encodeToString(encrypted);
    }
    
    private String decryptPassword(String encryptedPassword) {
        if (encryptedPassword == null) {
            return null;
        }
        byte[] encrypted = Base64.getDecoder().decode(encryptedPassword);
        byte[] decrypted = new byte[encrypted.length];
        for (int i = 0; i < encrypted.length; i++) {
            decrypted[i] = (byte) (encrypted[i] ^ XOR_KEY);
        }
        return new String(decrypted);
    }
    
    public String getHost() {
        return host;
    }
    
    public void setHost(String host) {
        this.host = host;
    }
    
    public int getPort() {
        return port;
    }
    
    public void setPort(int port) {
        this.port = port;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    @Override
    public String toString() {
        return "DatabaseConfig{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", username='" + username + '\'' +
                ", password='***'" +
                '}';
    }
}