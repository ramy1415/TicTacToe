/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xosingle;


public class XoSingleModel {
    
    private String[][] arr=new String[3][3];

    public XoSingleModel() {
        int h=0;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                arr[i][j]=new String(Integer.toString(h++));
            }
        }
    }
    
    public void newGame(){
        int h=0;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                arr[i][j]=new String(Integer.toString(h++));
            }
        }
    }
    
    
    
    public void setarr(int x,int y,String z){
        
        arr[x][y]=new String(z);
    }
    
    public String getarr(int x,int y){
        return arr[x][y];
    }
    
    
    public boolean checkwinner(){
        if(arr[0][0].equals(arr[0][1])&&arr[0][1].equals(arr[0][2])){return true;}
        else if(arr[1][0].equals(arr[1][1])&&arr[1][1].equals(arr[1][2])){return true;}
        else if(arr[2][0].equals(arr[2][1])&&arr[2][1].equals(arr[2][2])){return true;}
        else if(arr[0][0].equals(arr[1][0])&&arr[1][0].equals(arr[2][0])){return true;}
        else if(arr[0][1].equals(arr[1][1])&&arr[1][1].equals(arr[2][1])){return true;}
        else if(arr[0][2].equals(arr[1][2])&&arr[1][2].equals(arr[2][2])){return true;}
        else if(arr[0][0].equals(arr[1][1])&&arr[1][1].equals(arr[2][2])){return true;}
        else if(arr[0][2].equals(arr[1][1])&&arr[1][1].equals(arr[2][0])){return true;}
        
        return false;
    }
    
    
}
