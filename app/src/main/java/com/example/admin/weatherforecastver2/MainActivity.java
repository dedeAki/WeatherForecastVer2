package com.example.admin.weatherforecastver2;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private TextView textView;
    private class GetWeatherForecastTask extends GetWeatherForecastApiTask {
        public GetWeatherForecastTask(Context context) {
            super(context);
        } //コンストラクタGetWeatherForecast
        @Override
        protected void onPostExecute(WeatherForecast data) {
            super.onPostExecute(data); //メインスレッドに反映させたい処理をここに書く
            if (data != null) {
                textView.setText(data.location.area + " " + data.location.prefecture + " " + data.location.city); //地方県都市
                for (WeatherForecast.Forecast forecast : data.forecastList) {  // 予報を一覧表示(2日分)
                    textView.append("\n");
                    textView.append(forecast.dateLabel + " " + forecast.telop);
                }
            } else if (exception != null) {//データが空であったら(エラーが出たら)
                Toast.makeText(MainActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();//トーストで注意
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) { //アプリが起動したときに呼び出される
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main); //アクティビティを配置
        textView = (TextView) findViewById(R.id.tv_main); //別のviewの定義を呼び出す(定義自体は先にcontent_main.xmlにする)
        new GetWeatherForecastTask(this).execute("140020"); //URLの最後の部分例えば140020なら小田原を指す詳しくはlivedoorのお天気サービス
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //アクションバーを定義するメソッド
        getMenuInflater().inflate(R.menu.menu_main, menu); //xmlから定義を呼び出し
        return true; //成功
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //　メニューアイテムの追加
        int id = item.getItemId(); //メニューアイテムに(上記の)設定を追加
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item); //定義したメニューアイテムを返す
    }
}
