package be.ekm.pockettony;

import be.ekm.pockettony.R;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ListActivity {

	/** Just a SVN Test
	/** Just a SVN Test2
    /** Items entered by the user is stored in this ArrayList variable */
    ArrayList<String> list = new ArrayList<String>();
    
    private static final int READ_BLOCK_SIZE = 100;

    /** Declaring an ArrayAdapter to set items to ListView */
    ArrayAdapter<String> adapter;

    public void editList() {
    	for (int i = 0; i < list.size(); i++) {
    		if (list.get(i).contains("Ierst") && i != 0 && list.get(i).length() > 1) {
    			String newName = "Dèn " + list.get(i).substring(6);
    			list.remove(i);
    			list.add(i, newName);
    		} 
    		else if (list.get(i).contains("Dèn") && i == 0 && list.get(i).length() > 1) {
    			String newName = "Ierst " + list.get(i).substring(4);
    			list.remove(i);
    			list.add(i, newName);
    		}
    		else if (!list.get(i).contains("Dèn") && !list.get(i).contains("Ierst") && list.get(i).length() > 1) {
    			if (i == 0) {
    				String newName = "Ierst " + list.get(i);
    				list.remove(i);
    				list.add(i, newName);
    			} else {
    				String newName = "Dèn " + list.get(i);
    				list.remove(i);
    				list.add(i, newName);
    			}
    			
    		}
    		
    	}
    }
    

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

	    super.onCreate(savedInstanceState);
	    loadQueueList();
	    
	    /** Setting a custom layout for the list activity */
	    setContentView(R.layout.main);
	
	    /** Reference to the add button of the layout main.xml */
	    Button btn = (Button) findViewById(R.id.btnAdd);
	    
	    /** Reference to the delete button of the layout main.xml */
	    Button btnDel = (Button) findViewById(R.id.btnDel);
	    
	    /** Reference to the delete button of the layout main.xml */
	    Button btnInfo = (Button) findViewById(R.id.btnInfo);
	
	    /** Defining the ArrayAdapter to set items to ListView */
	    adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, list);
	
	    /** Defining a click event listener for the button "Add" */
	    OnClickListener listener = new OnClickListener() {
	                    @Override
	                    public void onClick(View v) {
	                            EditText edit = (EditText) findViewById(R.id.txtItem);
	                            if (edit.getText().toString().length() > 0) {
	                            	list.add(edit.getText().toString());
	                            	edit.setText("");
	                            	editList();
	                            	adapter.notifyDataSetChanged();
	                            }
	                            else {
	                            	String text = "Ne noam, slimme!";
	                            	Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
	                            }
	                    }
	            };
	            
	            /** Defining a click event listener for the button "Delete" */
	            OnClickListener listenerDel = new OnClickListener() {
	            	@Override
	            	public void onClick(View v) {
	            		/** Getting the checked items from the listview */
	            		SparseBooleanArray checkedItemPositions = getListView().getCheckedItemPositions();
	            		int itemCount = getListView().getCount();
	            		
	            		for(int i=itemCount-1; i >= 0; i--){
	            			if(checkedItemPositions.get(i)){	    				
	            				adapter.remove(list.get(i));
	            			}
	            		}
	            		for(int i=0 ; i < getListView().getAdapter().getCount(); i++)
	                    {
	                        getListView().setItemChecked(i, false);
	                    }
	            		editList();
	            		adapter.notifyDataSetChanged();	    	    
	            	}
	            };            
	    
	    
	            
	            
	            /** Defining a click event listener for the button "Info" */
	    OnClickListener listenerInfo = new OnClickListener() {
	    	@Override
	    	public void onClick(View v) {
	    		AlertDialog alertDialog = new AlertDialog.Builder(
                        MainActivity.this).create();
 
        // Setting Dialog Title
        alertDialog.setTitle("Och danke kènsj:");
 
        final String message = "Tinus - Toal \n" +
        		"Moos - Apostrofke en bugs\n" +
        		"Stelke - Fottoke\n" +
        		"EKM - Pregram\n" +
        		"en nateurlik...\n" +
        		"Tony! - Tony";
        // Setting Dialog Message
        alertDialog.setMessage(message);
 
 
        // Setting OK Button
        alertDialog.setButton("Gank wègk", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
        });
 
        // Showing Alert Message
        alertDialog.show();
	    	}
	    };            
	
	    
	    
	    
	    /** Setting the event listener for the add button */
	    btn.setOnClickListener(listener);
	    
	    /** Setting the event listener for the delete button */
	    btnDel.setOnClickListener(listenerDel);    
	    
	    /** Setting the event listener for the delete button */
	    btnInfo.setOnClickListener(listenerInfo);    
	
	    /** Setting the adapter to the ListView */
	    setListAdapter(adapter);
    }
    
    public void onDestroy() {
    	super.onDestroy();
    		try {
    			FileOutputStream fOut = openFileOutput("queueList.txt", MODE_PRIVATE);
    			OutputStreamWriter osw = new OutputStreamWriter(fOut);
    			
    			StringBuilder sb = new StringBuilder();
    			for (int i = 0; i < list.size(); i++) {
    				String toAppend = "";
    				if (i == 0 && list.get(i).length() > 1) {
    					toAppend = list.get(i).substring(6);
    				} else if (list.get(i).length() > 1){
    					toAppend = list.get(i).substring(4);
    				}
    				if (i < list.size()-1) {
    					sb.append(toAppend);
    					sb.append("---");
    				} else if (i == list.size()-1) {
    					sb.append(toAppend);
    				}
    			}
    			osw.write(sb.toString());
    			osw.flush();
    			osw.close();
    			
    			Toast.makeText(getBaseContext(), "List saved", Toast.LENGTH_SHORT).show();
    			list.clear();
    			
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		
    		
    		
    	
    }
    
    public void loadQueueList() {
    	try {
    		FileInputStream fIn = openFileInput("queueList.txt");
    		InputStreamReader isr = new InputStreamReader(fIn);
    		
    		char[] inputBuffer = new char[READ_BLOCK_SIZE];
    		String s = "";
    		
    		int charRead;
    		while ((charRead = isr.read(inputBuffer))>0) {
    			String readString = String.copyValueOf(inputBuffer, 0, charRead);
    			s += readString;
    			
    			inputBuffer = new char[READ_BLOCK_SIZE];
    		}
    		
    		String[] temp = s.split("---");
    		for (int i = 0; i < temp.length; i++) {
    			if (!temp[i].isEmpty()) {
    				list.add(i, temp[i]);
    			}
    		}
    		if (temp.length > 0 && temp[0].toString() != "") {
    			editList();
    		}
    		
    		
    		
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    
}