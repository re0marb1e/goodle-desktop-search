package cn.re0marb1e.index;

import java.io.File;

import cn.re0marb1e.gui.IndexAndSearch;

//文件过滤器，根据索引复选框选择是否索引该文件。。。
public class FileFilter {
    File file;
    String type;
    public FileFilter(File file){
        this.file = file;
        this.type = file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf(".")+1);
    }
    public String getType(){
        return type;
    }
    public Boolean isFilter(){
        if(this.type.equalsIgnoreCase("txt")){
            return !IndexAndSearch.jcb1.isSelected();
        }
        if(this.type.equalsIgnoreCase("pdf")){
            return !IndexAndSearch.jcb2.isSelected();
        }
        if(this.type.equalsIgnoreCase("doc")||this.type.equalsIgnoreCase("docx")){
            return !IndexAndSearch.jcb3.isSelected();
        }
        if(this.type.equalsIgnoreCase("xls")||this.type.equalsIgnoreCase("xlsx")){
            return !IndexAndSearch.jcb4.isSelected();
        }
        //过滤ppt
        if(this.type.equalsIgnoreCase("ppt")||this.type.equalsIgnoreCase("pptx")){
            return !IndexAndSearch.jcb5.isSelected();
        }
        //过滤视频
        if(this.type.equalsIgnoreCase("rmvb")||this.type.equalsIgnoreCase("rm")||
                this.type.equalsIgnoreCase("wmv")||this.type.equalsIgnoreCase("avi")||
                this.type.equalsIgnoreCase("mkv")||this.type.equalsIgnoreCase("flv")||
                this.type.equalsIgnoreCase("mp4")||this.type.equalsIgnoreCase("mpg")){
            return !IndexAndSearch.jcb6.isSelected();
        }
        //过滤音频
        if(this.type.equalsIgnoreCase("mp3")||this.type.equalsIgnoreCase("wma")||
                this.type.equalsIgnoreCase("ape")||this.type.equalsIgnoreCase("flac")||
                this.type.equalsIgnoreCase("aac")){
            return !IndexAndSearch.jcb7.isSelected();
        }
        //过滤图片
        if(this.type.equalsIgnoreCase("jpg")||this.type.equalsIgnoreCase("bmp")||
                this.type.equalsIgnoreCase("tiff")||this.type.equalsIgnoreCase("gif")||
                this.type.equalsIgnoreCase("raw")){
            return !IndexAndSearch.jcb8.isSelected();
        }
        return !IndexAndSearch.jcb9.isSelected();
    }
}
