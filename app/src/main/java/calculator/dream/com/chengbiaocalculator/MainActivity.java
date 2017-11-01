package calculator.dream.com.chengbiaocalculator;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //定义属性
    private Button addAll,clearAll,history,contact,save;//按钮计算
    private float totalCost;//总价
    private List<Button> btns=null;
    private String[] description=null;
    private List<EditText> editTexts=null;
    private float[] prices=null;
    private String[] projectNames=null;
    private FileOperate operate;
    private Initialize initialize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        //用LayoutInflater加载布局
//        LayoutInflater factory = LayoutInflater.from(MainActivity.this);
//       //获取layout_active_history布局文件获取View
//        final View layoutActiveHistoryView = factory.inflate(R.layout.layout_active_history, null);
        int count=0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化资源信息
//        initialize=new Initialize(this,View.inflate(this,R.layout.activity_main,null));
//        operate=new FileOperate(this,View.inflate(this,R.layout.activity_main,null));
        btns= getAllButton();
        description= getDescription();
        editTexts= getAllEditText();
        prices= getPrices();
//        btns=initialize.getAllButton();
//        editTexts=initialize.getAllEditText();
//        description=initialize.getDescription();
//        prices=initialize.getPrices();
//        projectNames=initialize.getProjectName();
        //关联控件
        addAll = (Button) findViewById(R.id.addAll);
        clearAll= (Button) findViewById(R.id.clearAll);
        history=(Button) findViewById(R.id.history);
        contact=(Button) findViewById(R.id.contact);
        save=(Button) findViewById(R.id.save);

        //创建监听对象
        MyListener myListener = new MyListener();
        DescriptionListener desListener=new DescriptionListener();
        //执行按钮
        addAll.setOnClickListener(myListener);
        clearAll.setOnClickListener(myListener);
        history.setOnClickListener(myListener);
        contact.setOnClickListener(myListener);
        save.setOnClickListener(myListener);
        for (Button btn:btns
             ) {
            btn.setOnClickListener(desListener);
            count++;
        }

    }



    /** 按钮addall*/
    public  void countButton(){
        totalCost=0;
        int[] num=getNum();//new Initialize(this,View.inflate(this,R.layout.activity_main,null)).getNum();
        for(int i=0;i<prices.length;i++)
        {
            totalCost+=prices[i]*num[i];
           // Log.i("total",Float.toString(totalCost));
        }

        new  AlertDialog.Builder(MainActivity.this)
                .setTitle("总价" )
                .setMessage("总价："+totalCost)
                .setPositiveButton("确定" ,  null )
                .show();

}

    /** 按钮clearall*/
    public void clearButton(){
        for (EditText editText:editTexts
             ) {
            editText.setText("");
        }
        Toast  toast=Toast.makeText(this,"清空成功！",Toast.LENGTH_SHORT);
        toast.show();
      //  setContentView(R.layout.layout_tab_bar);
    }

    /***保存历史总价记录**/
    private void saveHistory() {
        new  AlertDialog.Builder(MainActivity.this)
                .setTitle("保存记录" )
                //.setMessage(sbhistory)//在countButton中记录
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try{
                            clearButton();
                            new FileOperate().saveFile(totalCost);
                            Toast toast = Toast.makeText(getApplicationContext(), "保存成功！", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        catch (Exception e)
                        {
                            e.toString();
                            Toast toast = Toast.makeText(getApplicationContext(), "保存失败！", Toast.LENGTH_SHORT);
                            toast.show();
                        }

                    }
                })
                .setNeutralButton("取消",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast toast = Toast.makeText(getApplicationContext(), "你选择了取消", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                })
                .show();
    }

    /***显示历史总价记录**/
    private void showHistory() {
//        new  AlertDialog.Builder(MainActivity.this)
//                .setTitle("历史记录" )
//                .setMessage(sbhistory+"\n")//在countButton中记录
//                .setPositiveButton("关闭" ,  null )
//                .show();
        Intent intent=new Intent();
        intent.setClass(MainActivity.this, HistoryActivity.class);
        startActivity(intent);
    }

    /***联系我**/
    private void contactMe(){
         String url="mqqwpa://im/chat?chat_type=wpa&uin=1519269558";//普通qq调用
      //  String url="mqq://im/chat?chat_type=crm&uin=1519269558";//开通了公众号的调用
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    /**内部类button监听 */
    class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            //
//            Intent intent = new Intent();
//            intent.setClass(MainActivity.this, two.class);
//            startActivity(intent);
            switch (v.getId()) {
                case R.id.addAll:
                    countButton();
                    break;
                 case R.id.clearAll:
                    clearButton();
                     break;
                case R.id.save:
                    saveHistory();
                    break;
                case R.id.history:
                    showHistory();
                    break;
                case R.id.contact:
                    contactMe();
                    break;

            }
        }
    }

    /**内部类监听显示描述*/
    class DescriptionListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            new  AlertDialog.Builder(MainActivity.this)
                    .setTitle("描述" )
                    .setMessage(description[btns.indexOf(findViewById(v.getId()))])
                    .setPositiveButton("我知道了" ,  null )
                    .show();
            }
        }



    /**
     * Created by Long on 2017/10/31.
     */


        /* 获取项目名信息*/
        public String[] getProjectName(){
            Resources res = getResources();
            if(res.getStringArray(R.array.project).length!=0 ) {
                String[] project = new String[res.getStringArray(R.array.project).length];
                for (int i = 0; i < res.getStringArray(R.array.project).length; i++)
                {
                    project[i] = res.getStringArray(R.array.project)[i];
                }
                return  project;
            }
            return  null;
        }

        /* 获取单价信息*/
        public float[] getPrices(){
            Resources res = getResources();
            if(res.getStringArray(R.array.price).length!=0 ) {
                float[] prices = new float[res.getStringArray(R.array.price).length];
                for (int i = 0; i < res.getStringArray(R.array.price).length; i++)
                {
                    prices[i] = Float.parseFloat(res.getStringArray(R.array.price)[i]);
                    Log.i("price",res.getStringArray(R.array.price)[i]);
                }
                return  prices;
            }
            return  null;
        }

        /*获取描述信息*/
        public String[] getDescription(){
            Resources res = getResources();
            if(res.getStringArray(R.array.description).length!=0 ) {
                String[] description = new String[res.getStringArray(R.array.description).length];
                for (int i = 0; i < res.getStringArray(R.array.description).length; i++) {
                    description[i] = res.getStringArray(R.array.description)[i];
                    Log.i("description", res.getStringArray(R.array.description)[i]);
                }
                return description;
            }
            return  null;
        }

        /*获取数量信息*/
        public int[] getNum(){
           // List<EditText> editTexts= getAllEditText();
            int[] nums=new int[68];
            int i=0;
            for (EditText editText:editTexts
                 ) {
                if (editText.getText().toString().equals("")||editText.getText().toString()==null)
                    nums[i]=0;
                else
                 nums[i]=Integer.parseInt(editText.getText().toString());//关联控件
                i++;
            }
               return nums;
        }

        public List<EditText> getAllEditText(){
            //定义一个存储按钮的list数组(0-68按钮)
            List<EditText> editTexts=new ArrayList<EditText>();
            //获取R 资源
            Resources res=getResources();
            //下面用for循环进去findviewid
            for (int i=0;i<68;i++){
                int id=res.getIdentifier("et_price"+(i+1),"id",getPackageName());
                EditText editText= (EditText) findViewById(id);//关联控件
                editTexts.add(editText);
            }
            return editTexts;
        }

        public List<Button> getAllButton(){
            //定义一个存储按钮的list数组(0-68按钮)
            List<Button> btnss=new ArrayList<Button>();
            //获取R 资源
            Resources res=getResources();
            //下面用for循环进去findviewid
            for (int i=0;i<68;i++){
                int id=res.getIdentifier("btn"+(i+1),"id",getPackageName());
                Button btn= (Button) findViewById(id);//关联控件
                btnss.add(btn);
            }
            return btnss;
        }





    /**
     * Created by Long on 2017/10/31.
     */

    public class FileOperate  {
        /**h获取时间*/
        private String getTime(){
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss ");
            Date curDate = new Date(System.currentTimeMillis());//获取当前时间
            String time = formatter.format(curDate);
            return time;
        }

        /***计算相隔天数，传入值为一个时间**/
        private long getIntervalDays(String start ){
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try
            {
                Date ds= df.parse(start);
                Date dn = df.parse(getTime());
                long diff = dn.getTime() - ds.getTime();  //毫秒
                long days = diff / (1000 * 60 * 60 * 24);
                long hour=(diff/(60*60*1000)-days*24);
                long min=((diff/(60*1000))-days*24*60-hour*60);
                long s=(diff/1000-days*24*60*60-hour*60*60-min*60);
                return days;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return 89;
            }


        }

        /**写文件*传入值总价 **/
        public void  saveFile(float totalCost){
            String filename =getTime()+".txt";
            // String filename ="file.txt";
            StringBuilder string=new StringBuilder();
            String[] projects=getProjectName();
            int[] nums=getNum();
            for (int i=0;i<68;i++) {
                string.append(projects[i]+"   "+nums[i]+"个\n");
            }
            string.append("总价："+totalCost);
            FileOutputStream outputStream=null;
            BufferedWriter writer=null;
            try{
                outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                writer=new BufferedWriter(new OutputStreamWriter(outputStream));
                writer.write(string.toString());
                writer.flush();
//            outputStream.write(string.toString().getBytes());
//            outputStream.close();
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
            finally {
                try{
                    if(writer!=null)
                        writer.close();
                    if(outputStream!=null)
                        outputStream.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

        /**读文件**/
        public String readFile(String fileName){
            BufferedReader br=null;
            StringBuilder sb=new StringBuilder();
            try{
                br=new BufferedReader(new InputStreamReader( openFileInput(fileName)));
                String content=br.readLine();
                while (content!=null) {
                    sb.append(content);
                    content=br.readLine();
                }
                // return sb.toString();
            }
            catch(FileNotFoundException  e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }finally {
                if(br!=null)
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
            return sb.toString();
        }
        public  String[] getFileList(){
            return    fileList();
        }


    }

}

