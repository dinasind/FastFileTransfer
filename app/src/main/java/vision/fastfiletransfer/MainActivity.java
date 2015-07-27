package vision.fastfiletransfer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends FragmentActivity {

    private Button btnShare;
    private Button btnReceive;
    private TextView tvModel;
    private TextView tvInvite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        LayoutInflater inflater = getLayoutInflater();
        View rootView = inflater.inflate(R.layout.activity_titlebar, null, false);
        RelativeLayout top = (RelativeLayout) findViewById(R.id.activity_main_top);
        top.addView(rootView);
        Button btnTitleBarLeft = (Button) rootView.findViewById(R.id.titlebar_btnLeft);
        TextView tvTitle = (TextView) rootView.findViewById(R.id.titlebar_tvtitle);
        tvTitle.setText("文件快传");

        tvModel = (TextView) findViewById(R.id.tvModel);
        btnShare = (Button) findViewById(R.id.btnShare);
        btnReceive = (Button) findViewById(R.id.btnReceive);
        tvInvite = (TextView) findViewById(R.id.tvInvite);
        tvModel.setText("本机: " + android.os.Build.MODEL.replaceAll("\\s|-", ""));
//        Log.d("SSID:", android.os.Build.MODEL.replaceAll("\\s|-", ""));

        btnTitleBarLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(MainActivity.this, ShareActivity.class);
                shareIntent.putExtra("hasSDcard", checkSDcard(getResources().getString(R.string.recFolder)));
                startActivity(shareIntent);
            }
        });
        btnReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSDcard(getResources().getString(R.string.recFolder))) {
                    Intent receiveIntent = new Intent(MainActivity.this, ReceiveActivity.class);
                    startActivity(receiveIntent);
                } else {
                    Toast.makeText(MainActivity.this, "请检查SD卡是否正确安装", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tvInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "分享些啥？", Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
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
        if (id == R.id.res_mag) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean checkSDcard(String dirName) {
        boolean isOK = false;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
//            Log.d(this.getClass().getName(), Environment.getExternalStorageState());
            File dir = new File(Environment.getExternalStorageDirectory().getPath() + dirName);
            if (!dir.exists()) {            //文件夹不存在
                if (dir.mkdirs()) {         //创建文件夹
//                    Toast.makeText(this.context, "创建文件夹成功", Toast.LENGTH_SHORT).show();
                    Log.d(this.getClass().getName(), "created document success");
                }
            }
            if (dir.exists()) {             //已经存在或者已经创建成功
                if (dir.canWrite()) {       //可以写入
                    Log.d(this.getClass().getName(), "the dir is OK!");
                    isOK = true;
                } else {
                    Log.e(this.getClass().getName(), "the dir can not write");
                }
            } else {
                Log.d(this.getClass().getName(), "没有这个目录");
            }
        } else {
            Log.d(this.getClass().getName(), "请检查SD卡是否正确安装");
        }
        return isOK;
    }

}
