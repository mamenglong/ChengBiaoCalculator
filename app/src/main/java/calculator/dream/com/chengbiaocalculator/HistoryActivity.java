package calculator.dream.com.chengbiaocalculator;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
/**
 * Created by Long on 2017/10/31.
 */

public class HistoryActivity extends Activity {
    private TextView showHistoryContent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_active_history);
          //关联控件
        showHistoryContent= (TextView)findViewById(R.id.showContent);
        StringBuilder sb=new StringBuilder();
        LinearLayout linearLayout=findViewById(R.id.sc_layout);
        for (String str: fileList()
                ) {
            TextView textView = new TextView(this);
            textView.setText(str);
            linearLayout.addView(textView);
            sb.append(str+"\n");
        }
        showHistoryContent.setText(sb.toString());

    }
    /***联系我**/
    public void contactMe(View v){
        String url="mqqwpa://im/chat?chat_type=wpa&uin=1519269558";//普通qq调用
        //  String url="mqq://im/chat?chat_type=crm&uin=1519269558";//开通了公众号的调用
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));

    }
}
