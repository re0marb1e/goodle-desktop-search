package cn.re0marb1e.index;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import  cn.re0marb1e.gui.IndexAndSearch;

public class IndexBuilder{
    Directory directory;
    IndexWriterConfig iwc = null;
    public IndexWriter writer = null;
    Document doc = null;
    int numDocs;
    public IndexBuilder(String DirectoryPath){
        try {
            directory = FSDirectory.open(new File(DirectoryPath));
            iwc = new IndexWriterConfig(Version.LUCENE_35, new IKAnalyzer());
            writer = new IndexWriter(directory, iwc);
            writer.deleteAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void MainIndexBuilder(String FilePath){
        try {
            File f = new File(FilePath);
            for(File file:f.listFiles()){
                if(file.isDirectory())
                {
                    this.MainIndexBuilder(file.getAbsolutePath());
                }
                else
                {
                    //文件过滤器
                    FileFilter ff = new FileFilter(file);
                    //判断是否过滤
                    if(ff.isFilter()==false){
                        //文件解析器
                        String filetype = ff.getType();
                        FileParser fp = new FileParser(file,filetype);
                        doc = new Document();
                        //文件路径,不分词，不加权
                        doc.add(new Field("filePath",file.getAbsolutePath(),Field.Store.YES,Field.Index.NOT_ANALYZED_NO_NORMS));
                        //文件名，不分词，不加权
                        doc.add(new Field("fileName",file.getName().toLowerCase(),Field.Store.YES,Field.Index.NOT_ANALYZED_NO_NORMS));
                        //文件类型，不分词，不加权,大写转小写
                        doc.add(new Field("fileType",filetype.toLowerCase(),Field.Store.YES,Field.Index.NOT_ANALYZED_NO_NORMS));
                        //文件大小,不分词，不加权
                        doc.add(new Field("fileSize",Long.toString(file.length()),Field.Store.YES,Field.Index.NOT_ANALYZED_NO_NORMS));
                        //文件修改时间，不分词，加权
                        doc.add(new Field("fileModifiedTime",fp.getModifiedTime(),Field.Store.YES,Field.Index.NOT_ANALYZED));
                        //文件内容摘要
                        doc.add(new Field("fileSummary",fp.getSummary(),Field.Store.YES,Field.Index.ANALYZED_NO_NORMS));
                        //文件内容，分词，加权

                        doc.add(new Field("content",fp.reader(),Field.Store.NO,Field.Index.ANALYZED));
//						}
                        writer.addDocument(doc);
                        IndexAndSearch.textA1.append("添加文件："+"\n");
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (CorruptIndexException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void closeWriter(){
        if(writer!=null)
            try {
                writer.close();
            } catch (CorruptIndexException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
    public int getdocnum(){
        IndexReader reader;
        try {
            reader = IndexReader.open(directory);
            numDocs = reader.numDocs();
            reader.close();
        } catch (CorruptIndexException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return numDocs;
    }
}
