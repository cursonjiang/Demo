package uk.co.senab.photoview.sample;

import android.app.Activity;
import android.os.Bundle;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import uk.co.senab.photoview.PhotoView;

public class AUILSampleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);

        PhotoView photoView = (PhotoView) findViewById(R.id.iv_photo);

        if (!ImageLoader.getInstance().isInited()) {
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).build();
            ImageLoader.getInstance().init(config);
        }

        ImageLoader.getInstance().displayImage("http://img.my.csdn.net/uploads/201404/13/1397393290_5765.jpeg", photoView);
    }
}
