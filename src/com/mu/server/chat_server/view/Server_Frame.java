package com.mu.server.chat_server.view;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.mu.common.Message;
import com.mu.common.MessageType;
import com.mu.server.chat_server.backstage.ServerConClient;
import com.mu.server.chat_server.backstage.ServerManage;
import com.mu.server.chat_server.tools.ServerThreadCollection;
import com.mu.server.chat_server.tools.Tools;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * �����������
 * @author MUZUKI
 *
 */
public class Server_Frame extends JFrame{
	//������Ҫ�����
	private JButton jb1,jb2,jb3;	//��ť
	private static JButton jb4;
	private static JList jl;	//�б�
	private static JTextArea jta1,jta2;	//�ı�����
	private JLabel jla1;	//��ǩ
	private static JLabel jla2;
	private JScrollPane jsp1,jsp2,jsp3;	//������
    private ServerManage server;
    private JFrame jf;
    private static String[] onLine={""};//�����û�������
	public Server_Frame(){
		super("�����ҷ�����");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Server_Frame.class.getResource("/com/mu/utils/favicon.ico")));
		this.jf = this;
		Container c = this.getContentPane();
		//���ô����С
		this.setSize(512,417);
		//���ÿղ���
		getContentPane().setLayout(null);
		jb1 = new JButton("����������");
		jb1.setBounds(100, 10, 100, 30);
		c.add(jb1);
		//������������Ťע���¼�����
		jb1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				server = new ServerManage();
				Thread t = new Thread(server);
				t.start();
				jb1.setEnabled(false);
				jb2.setEnabled(true);
				jb3.setEnabled(true);
				jta2.setEditable(true);
			}
		});
		  
		jb2 = new JButton("�رշ�����");
		jb2.setBounds(260, 10, 100, 30);
		c.add(jb2);
	    jb2.addActionListener(new ActionListener() {
	    	@Override
			public void actionPerformed(ActionEvent e) {
	    		server.CloseServer();
				jb1.setEnabled(true);
				jb2.setEnabled(false);
				jb3.setEnabled(false);
				jta2.setEditable(false);
			}
		});
	    
	    
	    jla1 = new JLabel("����������");
	    jla1.setBounds(370, 30, 80, 20);
	    c.add(jla1);
	    
	    jla2 = new JLabel("0");
	    jla2.setBounds(440, 30, 30, 20);
	    c.add(jla2);
	        
		jta1 = new JTextArea();
		//���ò��ɱ༭
		jta1.setEditable(false);
		jsp1 = new JScrollPane(jta1);
		jsp1.setBounds(10, 50, 350, 200);
		c.add(jsp1);
		
		jl = new JList(onLine);
		jsp2 = new JScrollPane(jl);
		jsp2.setBounds(365, 50, 120, 285);
		c.add(jsp2);
		
		jta2 = new JTextArea();
		jta2.setEditable(false);
		jsp3 = new JScrollPane(jta2);
		jsp3.setBounds(10, 255, 350, 80);
		c.add(jsp3);
		
		jb3 = new JButton("����ϵͳ��Ϣ");
		jb3.setEnabled(false);
		jb3.setBounds(230, 345, 130, 30);
		c.add(jb3);
		
		//����ϵͳ��Ϣ��Ť���¼�����
		jb3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String message = jta2.getText();
				//��ʾϵͳ��Ϣ
				ShowSystemMessage("ϵͳ��Ϣ��"+message+"\r\n");
				server.Send_SystemMessage("ϵͳ��Ϣ��"+message+"\r\n");
				jta2.setText("");
				//��ȡ���
				jta2.grabFocus();
			}
		});
		
		jb4 = new JButton("ǿ������");
		jb4.setEnabled(false);
		jb4.setBounds(365, 345, 120, 30);
		//ע��ǿ�����߰�Ť���¼�����
		jb4.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int i = jl.getAnchorSelectionIndex();
				if(i==-1){
					JOptionPane.showMessageDialog(jf, "��ѡ��һ��");
				}else{
					//ȡ��ѡ�е��û�����ȡ�����߳���
					ServerConClient scc = ServerThreadCollection.getServerContinueConnetClient(onLine[i]);
					scc.CloseThread();
				}
				
			}
		});
		c.add(jb4);
		
		//�������
		JButton manageButton = new JButton("\u7BA1\u7406");
		manageButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				EmpManagement eManagement = new EmpManagement();
				eManagement.open();
			}
		});
		manageButton.setBounds(10, 344, 93, 30);
		getContentPane().add(manageButton);
		
		//���ô����С���ɸı�
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Tools.setFrameCenter(this);
		this.setVisible(true);
	}
	
	
	
	
	
	/**
     * ���������û��ķ���
     * @param strings �����û����ַ���
     */
    @SuppressWarnings({ "rawtypes", "unchecked", "serial" })
	public static void SetOnLline(String string){
    	if(string.equals("")){
	    		final String[] strings = {""};
	    		 //ͬʱ��ʾ��������
	       	 ShowOnlineNumber("0");
	       	jb4.setEnabled(false);
	       	 jl.setModel(new AbstractListModel() {	 
	             public int getSize(){ 
	            	 return strings.length; 
	             } 
	             public Object getElementAt(int i){ 
	            	 return strings[i]; 
	            	 }
	             
	         });
    	}else{
    		jb4.setEnabled(true);
    		final String[] strings = string.split(" ");
    		onLine = strings;
	    	 //ͬʱ��ʾ��������
	    	 ShowOnlineNumber(new Integer(strings.length).toString());
	    	 jl.setModel(new AbstractListModel() {	 
	             public int getSize(){ 
	            	 return strings.length; 
	             } 
	             public Object getElementAt(int i){ 
	            	 return strings[i]; 
	            	 }
	             
	         });
    	 }
    }
    
    /**
     * ��ʾ��Ϣ���ı�����
     * @param mess
     */
    public static void showMessage(Message mess){
    	if(mess.getMessageType().equals(MessageType.Common_Message_ToAll)){
    		jta1.append(mess.getSender()+"��������˵�� "+mess.getContent()+"\r\n");
    	}else if(mess.getMessageType().equals(MessageType.Common_Message_ToPerson)){
    		jta1.append(mess.getSender()+"��"+mess.getGetter()+"˵��"+mess.getContent()+"\r\n");
    	}else if(mess.getMessageType().equals(MessageType.CommonMessage)){
    		jta1.append(mess.getContent()+"\r\n");
    	}
    	
    }

    /**
     * ��ʾ��������
     */
    public static void ShowOnlineNumber(String num){
    	jla2.setText(num);
    }
    
    /**
     * ��ʾϵͳ��Ϣ
     * @param str
     */
    public static void ShowSystemMessage(String str){
    	jta1.append(str);
    }
}
