package cn.re0marb1e.index;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.poi.POITextExtractor;
import org.apache.poi.extractor.ExtractorFactory;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.xmlbeans.XmlException;

public class FileParser {
    File file;
    String type;
    public FileParser(File file,String type){
        this.file = file;
        this.type = type;
    }
    public String getModifiedTime(){
        Calendar cal = Calendar.getInstance();
        long time = file.lastModified();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        cal.setTimeInMillis(time);
        return formatter.format(cal.getTime());
    }
    public String getSummary(){
        if(this.reader().length()<99)
        {
            return this.reader().substring(0,this.reader().length());
        }
        else
            return this.reader().substring(0,99);
    }
    public String reader(){
        //解析txt内容
        if(this.type.equalsIgnoreCase("txt")){
            Scanner scanner;
            try {
                scanner = new Scanner(file ,"GBK");
                scanner.useDelimiter("\\z");
                String buffer = "";
                while (scanner.hasNext())
                {
                    buffer+= scanner.next();
                }
                return buffer;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        //解析pdf内容
        if(this.type.equalsIgnoreCase("pdf")){
            PDDocument document=null;
            try {
                document=PDDocument.load(file.getAbsolutePath());
                PDFTextStripper stripper =new PDFTextStripper();
                String sm= stripper.getText(document);
                return sm;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //解析word，excel，ppt内容
        if(this.type.equalsIgnoreCase("doc")||this.type.equalsIgnoreCase("docx")||this.type.equalsIgnoreCase("xls")||this.type.equalsIgnoreCase("xlsx")
                ||this.type.equalsIgnoreCase("ppt")||this.type.equalsIgnoreCase("pptx")){
            try {
                POITextExtractor extractor = ExtractorFactory.createExtractor(file);
                return extractor.getText();
            } catch (InvalidFormatException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (OpenXML4JException e) {
                e.printStackTrace();
            } catch (XmlException e) {
                e.printStackTrace();
            }
        }
        //解析其他,返还原文件名
        return file.getName();
    }
}
