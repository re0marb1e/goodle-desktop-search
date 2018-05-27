package cn.re0marb1e.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import cn.re0marb1e.index.IndexBuilder;
import cn.re0marb1e.searcher.Search;

//搜索和索引主界面
public class IndexAndSearch extends JFrame implements ActionListener{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    JTabbedPane jtb;
    JPanel jp1,jp2,jp2_1,jp2_2,jp2_1_1,jp2_1_2,jp2_1_3,jp2_1_4,jp1_1,jp1_2,jp1_3,jp1_4;
    JButton jb1,jb2,jb3,jb4;
    JLabel jl1,jl2,jl3,jl4;
    //FilePath映射textF1内容，DirectoryPath映射textF2内容;
    private String FilePath;
    private String DirectoryPath;
    JTextField textF1,textF2;
    //和实际索引位置，存放位置一一映射;
    File f1,f2;
    ReadWriterTXT rwt1,rwt2;
    LinkLabel jll1,jll2;
    public static JCheckBox jcb1,jcb2,jcb3,jcb4,jcb5,jcb6,jcb7,jcb8,jcb9;
    //索引结果显示;
    public static JTextArea textA1;
    JScrollPane jsp;
    //搜索相关;
    JTextField textF3;      //搜索框
    JComboBox jcomb;
    ButtonGroup bg;
    public static JRadioButton jrb1,jrb2,jrb3,jrb4,jrb5,jrb6,jrb7,jrb8,jrb9,jrb10;
    //初始化;
    IndexAndSearch(){
        super("Goodle!!!DesktopSearch");
        jtb = new JTabbedPane();
        jp1= new JPanel();
        jp2= new JPanel();
        jp2_1= new JPanel();
        jp2_2= new JPanel();
        jp2_1_1= new JPanel();
        jp2_1_2= new JPanel();
        jp2_1_3= new JPanel();
        jp2_1_4= new JPanel();
        jp1_1= new JPanel();
        jp1_2= new JPanel();
        jp1_3= new JPanel();
        jp1_4= new JPanel();
        jb1 = new JButton("选择要建立索引的目录");
        jb2 = new JButton("选择索引存放的目录");
        jb3 = new JButton("重新建索引");
        jb4 = new JButton("搜索");

        jl1 = new JLabel("索引的项:");
        jl2 = new JLabel("Goodle!!!");
        jl2.setFont(new Font("宋体",Font.BOLD,40));

        String[] item = {"内容","文件名"};
        jcomb = new JComboBox(item);

        //设置textF1,textF2,FilePath,DirectoryPath初始化,初始化数据从FilePath.txt,DirectoryPath.txt读出;
        f1 = new File("src/main/lib/FilePath.txt");
        f2 = new File("src/main/lib/DirectoryPath.txt");
        rwt1 = new ReadWriterTXT(f1);
        rwt2 = new ReadWriterTXT(f2);
        textF1 = new JTextField(35);
        textF2 = new JTextField(35);
        textF1.setEditable(false);
        textF2.setEditable(false);
        textF1.setText(rwt1.reader());
        textF2.setText(rwt2.reader());
        FilePath = rwt1.reader();
        DirectoryPath = rwt2.reader();
        //设置超链接jll1，jll2初始化;
        jl3 = new JLabel("FilePath");
        jl4 = new JLabel("DirectoryPath");
        jll1 = new LinkLabel(FilePath,FilePath);
        jll2 = new LinkLabel(DirectoryPath,DirectoryPath);
        //搜索输入框;
        textF3 = new JTextField(40);
        //索引结果显示文本框;
        textA1 = new JTextArea(5,50);
        textA1.setEditable(false);
        jsp = new JScrollPane(textA1,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        //索引复选框;
        jcb1 = new JCheckBox("TXT",true);
        jcb2 = new JCheckBox("PDF",true);
        jcb3 = new JCheckBox("Word",true);
        jcb4 = new JCheckBox("Excel",true);
        jcb5 = new JCheckBox("PowerPoint",true);
        jcb6 = new JCheckBox("视频",true);
        jcb7 = new JCheckBox("音频",true);
        jcb8 = new JCheckBox("图片",true);
        jcb9 = new JCheckBox("其他",true);
        //单选框组;
        jrb1 = new JRadioButton("全部",true);
        jrb2 = new JRadioButton("TXT");
        jrb3 = new JRadioButton("PDF");
        jrb4 = new JRadioButton("Word");
        jrb5 = new JRadioButton("Excel");
        jrb6 = new JRadioButton("PowerPoint");
        jrb7 = new JRadioButton("视频");
        jrb8 = new JRadioButton("音频");
        jrb9 = new JRadioButton("图片");
        jrb10 = new JRadioButton("其他");
        bg = new ButtonGroup();
        bg.add(jrb1);
        bg.add(jrb2);
        bg.add(jrb3);
        bg.add(jrb4);
        bg.add(jrb5);
        bg.add(jrb6);
        bg.add(jrb7);
        bg.add(jrb8);
        bg.add(jrb9);
        bg.add(jrb10);
        //索引项;
        jp2_1.setLayout(new GridLayout(4,1));
        jp2_1.add(jp2_1_1);
        jp2_1.add(jp2_1_2);
        jp2_1.add(jp2_1_3);
        jp2_1.add(jp2_1_4);

        jp2_1_1.add(jb1);
        jp2_1_1.add(textF1);

        jp2_1_2.add(jb2);
        jp2_1_2.add(textF2);

        jp2_1_3.add(jl1);
        jp2_1_3.add(jcb1);
        jp2_1_3.add(jcb2);
        jp2_1_3.add(jcb3);
        jp2_1_3.add(jcb4);
        jp2_1_3.add(jcb5);
        jp2_1_3.add(jcb6);
        jp2_1_3.add(jcb7);
        jp2_1_3.add(jcb8);
        jp2_1_3.add(jcb9);

        jp2_1_4.add(jb3);

        jp2_2.add(jsp);
        jp2.setLayout(new BorderLayout());
        jp2.add(jp2_1,BorderLayout.CENTER);
        jp2.add(jp2_2,BorderLayout.SOUTH);
        //搜索项;
        jp1.setLayout(new GridLayout(5,1));
        jp1.add(jp1_4);
        jp1.add(jp1_1);
        jp1.add(jp1_2);
        jp1.add(jp1_3);

        jp1_4.add(jl3);
        jp1_4.add(jll1);
        jp1_4.add(jl4);
        jp1_4.add(jll2);

        jp1_1.add(jl2);

        jp1_2.add(jcomb);
        jp1_2.add(textF3);
        jp1_2.add(jb4);

        jp1_3.add(jrb1);
        jp1_3.add(jrb2);
        jp1_3.add(jrb3);
        jp1_3.add(jrb4);
        jp1_3.add(jrb5);
        jp1_3.add(jrb6);
        jp1_3.add(jrb7);
        jp1_3.add(jrb8);
        jp1_3.add(jrb9);
        jp1_3.add(jrb10);

        jtb.add(jp1,"搜索项");
        jtb.add(jp2,"索引项");
        this.add(jtb);
        //设置;
        this.setSize(640,320);
        this.setLocationRelativeTo(getOwner());
        this.setIconImage(new ImageIcon("src/main/img/logo.PNG").getImage());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
        //监听;
        jb1.addActionListener(this);
        jb2.addActionListener(this);
        jb3.addActionListener(this);
        jb4.addActionListener(this);
        textF3.addActionListener(this);
    }
    public void actionPerformed(ActionEvent e) {
        //jb1的事件;
        if(e.getSource()==jb1){
            JFileChooser jfc = new JFileChooser("E:\\");
            jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int returnVal = jfc.showOpenDialog(jfc);
            if(returnVal == JFileChooser.APPROVE_OPTION){
                FilePath = jfc.getSelectedFile().getAbsolutePath();
                textF1.setText(FilePath);
            }
        }
        //jb2的事件;
        if(e.getSource()==jb2){
            JFileChooser jfc = new JFileChooser("E:\\");
            jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int returnVal = jfc.showOpenDialog(jfc);
            if(returnVal == JFileChooser.APPROVE_OPTION){
                DirectoryPath = jfc.getSelectedFile().getAbsolutePath();
                textF2.setText(DirectoryPath);
            }
        }
        //重建索引;
        if(e.getSource()==jb3){
            textA1.setText("");
            if(DirectoryPath==""||FilePath==""){
                JOptionPane.showMessageDialog(null,"未设置位置", "警告", JOptionPane.WARNING_MESSAGE);
            }
            else
            {
                //初始化IndexWriter
                IndexBuilder ib = new IndexBuilder(DirectoryPath);
                //建立索引
                ib.MainIndexBuilder(FilePath);
                //关闭IndexWriter
                ib.closeWriter();
                //label显示
                jll1.settext(FilePath);
                jll1.setUrl(FilePath);
                jll1.show();
                jll2.settext(DirectoryPath);
                jll2.setUrl(DirectoryPath);
                jll2.show();
                //索引建立完成
                textA1.setText(textA1.getText()+"索引建立完毕"+"\n");
                //获取索引docment数量并显示
                textA1.setText(textA1.getText()+"共"+ib.getdocnum()+"条索引");
                //配置文件
                rwt1.replace(rwt1.reader(),FilePath);
                rwt2.replace(rwt2.reader(),DirectoryPath);
            }
        }
        //搜索
        if(e.getSource()==jb4 ||e.getSource()==textF3){
            if(rwt2.reader()==""||rwt2.reader()==""){
                JOptionPane.showMessageDialog(null,"没有索引", "警告", JOptionPane.WARNING_MESSAGE);
            }
            else
            {
                //初始化
                Search search = new Search(rwt2.reader());
                //生成查询结果，并返还命中数
                int hits = search.getHitsAndTopDocs(search.getField(jcomb.getSelectedItem()),textF3.getText());
                //未命中结果;
                if(hits == 0)
                {
                    JOptionPane.showMessageDialog(null,"搜索结果为0条", "搜索结果", JOptionPane.INFORMATION_MESSAGE);
                    search.closeReader();
                }
                //命中结果;
                else
                {
                    search.getResult();
                    search.showResult();
                    //关闭IndexReader;
                    search.closeReader();
                }
            }
        }
    }
}