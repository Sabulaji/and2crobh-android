package com.sabulaji.and2crobh;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.ImageButton;

public class MainActivity extends Activity implements /*OnClickListener,*/OnTouchListener {

	//public static final int UPDATE_BUTTONS = 1;
	private ImageButton button_w, button_s, button_a, button_d, button_cl,button_lo;
	private ImageButton button_go;
	private EditText setHost;
	int PORT = 8888;
	String HOST = "192.168.137.2";
	String command = "";
	String cmd = "";
	Socket socket;
	PrintWriter writer;
	//int flag = 0;
	Boolean flag = false;
	

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
				//flag = 1;
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
		button_w = (ImageButton) findViewById(R.id.imageButton_w);
		button_s = (ImageButton) findViewById(R.id.imageButton_s);
		button_a = (ImageButton) findViewById(R.id.imageButton_a);
		button_d = (ImageButton) findViewById(R.id.imageButton_d);
		button_cl = (ImageButton) findViewById(R.id.imageButton_cl);
		button_lo = (ImageButton) findViewById(R.id.imageButton_lo);
		button_go = (ImageButton) findViewById(R.id.imageButton_go);

		/*button_u.setOnClickListener(this);
		button_d.setOnClickListener(this);
		button_l.setOnClickListener(this);
		button_r.setOnClickListener(this);
		button_cl.setOnClickListener(this);
		button_lo.setOnClickListener(this);*/
		
		button_w.setOnTouchListener(this);
		button_s.setOnTouchListener(this);
		button_a.setOnTouchListener(this);
		button_d.setOnTouchListener(this);
		button_cl.setOnTouchListener(this);
		button_lo.setOnTouchListener(this);
		
		button_w.setEnabled(false);
		button_s.setEnabled(false);
		button_a.setEnabled(false);
		button_d.setEnabled(false);
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
		button_w.setEnabled(true);
		button_s.setEnabled(true);
		button_a.setEnabled(true);
		button_d.setEnabled(true);
		button_cl.setEnabled(true);
		button_lo.setEnabled(true);		
	}

	private void command(String command) {
		this.command = command;
		try {
			writer.print(command);
			writer.flush();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		Log.d("MainActivity", command);
		/*try {
			socket.close();
			Log.d("MainActivity", "socket.close");
		} catch (IOException e) {
			//handlerException(e, "close exception: " + e.toString());
			Log.d("MainActivity", "command-socket.close()-io");
		}*/
	}
	
	class myThread extends Thread{
		@Override
		public void run(){
			initSocket();			
		}
	}
	
	class longPress extends Thread{
		@Override
		public void run(){
			while(flag){
				command(cmd);
				try {
					Thread.sleep(250);
				} catch (Exception e) {
					break;
				}
			}
		}
	}

	/*@Override
	public void onClick(View v) {
		
		//new myThread().start();
		
		switch (v.getId()){
		case R.id.imageButton_u:
			//Log.d("MainActivity", "onClick_up");
			command("w");
			break;
		case R.id.imageButton_d:
			command("s");
			break;
		case R.id.imageButton_l:
			command("a");
			break;
		case R.id.imageButton_r:
			command("d");
			break;
		case R.id.imageButton_cl:
			command("c");
			break;
		case R.id.imageButton_lo:
			command("l");
			break;
		}
	}*/

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (v.getId()){
		case R.id.imageButton_w:			
			if(event.getAction() == MotionEvent.ACTION_DOWN){
				Log.d("MainActivity", "w_touch_down");
				cmd = "w";
				flag = true;
				new longPress().start();
			}
			if(event.getAction() == MotionEvent.ACTION_UP){
				Log.d("MainActivity", "w_touch_up");
				flag = false;
				cmd = "";
			}
			break;
		case R.id.imageButton_s:
			if(event.getAction() == MotionEvent.ACTION_DOWN){
				cmd = "s";
				flag = true;
				new longPress().start();
			}
			if(event.getAction() == MotionEvent.ACTION_UP){
				flag = false;
				cmd = "";
			}
			break;
		case R.id.imageButton_a:
			if(event.getAction() == MotionEvent.ACTION_DOWN){
				cmd = "a";
				flag = true;
				new longPress().start();
			}
			if(event.getAction() == MotionEvent.ACTION_UP){
				flag = false;
				cmd = "";
			}
			break;
		case R.id.imageButton_d:
			if(event.getAction() == MotionEvent.ACTION_DOWN){
				cmd = "d";
				flag = true;
				new longPress().start();
			}
			if(event.getAction() == MotionEvent.ACTION_UP){
				flag = false;
				cmd = "";
			}
			break;
		case R.id.imageButton_cl:
			if(event.getAction() == MotionEvent.ACTION_DOWN){
				cmd = "c";
				flag = true;
				new longPress().start();
			}
			if(event.getAction() == MotionEvent.ACTION_UP){
				flag = false;
				cmd = "";
			}
			break;
		case R.id.imageButton_lo:
			if(event.getAction() == MotionEvent.ACTION_DOWN){
				cmd = "l";
				flag = true;
				new longPress().start();
			}
			if(event.getAction() == MotionEvent.ACTION_UP){
				flag = false;
				cmd = "";
			}
			break;
		}
		return false;
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
	
	/*@Override
	protected void onDestroy(){
		super.onDestroy();
		try {
			socket.close();
			Log.d("MainActivity", "socket.close");
		} catch (IOException e) {
			Log.d("MainActivity", "command-socket.close()-io");	
		}
	}*/
	
}