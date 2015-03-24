package com.sabulaji.and2crobh;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class MainActivity extends Activity implements OnClickListener {

	public static final int UPDATE_BUTTONS = 1;
	private ImageButton button_u, button_d, button_l, button_r, button_cl,button_lo;
	private Button button_go;
	private EditText setHost;
	int PORT = 8888;
	String HOST = "192.168.137.3";
	String command = "";
	Socket socket;
	PrintWriter writer;
	int flag = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initButtons();
		button_go.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				
				HOST = setHost.getText().toString();
				Log.d("MainActivity", "set host:" + HOST);
				flag = 1;
				new myThread().start();
    			Log.d("MainActivity", "go_click_myThread().start()");  
    			initedButtons();
    			Log.d("MainActivity", "onclick-initedbuttons");
			}
		});
		
		//new myThread().start();
		
		//initedButtons();
		Log.d("MainAcitvity", "initedButtons");

			
		
	}

	/*
	 * public class Commander { public String HOST ="192.168.2.101"; //the ip of
	 * raspberry pi public int PORT =8888; public void send(Command forward)
	 * throws Exception { Socket socket = new Socket(HOST, PORT); PrintWriter
	 * writer = new PrintWriter(socket.getOutputStream());
	 * writer.println(forward.toString()); writer.flush(); socket.close(); } }
	 */

	private void initButtons() {
		button_u = (ImageButton) findViewById(R.id.imageButton_u);
		button_d = (ImageButton) findViewById(R.id.imageButton_d);
		button_l = (ImageButton) findViewById(R.id.imageButton_l);
		button_r = (ImageButton) findViewById(R.id.imageButton_r);
		button_cl = (ImageButton) findViewById(R.id.imageButton_cl);
		button_lo = (ImageButton) findViewById(R.id.imageButton_lo);
		button_go = (Button) findViewById(R.id.button_go);

		button_u.setOnClickListener(this);
		button_d.setOnClickListener(this);
		button_l.setOnClickListener(this);
		button_r.setOnClickListener(this);
		button_cl.setOnClickListener(this);
		button_lo.setOnClickListener(this);
		
		button_u.setEnabled(false);
		button_d.setEnabled(false);
		button_l.setEnabled(false);
		button_r.setEnabled(false);
		button_cl.setEnabled(false);
		button_lo.setEnabled(false);
		
		setHost = (EditText)findViewById(R.id.editText1);
		
	}

/*	@Override
    public void onClick(View v){
    	switch (v.getId()){
    		case R.id.button_go:
    			
    			new myThread().start();
    			
  			
    			break;    	
    		case R.id.imageButton_u:
    			command("up");
    			break;
    		case R.id.imageButton_d:
    			command("down");
    			break;
    		case R.id.imageButton_l:
    			command("left");
    			break;
    		case R.id.imageButton_r:
    			command("right");
    			break;
    		case R.id.imageButton_cl:
    			command("clip");
    			break;
    		case R.id.imageButton_lo:
    			command("loosen");
    			break;
    	}
    	
    }*/

	private void initSocket() {
		try {
			Log.d("MainActivity", "initSocketing..");
			socket = new Socket(HOST, PORT);
			writer = new PrintWriter(socket.getOutputStream());
			
			/*Message message = new Message();
			message.what = UPDATE_BUTTONS;
			handler.sendMessage(message);*/
			//flag = 1;
			Log.d("MainActivity", "initSocketed");
		} catch (UnknownHostException e) {
			//handlerException(e, "unknown host exception: " + e.toString());
			Log.d("MainActivity", "initSocketed-unknowhost");
		} catch (IOException e) {
			//handlerException(e, "io exception: " + e.toString());
			Log.d("MainActivity", "initSocketed-io");
		}
	}

	private void initedButtons() {
		button_go.setEnabled(false);
		setHost.setEnabled(false);
		button_u.setEnabled(true);
		button_d.setEnabled(true);
		button_l.setEnabled(true);
		button_r.setEnabled(true);
		button_cl.setEnabled(true);
		button_lo.setEnabled(true);		
	}

	private void command(String command) {
		this.command = command;
		writer.print(command);
		writer.flush();
		Log.d("MainActivity", command);
		try {
			socket.close();
			Log.d("MainActivity", "socket.close");
		} catch (IOException e) {
			//handlerException(e, "close exception: " + e.toString());
			Log.d("MainActivity", "command-socket.close()-io");
		}
	}
	
	class myThread extends Thread{
		@Override
		public void run(){
			initSocket();			
		}
	}

	@Override
	public void onClick(View v) {
		
		new myThread().start();
		
		switch (v.getId()){
		case R.id.imageButton_u:
			Log.d("MainActivity", "onClick_up");
			command("up");
			break;
		case R.id.imageButton_d:
			command("down");
			break;
		case R.id.imageButton_l:
			command("left");
			break;
		case R.id.imageButton_r:
			command("right");
			break;
		case R.id.imageButton_cl:
			command("clip");
			break;
		case R.id.imageButton_lo:
			command("loosen");
			break;
		}
	}

/*	private Handler handler = new Handler(){
		public void handlerMessage(Message msg){
			Log.d("MainActivity", "handler");
			switch (msg.what){
			case UPDATE_BUTTONS:
				initedButtons();
				Log.d("MainActivity", "handler_initedButtons");
				break;
			
			}				
		}
	};*/
}