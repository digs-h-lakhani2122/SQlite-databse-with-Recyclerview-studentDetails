package coatocl.exaatocl.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class recyclerview extends AppCompatActivity {
    List<CustomModel> ListDataItem;
    SQLiteDatabase database;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);

        recyclerView = findViewById(R.id.recyclerview);

        ListDataItem = new ArrayList<>();

        database =openOrCreateDatabase(MainActivity.DATABASE_NAME, MODE_PRIVATE, null);
        showStudentsFromDatabase();
    }

    private void showStudentsFromDatabase() {
//        Student indicates table name
        Cursor cursorStudent = database.rawQuery("SELECT*FROM Student", null);
        if (cursorStudent.moveToFirst()) {
            do {

                ListDataItem.add(new CustomModel(
                        cursorStudent.getInt(0),
                        cursorStudent.getString(1),
                        cursorStudent.getString(2),
                        cursorStudent.getString(3),
                        cursorStudent.getString(4)
                ));
            }
            while (cursorStudent.moveToNext());
        }
        cursorStudent.close();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        Adapter adapter = new Adapter(this, ListDataItem, database, R.layout.list_item);
        recyclerView.setAdapter(adapter);

  }
}
