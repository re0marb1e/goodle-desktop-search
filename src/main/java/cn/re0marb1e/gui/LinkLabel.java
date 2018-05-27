package cn.re0marb1e.gui;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

//超链接标签
public class LinkLabel extends JLabel{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    //text为显示内容,URL为超链接;
    private String text,url;
    private boolean isSupported;
    //LinkLabel构造函数;
    public LinkLabel(String text,String url){
        this.text = text;
        this.url = this.exchange(url);
        this.show();
    }
    //LinkLabel构造函数;
    public LinkLabel(){
    }
    //设置超链接,显示标签;
    public void show(){
        try {
            isSupported = Desktop.isDesktopSupported()&& Desktop.getDesktop().isSupported(Desktop.Action.BROWSE);
        } catch (Exception e) {
            isSupported = false;
        }
        setText(false);
        addMouseListener(
                new MouseAdapter() {
                    public void mouseEntered(MouseEvent e){
                        setText(isSupported);
                        if(isSupported)
                            setCursor(new Cursor(Cursor.HAND_CURSOR));
                    }
                    public void mouseExited(MouseEvent e){
                        setText(false);
                    }
                    public void mouseClicked(MouseEvent e){
                        try {
                            Desktop.getDesktop().browse(new java.net.URI(url));
                        } catch (Exception ex) {}
                    }
                }
        );
    }
    //设置超链接;
    public void setUrl(String url){
        this.url = this.exchange(url);
    }
    //设置显示内容;
    public void settext(String text){
        this.text = text;
    }
    private void setText(boolean b) {
        if (!b)
            setText("<html><font color=blue><u>" + text);
        else
            setText("<html><font color=red><u>" + text);
    }
    //把“\”转化为“/”;
    private String exchange(String url){
        return url.replaceAll("\\\\","/");
    }
}
