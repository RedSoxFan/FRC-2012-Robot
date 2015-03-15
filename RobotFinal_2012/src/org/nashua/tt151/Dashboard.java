package org.nashua.tt151;

import com.sun.squawk.debugger.Log;
import com.sun.squawk.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import javax.microedition.io.Connector;
import javax.microedition.io.SocketConnection;
public class Dashboard {
    private Connection con;
    public Dashboard(String host,ConnectionListener cl){
        con = new Connection(host,1735,cl);
    }
    public void updatePWM(String put,int i, String name, String value) throws IOException {
        con.sendData("PWM["+put+","+i+","+name+","+value+"]");
    }
    public void updateInfo(String str, String value) throws IOException {
        con.sendData("INFO["+str+","+value+"]");
    }
    public void sendRaw(String str) throws IOException {
        con.sendData(str);
    }
    public String requestInfo(String req) throws IOException {
        String r = "REQUEST["+req+"]";
        con.sendData(r);
        r = con.requestData(req);
        return r;
    }
    public static interface ConnectionListener {
        public void onDataRecieved(String str);
    }
    private static class Connection {
        private SocketConnection sock;
        private InputStreamReader reader;
        private BufferedWriter writer;
        private ConnectionListener listener;
        protected String[][] str = new String[0][0];
        public Connection(String host,int port,ConnectionListener cl) {
            listener = cl;
            try {
                // Connect To Server
                sock = (SocketConnection) Connector.open("socket://"+host+":"+port);
                reader = new InputStreamReader(sock.openDataInputStream());
                writer = new BufferedWriter(new OutputStreamWriter(sock.openDataOutputStream()));
                // Listen For Data
                new java.util.Timer().schedule(new java.util.TimerTask(){
                    public void run(){
                        if (sock!=null) {

                            try {
                                if (reader.ready()) {
                                    char[] buffer = new char[50];
                                    reader.read(buffer);
                                    String stri = "";
                                    for (int i=0;i<buffer.length;i++) stri+=buffer[i]=='\0'?"":""+buffer[i];
                                    if (stri!=null) onDataRecieved(stri);
                                }
                            } catch (Exception e) {
                                Log.log("Failed To Read - "+e.getMessage());
                            }
                        }
                    }
                },1,100);
            } catch (Exception ex) {
                Log.log("Failed To Create Server - "+ex.getMessage());
            }
        }
        public void sendData(String str) throws IOException{
            writer.write(str);
            writer.newLine();
            writer.flush();
	}
	protected void onDataRecieved(String str){
            System.out.println(str);
            if (str.startsWith("RETURN")) {
                update(str.substring(str.indexOf(",")+1,str.indexOf("&")),str.substring(str.indexOf("&")+1));
            } else {
                listener.onDataRecieved(str);
            }
	}
        public String requestData(String req) {
            String stri=null;
            while ((stri=grab(req))==null) {}
            return stri;
        }
        public String grab(String key) {
            for (int i=0;i<str.length;i++)
                if (str[i][0].equals(key))
                    return str[i][1];
            return null;
        }
        public void update(String key,String value) {
            for (int i=0;i<str.length;i++) {
                if (str[i][0].equals(key)) {
                    if (value==null) {
                        str[i]=null;
                        clean();
                    } else {
                        str[i][1]=value;
                    }
                    return;
                }
            }
            add(key,value);
        }
        public void add(String key, String value){
            clean();
            String[][] strr = new String[str.length+1][2];
            System.arraycopy(str, 0, strr, 0, str.length);
            strr[str.length] = new String[]{key,value};
            str=strr;
        }
        public void clean() {
            int c=0;
            for (int i=0;i<str.length;i++) c+=str[i]!=null?1:0;
            String[][] strr = new String[c][2];
            int x=0;
            for (int i=0;i<str.length;i++) if (str[i]!=null) {strr[x]=str[i];x++;}
            str=strr;
        }
    }
}