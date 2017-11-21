package ir.mfava.modfava.pardazesh.model.DTO;

import java.io.Serializable;
import java.util.Map;

public class JSONMessage implements Serializable {
    private Integer status;
    private String message;
    private Map<String,Object> data;

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public static JSONMessage Success(String message){
        JSONMessage jsonMessage = new JSONMessage();
        jsonMessage.setStatus(200);
        jsonMessage.setMessage(message);
        return jsonMessage;
    }

    public static JSONMessage Success(Map<String,Object> data){
        JSONMessage jsonMessage = new JSONMessage();
        jsonMessage.setStatus(200);
        jsonMessage.setMessage("");
        jsonMessage.setData(data);
        return jsonMessage;
    }

    public static JSONMessage Success(){
        JSONMessage jsonMessage = new JSONMessage();
        jsonMessage.setStatus(200);
        return jsonMessage;
    }

    public static JSONMessage Success(String message,Map<String,Object> data){
        JSONMessage jsonMessage = new JSONMessage();
        jsonMessage.setStatus(200);
        jsonMessage.setData(data);
        jsonMessage.setMessage(message);
        return jsonMessage;
    }

    public static JSONMessage Error(String message){
        JSONMessage jsonMessage = new JSONMessage();
        jsonMessage.setStatus(200);
        jsonMessage.setMessage(message);
        return jsonMessage;
    }
}
