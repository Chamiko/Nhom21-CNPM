package com.example.banhnhandau.mycooking;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.Random;

/**
 * Created by BaoND on 11/02/2017.
 */

public class LoadingActivity extends AppCompatActivity {
    TextView txtTittle, txtLDtips;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        txtTittle = (TextView)findViewById(R.id.txtTittle);
        txtLDtips = (TextView)findViewById(R.id.txtLDtips);

        Typeface custom_font = Typeface.createFromAsset(getAssets(),"Chalk-hand-lettering-shaded_demo.ttf");
        txtTittle.setTypeface(custom_font);

        Typeface custom_font_2 = Typeface.createFromAsset(getAssets(), "SVN-Archaic-1897.ttf");
        txtLDtips.setTypeface(custom_font_2);
        Random random = new Random();
        int i = random.nextInt(tips().size());
        txtLDtips.setText(tips().get(i));

        threadLoading();
    }

    public void threadLoading() {
        Thread welcomeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (Exception e) {

                } finally {

                    Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
                    EventObject course_inprogress;
                    startActivity(intent);
                    finish();
                }
            }
        });
        welcomeThread.start();
    }

    public ArrayList<String> tips(){
        ArrayList<String> tips = new ArrayList<>();
        tips.add("Khi cắt hay tỉa ớt, nếu tay bị dính cay, gây bỏng rát, hãy lấy một ít đường cát xoa rồi rửa sạch, hoặc có thể dùng dấm cũng làm tay đỡ rát hơn");
        tips.add("Để phân biệt trứng mới và trứng cũ: thả trứng vào chậu nước muối. Nếu trứng chìm là trứng mới, trứng nổi lên mặt nước là trứng quá cũ.");
        tips.add("Nên dùng dầu thực vật, vì trong dầu có chất khử mùi tanh, còn xào rau thì nên dùng mỡ heo, rau xào sẽ thơm, ngon và đẹp mắt hơn.");
        tips.add("Muốn cháo không bị trào ra ngoài nồi khi sôi, cho vào cháo một ít dầu ăn, mùi vị càng thơm ngon hơn.");
        tips.add("Nên đun sôi nước trước khi cho gạo vào vì trong nước máy có chất làm cho hao tổn vitamin B1 trong gạo.");
        tips.add("Không nên ăn trái cây cùng với hải sản. Sự kết hợp của axit tannic trong trái cây và canxi trong hải sản có thể gây nhiều triệu chứng khó chịu.");
        tips.add("Dùng sữa đậu nành và trứng cùng lúc có thể ngăn cản cơ thể hấp thụ protein.");
        tips.add("Thêm một chút nước chanh hoặc muối vào đĩa salad rau xanh để làm giảm quá trình hao hụt lượng vitamin trong rau.");
        tips.add("Để khử mùi tanh của cá, trước khi nấu hãy ngâm cá trong dung dịch 1 lít nước có pha thêm 3 thìa canh rượu trắng và gừng đập dập.");
        tips.add("Để món chiên rán không bị bắn mỡ trước khi chiên,  rắc thêm một chút bột mì vào chảo trước.");
        tips.add("Muốn nồi cháo không bị trào, gác một chiếc muỗng gỗ lên trên thành nồi. ");
        tips.add("Khi nướng thực phẩm trong lò vi sóng, muốn không bị tình trạng chín ngoài, sống trong, nên bật nóng lò trước đó khoảng 7 phút. ");
        tips.add("Khi muốn hâm lại món bánh nướng, nên cho bánh vào lò vi sóng cùng với một cốc nước để bánh không bị khô.");
        tips.add("Để thịt viên không bị vỡ vụn khi chế biến, nên trộn thịt với hỗn hợp trứng và dầu ăn trước khi vo viên.");
        return tips;
    }

}
