/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facilities;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Mahmoud
 */
public class Request implements Serializable{
    private RequestType type;
    public HashMap<String,String> requestData;
    public HashMap<String,List<String>> requestNames;
    public Request(RequestType t){
        type=t;
        requestData=new HashMap<>();
        requestNames=new HashMap<>();
    }
    public void setType(RequestType t){
        type=t;
    }
    public RequestType getType(){
        return type;
    }
    public void setData(String key, String value){
        requestData.put(key, value);
    }
    public String getData(String key){
        if(requestData.containsKey(key))
            return requestData.get(key);
        else
            return null;
    }
    
    
    //ramy
    public void setNames(String key, List<String> value){
        requestNames.put(key, value);
    }    
    public List<String> getNames(String key){
        if(requestNames.containsKey(key))
            return requestNames.get(key);
        else
            return null;
    }
}
