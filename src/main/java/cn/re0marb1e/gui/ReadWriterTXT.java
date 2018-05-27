package cn.re0marb1e.gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;

//读写txt文本;
public class ReadWriterTXT{
    File txt;
    //构造函数;
    public ReadWriterTXT(File txt){
        this.txt = txt;
    }
    //返还txt文本的内容;
    public String reader(){
        String str = "";
        try {
            Scanner scanner = new Scanner(txt,"GBK");
            scanner.useDelimiter("\\z");
            while (scanner.hasNext())
            {
                str+= scanner.next();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return str;
    }
    //把txt文本中的oldStr换成newStr;
    public void replace(String oldStr,String newStr) {
        String temp = "";
        try {
            FileInputStream fis = new FileInputStream(this.txt);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuffer buf = new StringBuffer();
            // 保存该行前面的内容
            for (; (temp = br.readLine()) != null&& !temp.equals(oldStr);)
            {
                buf = buf.append(temp);
                buf = buf.append(System.getProperty("line.separator"));
            }
            // 将内容插入
            buf = buf.append(newStr);
            // 保存该行后面的内容
            while ((temp = br.readLine()) != null) {
                buf = buf.append(System.getProperty("line.separator"));
                buf = buf.append(temp);
            }
            br.close();
            FileOutputStream fos = new FileOutputStream(this.txt);
            PrintWriter pw = new PrintWriter(fos);
            pw.write(buf.toString().toCharArray());
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}