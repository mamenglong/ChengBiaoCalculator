package calculator.dream.com.chengbiaocalculator;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Long on 2017/10/31.
 */

public class HistoryActivity extends Activity {
    private TextView showHistoryContent;
    private LinearLayout linearLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_active_history);
          //关联控件
        showHistoryContent= (TextView)findViewById(R.id.showContent);
        linearLayout=findViewById(R.id.sc_layout);
        siderBar();

    }

    public void siderBar(){
        for (final String str: fileList()
                ) {
            LinearLayout lyo=new LinearLayout(this);
            lyo.setOrientation(LinearLayout.HORIZONTAL);

            TextView textView = new TextView(this);
            textView.setText(str);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showHistoryContent.setText(readFile(str));
                }
            });
            Button button=new Button(this);
            button.setText("删除");
            button.setTextSize(12);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteFile(str);
                    linearLayout.removeAllViews();
                    siderBar();//刷新
                }
            });
            lyo.addView(textView);
            lyo.addView(button);
            linearLayout.addView(lyo);
        }

    }

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
        catch(FileNotFoundException e){
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

    /***联系我**/
    public void contactMe(View v){
        String url="mqqwpa://im/chat?chat_type=wpa&uin=1519269558";//普通qq调用
        //  String url="mqq://im/chat?chat_type=crm&uin=1519269558";//开通了公众号的调用
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));

    }
}
