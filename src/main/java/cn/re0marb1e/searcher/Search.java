package cn.re0marb1e.searcher;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import  cn.re0marb1e.gui.IndexAndSearch;

public class Search {
    Directory directory;
    IndexReader reader = null;
    IndexSearcher searcher = null;
    private int hits;
    String filepath[];
    String filesize[];
    String filemodifiedtime[];
    String filesummary[];
    TopDocs tds;
    int numDocs;
    //构造函数;
    public Search(String DirectoryPath){
        try {
            directory = FSDirectory.open(new File(DirectoryPath));
            reader = IndexReader.open(directory);
            searcher = new IndexSearcher(reader);
            this.numDocs = reader.numDocs();
        }catch (CorruptIndexException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //根据JComboBox选择Field域;
    public String getField(Object item){
        if(item=="内容"){
            return "content";
        }
        else
        {
            return "fileName";
        }
    }
    //查询，生成查询结果，并返还结果数量;
    public int getHitsAndTopDocs(String field, String searchcontent){
        if(searchcontent.equalsIgnoreCase("")){
            searchcontent = "*";
        }
        if(field == "fileName"){
            searchcontent = "*" + searchcontent + "*";
        }
        BooleanQuery query = new BooleanQuery();
        QueryParser parser = new QueryParser(Version.LUCENE_35,field,new IKAnalyzer());
        parser.setAllowLeadingWildcard(true);
        try {
            query.add(parser.parse(searchcontent),Occur.MUST);
        } catch (ParseException e2) {
            e2.printStackTrace();
        }
        try {
            query.add(parser.parse(searchcontent),Occur.MUST);
            if(IndexAndSearch.jrb1.isSelected()){
            }
            if(IndexAndSearch.jrb2.isSelected()){
                query.add(parser.parse("fileType:txt"),Occur.MUST);
            }
            if(IndexAndSearch.jrb3.isSelected()){
                query.add(parser.parse("fileType:pdf"),Occur.MUST);
            }
            if(IndexAndSearch.jrb4.isSelected()){
                query.add(parser.parse("fileType:doc fileType:docx"),Occur.MUST);
            }
            if(IndexAndSearch.jrb5.isSelected()){
                query.add(parser.parse("fileType:xls fileType:xlsx"),Occur.MUST);
            }
            if(IndexAndSearch.jrb6.isSelected()){
                query.add(parser.parse("fileType:ppt fileType:pptx"),Occur.MUST);
            }
            if(IndexAndSearch.jrb7.isSelected()){
                query.add(parser.parse("fileType:rmvb fileType:rm fileType:wmv fileType:avi fileType:mkv fileType:flv fileType:mp4 fileType:mpg"),Occur.MUST);
            }
            if(IndexAndSearch.jrb8.isSelected()){
                query.add(parser.parse("fileType:mp3 fileType:wma fileType:ape fileType:flac fileType:aac"),Occur.MUST);
            }
            if(IndexAndSearch.jrb9.isSelected()){
                query.add(parser.parse("fileType:jpg fileType:bmp fileType:tiff fileType:gif fileType:raw"),Occur.MUST);
            }
            if(IndexAndSearch.jrb10.isSelected()){
                query.add(parser.parse("- fileType:txt - fileType:pdf - fileType:doc - fileType:docx - fileType:xls - fileType:xlsx - fileType:ppt - fileType:pptx"), Occur.MUST_NOT);
                query.add(parser.parse("-fileType:rmvb -fileType:rm -fileType:wmv -fileType:avi -fileType:mkv -fileType:flv -fileType:mp4 -fileType:mpg"),Occur.MUST_NOT);
                query.add(parser.parse("-fileType:mp3 -fileType:wma -fileType:ape -fileType:flac -fileType:aac"),Occur.MUST_NOT);
                query.add(parser.parse("-fileType:jpg -fileType:bmp -fileType:tiff -fileType:gif -fileType:raw"),Occur.MUST_NOT);
            }
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        try {
            tds = searcher.search(query,numDocs);
            hits = tds.totalHits;
        } catch (IOException e){
            e.printStackTrace();
        }
        filepath = new String[hits];
        filesize = new String[hits];
        filemodifiedtime = new String[hits];
        filesummary = new String[hits];
        return hits;
    }
    //得到查询结果，并传入数组中;
    public void getResult(){
        int i = 0;
        for(ScoreDoc sd:tds.scoreDocs){
            Document doc;
            try {
                doc = searcher.doc(sd.doc);
                filepath[i] = doc.get("filePath");
                filesize[i] = doc.get("fileSize");
                filemodifiedtime[i] = doc.get("fileModifiedTime");
                filesummary[i] = doc.get("fileSummary");
                System.out.println(sd.doc+"["+sd.score+"]"+"["+filepath[i]+"]");
                i++;
            } catch (CorruptIndexException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //显示搜索结果;
    public void showResult(){
        SearchResult sResult = new SearchResult();
        //构造搜索页第一页
        sResult.tranData(filepath,filesize,filemodifiedtime,filesummary,hits);
        sResult.gui();
    }

    //关闭IndexReader;
    public void closeReader(){
        try {
            reader.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
