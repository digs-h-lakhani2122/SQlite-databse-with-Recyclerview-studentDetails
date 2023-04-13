package coatocl.exaatocl.recyclerview;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>
{

    List<CustomModel> listDataItem;
    SQLiteDatabase database;
    Context context;
    int resource;

    //    constructor
    public Adapter(Context context, List<CustomModel> ListDataItem, SQLiteDatabase database,int resource)
    {
//        super(context,ListDataItem,database,resource);
        this.context=context;
        this.listDataItem =ListDataItem;
        this.database = database;
        this.resource=resource;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(resource,null,false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return  listDataItem.size();
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        CustomModel customModel = listDataItem.get(position);
        holder.name.setText(customModel.getStudent_name());
        holder.department.setText(customModel.getStudent_department());
        holder.semester.setText(customModel.getStudent_semester());
        holder.project_name.setText(customModel.getStudent_project_name());


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("DELETE DATA");
                builder.setMessage("R you sure to delete this data?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String sql="DELETE FROM Student WHERE id = ?";
//                        database.execSQL(sql,new  Integer[]{customModel.getID()});
                        database.execSQL(sql,new Integer[]{customModel.getID()});
                        reloadStudentsFromDatabase();
                    }
                });
                builder.setNegativeButton("NO", (dialog, which) -> {

                });
                AlertDialog dialog=builder.create();
                dialog.show();

            }
        });


        holder.insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                updateStudent(customModel);
            }
        });



    }


    //    for update our listview's item
    private void updateStudent(CustomModel listmodel)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);

        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.edit,null);
        builder.setView(view);

        EditText eName=view.findViewById(R.id.ename);
        Spinner eDepartment=view.findViewById(R.id.edepartment);
        Spinner eSemester=view.findViewById(R.id.esemester);
        EditText eProject_name=view.findViewById(R.id.eproject);
        TextView ok=view.findViewById(R.id.eview_detail);

        eName.setText(listmodel.student_name);
        eProject_name.setText(listmodel.student_project_name);

        AlertDialog dialog=builder.create();
        dialog.show();


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ssname = eName.getText().toString();
                String ssproject = eProject_name.getText().toString();
                String ssdepartment = eDepartment.getSelectedItem().toString();
                String sssemester = eSemester.getSelectedItem().toString();

                if(ssname.isEmpty())
                {
                    eName.setError("Filled up.");
                    return;
                }
                else if (ssproject.isEmpty())
                {
                    eProject_name.setError("Filled up.");
                    return;
                }

//            SQLite for update

                String sql = "UPDATE Student SET sname = ?,"+ "adddepartment = ?," + "addsemester= ?," + "pname=?" + " WHERE id = ?;";

                        Log.d("CheckSQl",""+listmodel.getID());

                database.execSQL(sql, new String[]{ssname, ssdepartment, ssproject,sssemester, String.valueOf(listmodel.getID())});;
                Toast.makeText(context,"Updated DATA GONE to DATABASE",Toast.LENGTH_LONG).show();
                reloadStudentsFromDatabase();
                dialog.dismiss();
            }
        });
    }

    private void reloadStudentsFromDatabase() {

//        Student indicates table name
        Cursor cursorStudent = database.rawQuery("SELECT*FROM Student", null);
        if (cursorStudent.moveToFirst()) {
            listDataItem.clear();
            do {
//                    ListDataItem.add((new ListModelItemSet(
                listDataItem.add(new CustomModel(

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
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView department;
        TextView semester;
        TextView project_name;
        Button delete;
        Button insert;

        ViewHolder( View itemView)
        {
            super(itemView);

            name=(TextView)itemView.findViewById(R.id.name);
            semester=(TextView)itemView.findViewById(R.id.semester);
            department=(TextView)itemView.findViewById(R.id.department);
            project_name=(TextView)itemView.findViewById(R.id.project_name);
            delete=(Button)itemView.findViewById(R.id.b2);
            insert=(Button)itemView.findViewById(R.id.b1);

        }
    }
}

