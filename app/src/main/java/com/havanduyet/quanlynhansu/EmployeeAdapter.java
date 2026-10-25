package com.havanduyet.quanlynhansu;
import android.content.*;import android.net.Uri;import android.view.*;import android.widget.*;import java.text.*;import java.util.*;
public class EmployeeAdapter extends BaseAdapter {
 private final Context ctx;private final List<Employee> data;
 public EmployeeAdapter(Context ctx,List<Employee> data){this.ctx=ctx;this.data=data;}
 public int getCount(){return data.size();}public Object getItem(int pos){return data.get(pos);}public long getItemId(int pos){return pos;}
 @Override public View getView(int pos,View convert,ViewGroup parent){View v=convert==null?LayoutInflater.from(ctx).inflate(R.layout.row_employee,parent,false):convert;Employee e=data.get(pos);((TextView)v.findViewById(R.id.txtName)).setText(e.name);((TextView)v.findViewById(R.id.txtCode)).setText("Mã: "+e.code);((TextView)v.findViewById(R.id.txtDept)).setText(e.department+" • "+e.birthYear);((TextView)v.findViewById(R.id.txtSalary)).setText("Lương: "+NumberFormat.getNumberInstance(new Locale("vi","VN")).format(e.salary)+" đ");ImageView img=v.findViewById(R.id.imgAvatar);img.setImageDrawable(null);if(e.photoUri!=null&&!e.photoUri.isEmpty()){try{img.setImageURI(Uri.parse(e.photoUri));}catch(Exception ex){img.setImageResource(android.R.drawable.ic_menu_gallery);}}else img.setImageResource(android.R.drawable.ic_menu_myplaces);return v;}
}
