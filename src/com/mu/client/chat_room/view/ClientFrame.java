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
 * ��������Ľ���
 * @author Administrator
 *
 */
public class ClientFrame extends JFrame implements WindowListener{
	private static JTextArea jta1;	//�ı�����
	public JTextArea jta2;
	public JButton jb1,jb2,jb3;	//��Ť
	private JScrollPane jsp1,jsp2;	//������
	private ClienManage cm;	//��̨�������
	private JFileChooser jfc;	//�ļ�ѡ����
	private String Sender;
	private String Getter;
	private JFrame jf;
	private MessageSave messageSave = new MessageSave();
	public ClientFrame(final String Sender,final String Getter,final ClienManage cm){
		super(Sender+"������"+Getter+"������");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Client_Frame.class.getResource("/com/mu/utils/favicon.ico")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.jf = this;
		this.cm = cm;
		this.Sender = Sender;
		this.Getter = Getter;
		Container c = this.getContentPane();
		//���ô�С
		this.setSize(750, 500);
		//���ÿղ���
		getContentPane().setLayout(null);
		
		jta1 = new JTextArea();
		//���ò��ɱ༭
		jsp1 = new JScrollPane(jta1);
		jta1.setEditable(false);
		jsp1.setBounds(10, 10, 711, 299);
		c.add(jsp1);
		loadMessage();
		
		jta2 = new JTextArea();
		jta2.setEditable(true);
		jsp2 = new JScrollPane(jta2);
		//��ȡ���
		jta2.grabFocus();
		jsp2.setBounds(10, 319, 711, 105);
		c.add(jsp2);
		
		
		jb1 = new JButton("����");
		jb1.setIcon(new ImageIcon(ClientFrame.class.getResource("/com/mu/view/pic/sendMessage.png")));
		jb1.setBounds(612, 429, 109,30);
		//���Ͱ�Ťע���¼�����
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
					//������Ϣ
					messageSave.saveMessage(Sender+"    "+Time+"\r\n"+con+"\r\n",Getter);
					//������Ϣ
					cm.SendMessage(mess);
					jta2.setText("");
					//��ȡ���
					jta2.grabFocus();
				}
			}
		});
		c.add(jb1);
		
		jb2 = new JButton("�����ļ�");
		jb2.setIcon(new ImageIcon(ClientFrame.class.getResource("/com/mu/view/pic/file.png")));
		jb2.setBounds(10, 429, 130, 30);
		//�����ļ���Ťע���¼�����
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
				//�����ļ���
				mess.setContent(FileName);
				
				if( jfc.getSelectedFile().toPath().toString()!=null){
					//������Ϣ����
					cm.SendMessage(mess);
					//���·��
					String path = jfc.getSelectedFile().toPath().toString();
					//�����ļ�
					cm.SendFile(path);
					Message m = new Message();
					m.setMessageType(MessageType.Common_Message_ToPerson);
					m.setSender(Sender);
					m.setGetter(Getter);
					m.setTime(new Date().toLocaleString());
					m.setContent("�Ҹ��㷢�����ļ���Ϊ��"+FileName+" ���ļ�");
					ShowMessage(m);
					cm.SendMessage(m);
				}
			}
		});
		c.add(jb2);
		
		jb3 = new JButton("��������¼");
		jb3.setIcon(new ImageIcon(ClientFrame.class.getResource("/com/mu/view/pic/chat.png")));
		jb3.setBounds(157, 429, 140,30);
		//��������¼��Ťע���¼�����
		jb3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				jta1.setText("");
			}
		});
		c.add(jb3);
		//ע�ᴰ���¼�����
		this.addWindowListener(this);
		//���ô�С���ɸı�
		this.setResizable(false);
		//��������Ļ�м�
		Tools.setFrameCenter(this);
		this.setVisible(true);
	}
	
	
	/***
	 * ��ʾ��Ϣ�ڸ����������
	 * @param mess
	 */
	public void ShowMessage(Message mess){
		String str = mess.getSender()+":   "+mess.getTime()+"\r\n"+mess.getContent()+"\r\n";
		jta1.append(str);
		//������Ϣ
		messageSave.saveMessage(str,Getter);
	}

	//���������¼
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
