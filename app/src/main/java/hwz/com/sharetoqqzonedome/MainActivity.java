package hwz.com.sharetoqqzonedome;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.tencent.tauth.Tencent;

import java.io.IOException;

import hwz.com.sharetoqqzonedome.share.DoShareHelp;


public class MainActivity extends Activity
{
    public static Tencent mTencent;
    //appid
    private static String APP_ID = "1104720510";
    //标题
    private EditText edt_title;
    //内容
    private EditText edt_message;
    //显示图片
    private ImageView img_local;
    //图片返回码
    private final int IMAGE_CODE = 0;
    //本地图片路径
    private static String path="";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edt_title = (EditText) findViewById(R.id.edt_title);
        edt_message = (EditText) findViewById(R.id.edt_message);
        mTencent = Tencent.createInstance(APP_ID, this.getApplicationContext());
        img_local = (ImageView)findViewById(R.id.img_local);

        //qq空间分享
        findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String title = edt_title.getText().toString().trim();
                String message = edt_message.getText().toString().trim();
                if(doEdt(title,message))
                {
                    DoShareHelp.shareToQQzone(MainActivity.this, title, message, path);

                }
            }
        });

        //获取本地图片
        findViewById(R.id.btn_get_image).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,IMAGE_CODE);
            }
        });
    }
    //判断输入框是否为空
    private boolean doEdt(String title,String message)
    {
        if(title.equals(""))
        {
            Toast.makeText(MainActivity.this,"标题不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(message.equals(""))
        {
            Toast.makeText(MainActivity.this,"内容不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }
        else
        {
            return true;
        }
    }


    /**
     * 处理分享返回继续操作
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        mTencent.onActivityResult(requestCode, resultCode, data);
        //获取本地图片路径
        if (resultCode == Activity.RESULT_OK)
        {
            switch (requestCode)
            {
                case IMAGE_CODE:
                    Bitmap logoBitmap;

                    //外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口
                    ContentResolver resolver = getContentResolver();
                    //此处的用于判断接收的Activity是不是你想要的那个
                    try
                    {
                        //获得图片的uri
                        Uri originalUri = data.getData();
                        //显得到bitmap图片
                        logoBitmap = MediaStore.Images.Media.getBitmap(resolver, originalUri);
                        img_local.setImageBitmap(logoBitmap);

                        //获取图片的路径：
                        String[] proj = {MediaStore.Images.Media.DATA};
                        //好像是android多媒体数据库的封装接口，具体的看Android文档
                        Cursor cursor = managedQuery(originalUri, proj, null, null, null);
                        //按我个人理解 这个是获得用户选择的图片的索引值
                        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        //将光标移至开头 ，这个很重要，不小心很容易引起越界
                        cursor.moveToFirst();
                        //最后根据索引值获取图片路径
                        String path1 = cursor.getString(column_index);
                        path =path1;
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }

                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}




