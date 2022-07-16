package com.mu.client.chat_room.backstage;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import com.mu.common.Message;
import com.mu.common.MessageType;
import com.mu.common.User;
import com.mu.config.Config;
import com.mu.utils.Constents;


/**
 * �ͻ��˺�̨�Ĵ�����
 * @author Administrator
 *
 */
public class ClienManage {
	private ObjectOutputStream os;
	private ObjectInputStream ois;
	private Socket s;
	private boolean isConnect = true;
	private ServerSocket ss;
	
	private BufferedOutputStream bos;
	private DataInputStream dis;
	public ClienManage(){
		try {
			 s = new Socket("127.0.0.1", 9999);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(ConnectException e){
			isConnect = false;
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * �����û���½�ķ���
	 * @param user �û�����
	 * @return �����û��Ƿ��½�ɹ�
	 */
	public boolean Login(){
		boolean b = false;
		try {
			if(s!=null){
				ois = new ObjectInputStream(s.getInputStream());
				Message mess = (Message)ois.readObject();
				if(mess.getMessageType().equals(MessageType.Login_Success)){
					b = true;
				}
			}
//			s.shutdownOutput();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			b = false;
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			b = false;
			e.printStackTrace();
		}
		return b;
	}
	
	/**
	 * ����Ƿ��½���˵ķ���
	 * @return �Ƿ��½��
	 */
	public boolean Check_isLogin(User user){
		boolean b = false;
		try {
			if(s!=null){
				os = new ObjectOutputStream(s.getOutputStream());
				user.setType(MessageType.UserLogin);
				os.writeObject(user);
				ois = new ObjectInputStream(s.getInputStream());
				Message mess = (Message)ois.readObject();
				if(mess.getMessageType().equals(MessageType.Login)){
					b = true;
				}else if(mess.getMessageType().equals(MessageType.NoLogin)){
					b = false;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			b = true;
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			b = true;
			e.printStackTrace();
		}
		return b;
	}
	
	/**
	 * ������Ϣ�ķ���
	 * @return ���ؽ��յ���Ϣ
	 */
	public Message ReciveMessage(){
		Message mess = null;
		if(s!=null){
			try {
				ois = new ObjectInputStream(s.getInputStream());
				mess = (Message)ois.readObject();	
			}catch(SocketException e){
				isConnect = false;
				e.printStackTrace();
			}catch(EOFException e){
				isConnect = false;
				e.printStackTrace();
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return mess;
	}
	
	
	/**
	 * ������Ϣ�ķ���
	 * @param mess ��Ϣ
	 */
	public void SendMessage(Message mess){
		if(s!=null){
			try {
				os = new ObjectOutputStream(s.getOutputStream());
				os.writeObject(mess);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * �ر���Դ�ķ���
	 */
	public void CloseResource(){
			try {
				if(s!=null){
					s.close();
				}if(os!=null){
					os.close();
				}
				if(ois!=null){
					ois.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
	/**
	 * �����ļ��ķ���
	 * @param path ·�� 
	 */
	public void SendFile(String path){
		Socket s1 = null;
		try {
			//�´���һ��TCPЭ��
			s1 = new Socket("127.0.0.1", 8888);
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			bos = new BufferedOutputStream(s1.getOutputStream());
			
			dis = new DataInputStream(new FileInputStream(path));

			byte[] bys = new byte[1024];
			int len = 0;
			while ((len = dis.read(bys)) != -1) {
				bos.write(bys, 0, len);
				bos.flush();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(s1!=null){
				try {
					s1.shutdownOutput();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	
	
	/**
	 * �ж��Ƿ����ӵ�������
	 * @return �Ƿ����ӵ�������
	 */
	public boolean IsConnect(){
		return isConnect;
	}
	
	/**
	 * �����ļ��ķ���
	 */
	public void ReceiveFile(Message mess){
		//�Ƿ�����ļ�
		int res = JOptionPane.showConfirmDialog(null, "ȷ�Ͻ��� " + mess.getSender() + " �ļ���");
		if (res!=0) return;
		BufferedReader br = null;
		BufferedWriter bw = null;
		Socket s1 = null;
		try {
			ss = new ServerSocket(7777);
			s1 = ss.accept();
			dis = new DataInputStream(s1.getInputStream());
			File path =new File(Config.DOWNLOAD_PATH + mess.getContent());
			bos = new BufferedOutputStream(new FileOutputStream(path));
			byte[] bys = new byte[1024];
			int len = 0;
			while ((len = dis.read(bys)) != -1) {
				bos.write(bys, 0, len);
				bos.flush();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(ss!=null){
					ss.close();
				}
				if(dis!=null){
					dis.close();
				}if(s1!=null){
					s1.close();
				}if(bos!=null){
					bos.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
