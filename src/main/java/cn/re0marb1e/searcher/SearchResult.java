package cn.re0marb1e.searcher;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cn.re0marb1e.gui.LinkLabel;

//搜索结果弹窗
public class SearchResult extends JFrame implements ActionListener{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    JPanel[] jp = new JPanel[10];
    JPanel jpp,jpl,jpr;
    LinkLabel[] ll = new LinkLabel[10];   //路径
    LinkLabel[] llD = new LinkLabel[10];
    JLabel[] jl1 = new JLabel[10];   //大小
    JLabel[] jl2 = new JLabel[10];   //修改日期
    JLabel[] jl3 = new JLabel[10];   //摘要
    JLabel jresult;
    JButton jb1,jb2;
    int currentpage;
    int realcurrentpage;
    int hits;
    int page;
    int realpage;
    int left;
    String filepath[];
    String filesize[];
    String filemodifiedtime[];
    String filesummary[];
    String fileDirectory[];
    public SearchResult(){
        super("搜索结果");
        currentpage = 0;
        realcurrentpage = 1;
        //设置
        this.setLayout(new GridLayout(21,1));
        this.setLocation(150,30);
        this.setSize(1024,740);
        this.setIconImage(new ImageIcon("images/01.PNG").getImage());
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
        for(int i=0;i<10;i++){
            jp[i] = new JPanel();
            ll[i] = new LinkLabel();
            ll[i].setFont(new Font("宋体",Font.BOLD,20));

            jl1[i] = new JLabel();
            jl2[i] = new JLabel();
            llD[i] = new LinkLabel();
            jl3[i] = new JLabel();

            jp[i].add(ll[i]);
            jp[i].add(jl1[i]);
            jp[i].add(jl2[i]);
            jp[i].add(llD[i]);
            this.add(jp[i]);
            this.add(jl3[i]);
        }
        //监听
        jb1 = new JButton("上一页");
        jb2 = new JButton("下一页");
        jb1.addActionListener(this);
        jb2.addActionListener(this);
        jresult = new JLabel();
        jpp = new JPanel();
        jpl = new JPanel();
        jpr = new JPanel();
        jpl.add(jb1);
        jpr.add(jb2);
        jresult.setHorizontalAlignment(JLabel.CENTER);
        jpp.setLayout(new GridLayout(1,3));
        this.add(jpp);
        jpp.add(jpl);
        jpp.add(jresult);
        jpp.add(jpr);
    }
    public void gui(){
        //jresult标签;
        jresult.setText("共"+hits+"条搜索结果"+",第"+realcurrentpage+"页");
        //如果当前页是最后一页;
        if(currentpage == page){
            //如果最后一页有10条搜索结果;
            if(left==0)
            {
                this.showResult(0);
                this.showResult(1);
                this.showResult(2);
                this.showResult(3);
                this.showResult(4);
                this.showResult(5);
                this.showResult(6);
                this.showResult(7);
                this.showResult(8);
                this.showResult(9);
            }
            //如果最后一页有1――9条搜索结果;
            else
            {
                for(int i=10;i>left;i--){
                    this.showNothing(i-1);
                }
                for(int i=1;i<=left;i++){
                    this.showResult(i-1);
                }
            }
        }
        //如果当前页不是最后一页;
        else
        {
            this.showResult(0);
            this.showResult(1);
            this.showResult(2);
            this.showResult(3);
            this.showResult(4);
            this.showResult(5);
            this.showResult(6);
            this.showResult(7);
            this.showResult(8);
            this.showResult(9);
        }
        //如果当前页为第一页，隐藏jb1;
        if(currentpage==0){
            jb1.setVisible(false);
        }
        //如果当前页不是第一页，显示jb1;
        else{
            jb1.setVisible(true);
        }
        //如果当前页是最后一页，隐藏jb2;
        if(currentpage==page){
            jb2.setVisible(false);
        }
        //如果当前页不是最后一页，显示jb2;
        else{
            jb2.setVisible(true);
        }
    }
    //显示当前页第i+1条搜索结果;
    public void showResult(int i){
        ll[i].settext(filepath[currentpage*10+i]);
        ll[i].setUrl(filepath[currentpage*10+i]);
        ll[i].show();
        llD[i].settext("打开所在目录");
        llD[i].setUrl(fileDirectory[currentpage*10+i]);
        llD[i].show();
        jl1[i].setText("大小:"+filesize[currentpage*10+i]+"字节");
        jl2[i].setText("修改日期:"+filemodifiedtime[currentpage*10+i]);
        jl3[i].setText("摘要:"+filesummary[currentpage*10+i]);
    }
    //置空当前页i+1条搜索结果;
    public void showNothing(int i){
        ll[i].setText("");
        ll[i].setUrl("");
        llD[i].setText("");
        llD[i].settext("");
        jl1[i].setText("");
        jl2[i].setText("");
        jl3[i].setText("");
    }
    //
    public void tranData(String[] filepath,String[] filesize,String[] filemodifiedtime,
                         String[] filesummary,int hits){
        this.hits = hits;
        this.page = hits/10;
        this.left = hits%10;
        this.realpage = page+1;
        this.filepath=filepath;
        this.filesize=filesize;
        this.filemodifiedtime=filemodifiedtime;
        this.filesummary=filesummary;
//		int j = hits-1;
//		System.out.println(j);
        this.fileDirectory = new String[hits];
        for(int i=0;i<=hits-1;i++)
        {
            this.fileDirectory[i]=filepath[i].substring(0,filepath[i].lastIndexOf("\\"));
        }
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==jb1){
            currentpage = currentpage-1;
            realcurrentpage = realcurrentpage-1;
            this.gui();
        }
        if(e.getSource()==jb2){
            currentpage = currentpage+1;
            realcurrentpage = realcurrentpage+1;
            this.gui();
        }
    }
}