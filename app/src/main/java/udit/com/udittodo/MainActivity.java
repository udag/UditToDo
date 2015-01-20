package udit.com.udittodo;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readItems();
        items = new ArrayList<String>();
        lvItems = (ListView) findViewById(R.id.lvItem);
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        items.add("First Item");
        items.add("Second Item");
        setupListViewListener();
    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int pos, long id) {
                        items.remove(pos);
                        itemsAdapter.notifyDataSetChanged();
                        writeItems();
                        return true;
                    }
                });
    }

    public void onAddItem(View view) {
        EditText etTextNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etTextNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etTextNewItem.setText("");
        writeItems();
    }

    private void readItems() {
        File filesDirs = getFilesDir();
        File todo = new File(filesDirs, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todo));
        } catch(IOException ioExp) {
            items = new ArrayList<String>();
        }
    }

    private void writeItems() {
        File filesDirs = getFilesDir();
        File todo = new File(filesDirs, "todo.txt");
        try {
            FileUtils.writeLines(todo, items);
        } catch(IOException ioExp) {
            ioExp.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
