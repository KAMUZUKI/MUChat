package com.mu.client.chat_room.view;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.mu.common.Message;
import com.mu.common.MessageType;
import com.mu.client.chat_room.backstage.ClienManage;
import com.mu.client.chat_room.tools.ManageClientPersonCollection;
import com.mu.client.chat_room.tools.MessageSave;
import com.mu.client.chat_room.tools.Tools;
import javax.swing.ImageIcon;

/**
 * 单人聊天的界面
 * @author Administrator
 *
 */
public class ClientFrame extends JFrame implements WindowListener{
	private static JTextArea jta1;	//文本区域
	public JTextArea jta2;
	public JButton jb1,jb2,jb3;	//按扭
	private JScrollPane jsp1,jsp2;	//滚动条
	private ClienManage cm;	//后台处理对象
	private JFileChooser jfc;	//文件选择器
	private String Sender;
	private String Getter;
	private JFrame jf;
	private MessageSave messageSave = new MessageSave();
	public ClientFrame(final String Sender,final String Getter,final ClienManage cm){
		super(Sender+"正在与"+Getter+"聊天中");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Client_Frame.class.getResource("/com/mu/utils/favicon.ico")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.jf = this;
		this.cm = cm;
		this.Sender = Sender;
		this.Getter = Getter;
		Container c = this.getContentPane();
		//设置大小
		this.setSize(750, 500);
		//设置空布局
		getContentPane().setLayout(null);
		
		jta1 = new JTextArea();
		//设置不可编辑
		jsp1 = new JScrollPane(jta1);
		jta1.setEditable(false);
		jsp1.setBounds(10, 10, 711, 299);
		c.add(jsp1);
		loadMessage();
		
		jta2 = new JTextArea();
		jta2.setEditable(true);
		jsp2 = new JScrollPane(jta2);
		//获取光标
		jta2.grabFocus();
		jsp2.setBounds(10, 319, 711, 105);
		c.add(jsp2);
		
		
		jb1 = new JButton("发送");
		jb1.setIcon(new ImageIcon(ClientFrame.class.getResource("/com/mu/view/pic/sendMessage.png")));
		jb1.setBounds(612, 429, 109,30);
		//发送按扭注册事件监听
		jb1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String con = jta2.getText();
				if (!con.equals("")) {
					String Time = (new Date()).toLocaleString();
					Message mess = new Message();
					mess.setContent(con);
					mess.setTime(Time);
					mess.setSender(Sender);
					mess.setGetter(Getter);
					mess.setMessageType(MessageType.Common_Message_ToPerson);
					jta1.append(Sender+"    "+Time+"\r\n"+con+"\r\n");
					//保存消息
					messageSave.saveMessage(Sender+"    "+Time+"\r\n"+con+"\r\n",Getter);
					//发送消息
					cm.SendMessage(mess);
					jta2.setText("");
					//获取光标
					jta2.grabFocus();
				}
			}
		});
		c.add(jb1);
		
		jb2 = new JButton("发送文件");
		jb2.setIcon(new ImageIcon(ClientFrame.class.getResource("/com/mu/view/pic/file.png")));
		jb2.setBounds(10, 429, 130, 30);
		//发送文件按扭注册事件监听
		jb2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				jfc = new JFileChooser();
				jfc.showOpenDialog(jf);
				Message mess = new Message();
				mess.setMessageType(MessageType.Send_FileToPerson);
				mess.setSender(Sender);
				mess.setGetter(Getter);
				String FileName = jfc.getName(jfc.getSelectedFile());
				//设置文件名
				mess.setContent(FileName);
				
				if( jfc.getSelectedFile().toPath().toString()!=null){
					//发送消息类型
					cm.SendMessage(mess);
					//获得路径
					String path = jfc.getSelectedFile().toPath().toString();
					//发送文件
					cm.SendFile(path);
					Message m = new Message();
					m.setMessageType(MessageType.Common_Message_ToPerson);
					m.setSender(Sender);
					m.setGetter(Getter);
					m.setTime(new Date().toLocaleString());
					m.setContent("我给你发送了文件名为："+FileName+" 的文件");
					ShowMessage(m);
					cm.SendMessage(m);
				}
			}
		});
		c.add(jb2);
		
		jb3 = new JButton("清空聊天记录");
		jb3.setIcon(new ImageIcon(ClientFrame.class.getResource("/com/mu/view/pic/chat.png")));
		jb3.setBounds(157, 429, 140,30);
		//清空聊天记录按扭注册事件监听
		jb3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				jta1.setText("");
			}
		});
		c.add(jb3);
		//注册窗口事件监听
		this.addWindowListener(this);
		//设置大小不可改变
		this.setResizable(false);
		//设置在屏幕中间
		Tools.setFrameCenter(this);
		this.setVisible(true);
	}
	
	
	/***
	 * 显示信息在个人聊天界面
	 * @param mess
	 */
	public void ShowMessage(Message mess){
		String str = mess.getSender()+":   "+mess.getTime()+"\r\n"+mess.getContent()+"\r\n";
		jta1.append(str);
		//保存消息
		messageSave.saveMessage(str,Getter);
	}

	//加载聊天记录
	public void loadMessage() {
		List<String> list = messageSave.loadMessage(Getter);
		if (list!=null) {
			for (int i = 0; i < list.size(); i++) {
				jta1.append(list.get(i));
			}
		}else {
			return;
		}
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		String str = Sender+" "+Getter;
		ManageClientPersonCollection.removeClientPerson(str);
	}


	@Override
	public void windowClosed(WindowEvent e) {
		
	}


	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}
