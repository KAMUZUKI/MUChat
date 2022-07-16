package com.mu.server.chat_server.backstage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Date;

import com.mu.common.Message;
import com.mu.common.MessageType;
import com.mu.common.User;
import com.mu.server.chat_server.tools.ServerThreadCollection;
import com.mu.server.chat_server.view.Server_Frame;

/**
 * ��������̨�Ĵ�����
 * @author Administrator
 *
 */
public class ServerManage implements Runnable{
	private  ServerSocket ss ;
	private ObjectInputStream ois;
	private ObjectOutputStream os;
	private Server_Connect_Database server;
	public  ServerManage() {
		Message m = new Message();
		m.setContent("��������9999�˿ڼ���..\r\n");
		m.setMessageType(MessageType.CommonMessage);
		Server_Frame.showMessage(m);
		try {
			ss = new ServerSocket(9999);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			while(true){
				try {
					//���ܿͻ��˷��͹�������Ϣ
					Socket s = null;
					try{
						s = ss.accept();
					}catch(SocketException e){
						if(s!=null){
							s.close();
						}
						break;
					}
					ois = new ObjectInputStream(s.getInputStream());
					User user = (User)ois.readObject();

					//�����ȡ�������û���½����Ϣ
					if(user.getType().equals(MessageType.UserLogin)){
						server = new Server_Connect_Database();
						Message mess = new Message();
						//�ж��Ƿ��ظ���½
						if(server.Check_IsLogin(user)){
							//��½����
							mess.setMessageType(MessageType.Login);
							os = new ObjectOutputStream(s.getOutputStream());
							os.writeObject(mess);
							s.close();
						}else{
							//û��½��
							mess.setMessageType(MessageType.NoLogin);
							os = new ObjectOutputStream(s.getOutputStream());
							os.writeObject(mess);
							//�����½�ɹ�
							if(server.CheckLogin(user) && server.Update_IsLogin(user, 1)){//���ĳɵ�½��
								mess.setMessageType(MessageType.Login_Success);
								os = new ObjectOutputStream(s.getOutputStream());
								os.writeObject(mess);		
								//��½�ɹ��󣬵�����һ���߳�Ϊ�ͻ��˷��񣬲������̷߳��뼯��,�Ա�ȡ������
								ServerConClient scc = new ServerConClient(s,user.getName());
								//����뼯��
								ServerThreadCollection.addServerConnectClientThreadCollection(user.getName(), scc);
								//���������������û��б�
								scc.ServerUpdataOnline();
								//�����߳���
								Thread t = new Thread(scc);
								t.start();
								//���������û����������б�
								scc.UpdataOnline();
							}else{
								mess.setMessageType(MessageType.Login_Fail);
								os = new ObjectOutputStream(s.getOutputStream());
								os.writeObject(mess);
								s.close();
							}
						}
					}
					
				}catch(SocketException e){
					e.printStackTrace();
					break;
				}catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();		
		}
	}
	
	
	
	
	/**
	 * ����ϵͳ��Ϣ�ķ���
	 * @param message ϵͳ��Ϣ
	 */
	public static void Send_SystemMessage(String message){
		Message mess = new Message();
		mess.setContent(message);
		mess.setMessageType(MessageType.System_Messages);
		//��������û�
		String string = ServerThreadCollection.GetOnline();
		String[] strings = string.split(" ");
		String Name = null;
		for(int i=0;i<strings.length;i++){
			Name = strings[i];
			//���ý����û�
			mess.setGetter(Name);
			String time = (new Date().toLocaleString());
			mess.setTime(time);
			//�����������������ͻ���ͨ�ŵ��߳�
			ServerConClient sccc = ServerThreadCollection.getServerContinueConnetClient(Name);
			try {
				ObjectOutputStream os = new ObjectOutputStream(sccc.getS().getOutputStream());
				os.writeObject(mess);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * �رշ������ķ���
	 */
	public void CloseServer(){
		try {
			//��������û�
			String string = ServerThreadCollection.GetOnline();
			String[] strings = string.split(" ");
			for(int i=0;i<strings.length;i++){
				//�����������������ͻ���ͨ�ŵ��߳�
				ServerConClient sccc = ServerThreadCollection.getServerContinueConnetClient(strings[i]);
				if(sccc!=null){
					sccc.CloseThread();
				}
			}
			this.ss.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
